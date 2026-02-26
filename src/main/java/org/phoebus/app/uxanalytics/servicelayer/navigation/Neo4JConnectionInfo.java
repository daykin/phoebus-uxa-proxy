package org.phoebus.app.uxanalytics.servicelayer.navigation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Component;

import javax.naming.InitialContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@PropertySource("classpath:application.properties")
public class Neo4JConnectionInfo {
    @Value("${spring.data.neo4j.default-uri}")
    private String uri;
    @Value("${spring.data.neo4j.default-username}")
    private String username;
    @Value("${spring.data.neo4j.default-password}")
    private String password;

    private InitialContext jndiContext;

    private static final Logger logger = LoggerFactory.getLogger(Neo4JConnectionInfo.class);

    private static final String URI_JNDI = "neo4j/phoebus/uxa/bolt/uri";
    private static final String USERNAME_JNDI = "neo4j/phoebus/uxa/bolt/user";
    private static final String PASSWORD_JNDI = "neo4j/phoebus/uxa/bolt/password";

    private static String getJndiVariable(String name) throws Exception {
        JndiTemplate jndiTemplate = new JndiTemplate();
        return (String) jndiTemplate.lookup(name);
    }

    //try JNDI, then application.properties
    public String getUri() {
        try{
            return getJndiVariable(URI_JNDI);
        } catch (Exception e) {
            logger.warn("Could not get URI from JNDI, using default from application.properties: "+ uri);
            if(uri==null){
                logger.warn("uri not defined in application.properties either, using hardcoded default.");
                return "bolt://localhost:7687";
            }
            return this.uri;
        }
    }

    //try JNDI, then application.properties
    public String getUsername() {
        try{
            return getJndiVariable(USERNAME_JNDI);
        } catch (Exception e) {
            logger.warn("Could not get Username from JNDI, using default from application.properties: "+ username);
            if(username==null){
                logger.warn("Could not get Username from application.properties either, using hardcoded default.");
                return "neo4j";
            }
            return this.username;
        }
    }

    //try JNDI, then application.properties
    public String getPassword() {
        try{
            return getJndiVariable(PASSWORD_JNDI);
        } catch (Exception e) {
            logger.warn("Could not get password from JNDI, using default from application.properties");
            if(password==null){
                logger.warn("could not get password from application.properties either, using hardcoded default.");
                return "phoebus-uxa";
            }
            return this.password;
        }
    }

}
