package org.phoebus.app.uxanalytics.servicelayer.global;


import org.phoebus.app.uxanalytics.servicelayer.clicktracking.ClickRepository;
import org.phoebus.app.uxanalytics.servicelayer.navigation.Neo4JConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionCheck {

    @Autowired
    private ClickRepository clickRepository;

    @GetMapping("/checkConnection")
    public ResponseEntity<String> checkConnection() {
        String applicationStatus= "OK"; //by virtue of this returning to the user (I think, therefore I am)
        String mariaDatabaseStatus= "NOK";
        String graphDatabaseStatus= "NOK";
        Neo4JConnection neo4JConnection = Neo4JConnection.getInstance();
        if(neo4JConnection.checkConnection()){
            graphDatabaseStatus= "OK";
        }
        try{
            clickRepository.count();
            mariaDatabaseStatus= "OK";
        }
        catch (Exception ignored){}

        return ResponseEntity.ok("{\"applicationStatus\":\""+applicationStatus+"\",\"mariaDatabaseStatus\":\""+mariaDatabaseStatus+"\",\"graphDatabaseStatus\":\""+graphDatabaseStatus+"\"}");
    }
}
