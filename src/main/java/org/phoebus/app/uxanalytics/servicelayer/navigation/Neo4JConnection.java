package org.phoebus.app.uxanalytics.servicelayer.navigation;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.neo4j.driver.*;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.summary.ResultSummary;
import org.neo4j.driver.summary.SummaryCounters;
import org.neo4j.driver.types.MapAccessor;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Neo4JConnection {

    Logger logger = Logger.getLogger(Neo4JConnection.class.getName());
    Driver driver;
    //string names for "origin" sources (i.e., not another display)
    public static final String SRC_FILE_BROWSER = "file_browser";
    public static final String SRC_TOP_RESOURCES = "top_resources_list";
    public static final String SRC_RESTORATION = "memento_restored";
    public static final String SRC_UNKNOWN = "other_source";

    private static final ArrayList<String> sources = new ArrayList<>(
            Arrays.asList(SRC_FILE_BROWSER,
                    SRC_TOP_RESOURCES,
                    SRC_RESTORATION,
                    SRC_UNKNOWN));

    //names for node types (an 'origin' source, another display, or a PV)
    public static final String TYPE_ORIGIN = "origin_source";
    public static final String TYPE_DISPLAY = "display";
    public static final String TYPE_PV = "pv";

    private static final ArrayList<String> types = new ArrayList<>(
            Arrays.asList(TYPE_ORIGIN,
                    TYPE_DISPLAY,
                    TYPE_PV));

    //names for action types (Opened a display, wrote to a PV)
    public static final String ACTION_WROTE = "wrote_to";
    public static final String ACTION_OPENED = "opened";
    public static final String ACTION_NAVIGATED = "navigation_button";
    public static final String ACTION_RELOADED = "reloaded";

    private static final ArrayList<String> actions = new ArrayList<>(
            Arrays.asList(ACTION_WROTE,
                    ACTION_OPENED,
                    ACTION_NAVIGATED,
                    ACTION_RELOADED));

    public static final String PROTOCOL = "neo4j://";

    private InitialContext ctx;

    private boolean connected = false;

    public static Neo4JConnection instance = getInstance();
    private final Neo4JConnectionInfo connectionInfo = new Neo4JConnectionInfo();

    public static Neo4JConnection getInstance(){
        if(instance == null){
            instance = new Neo4JConnection();
        }
        return instance;
    }

    private Session session;

    private Neo4JConnection(){
        connect();
    }

    public Boolean connect() {
        String host = "host";
        try {
            String uri = connectionInfo.getUri();
            String username = connectionInfo.getUsername();
            String password = connectionInfo.getPassword();
            driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
            driver.verifyConnectivity();
            logger.log(Level.INFO, "Connected to " + uri + " as " + username);
            session = driver.session(
                    SessionConfig.builder()
                            .withDatabase("neo4j")
                            .build());
            return true;
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Failed to connect to " + host, ex);
            return false;
        }
    }

    public Boolean checkConnection(){
        try{
            driver.verifyConnectivity();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public String getProtocol() {
        return PROTOCOL;
    }

    public String getDefaultUsername() {
        return "neo4j";
    }

    public Integer tearDown() {
        driver.close();
        return 0;
    }


    private static String counters2json(SummaryCounters counters) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(counters);
    }

    //Since srcType, dstType and action cannot be parameterized (limitation of cypher),
    //ensure they come from the predefined, allowed lists
    private static boolean validateParameters(NavigationBean bean){
        return  types.contains(bean.getSrcType()) &&
                types.contains(bean.getDstType()) &&
                actions.contains(bean.getAction());
    }

    private static String badRequestErrorBody(){
        return "{error: \"Invalid parameters\"\n" +
                "required_parameters: " + "[\"srcName\", \"srcType\", \"dstName\", \"dstType\", \"action\"]\n" +
                "valid_srcName_or_dstName: " + sources.toString() + "\n" +
                "valid_srcType_or_dstType: " + types.toString() + "\n" +
                "valid_actions: " + actions.toString() + "}";
    }

    public ResponseEntity<String> recordConnection(NavigationBean bean){
        if(!validateParameters(bean))
            return ResponseEntity.badRequest().body(badRequestErrorBody());
        String dstName = bean.getDstName();
        String dstType = bean.getDstType();
        String srcName = bean.getSrcName();
        String srcType = bean.getSrcType();
        String via     = bean.getVia();
        String action  = bean.getAction();

        if(session==null || !session.isOpen() || !connected)
            connected = connect();
        driver.verifyConnectivity();
        String query = "";
        Map<String, Object> cypherParams;
        if(via==null) {
            query = String.format("MERGE(src:%s {name:$srcName}) " +
                            "MERGE(dst:%s {name:$dstName}) " +
                            "MERGE(src)-[connection:%s]->(dst) " +
                            "ON CREATE SET connection.timestamps = [$timestamp]" +
                            "ON MATCH SET connection.timestamps=connection.timestamps+$timestamp",
                    srcType, dstType, action);
            cypherParams = Map.of("srcName", srcName,
                    "dstName", dstName,
                    "timestamp",Instant.now().getEpochSecond());
        }
        else{
            query = String.format("MERGE(src:%s {name:$srcName}) " +
                            "MERGE(dst:%s {name:$dstName}) " +
                            "MERGE(src)-[connection:%s]->(dst) " +
                            "ON CREATE SET connection.timestamps = [$timestamp]," +
                            "connection.via = [$via]" +
                            "ON MATCH SET connection.timestamps=connection.timestamps+$timestamp,"+
                            "connection.via = connection.via+[$via]",
                    srcType, dstType, action);
            cypherParams = Map.of("srcName", srcName,
                    "dstName", dstName,
                    "timestamp", Instant.now().getEpochSecond(),
                    "via", via);
        }
        if(dstName != null && dstType != null && srcName != null && srcType != null && session!=null && session.isOpen()) {
            try{
                String finalQuery = query;
                SummaryCounters summaryCounters = session.executeWrite(tx -> {var result = tx.run(finalQuery,
                        cypherParams);
                    return result.consume().counters();});
            return ResponseEntity.ok().body(counters2json(summaryCounters));
            } catch (JsonProcessingException e){
                logger.warning("Failed to process response: " + e.getMessage());
                return ResponseEntity.badRequest().body("Failed to process response: " + e.getMessage());
            }
        }
        return ResponseEntity.badRequest().body("Failed to record connection: null parameters or could not connect to neo4j");
    }

}