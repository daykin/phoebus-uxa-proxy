package org.phoebus.app.uxanalytics.servicelayer.navigation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraphDatabaseImplNeo4J implements GraphDatabase{

    @Override
    @PostMapping("/recordNavigation")
    public ResponseEntity<String> recordNavigation(@RequestBody NavigationBean requestBody) {
        Neo4JConnection neo4JConnection = Neo4JConnection.getInstance();
        return neo4JConnection.recordConnection(requestBody);
    }
}
