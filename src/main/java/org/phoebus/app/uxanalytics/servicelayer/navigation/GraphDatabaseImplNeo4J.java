package org.phoebus.app.uxanalytics.servicelayer.navigation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraphDatabaseImplNeo4J implements GraphDatabase{

    @Override
    @PostMapping("/recordNavigation")
    public ResponseEntity<String> recordNavigation(@RequestParam(value="dstType", required=true) String dstType,
                                                   @RequestParam(value="dstName", required=true) String dstName,
                                                   @RequestParam(value="srcType", required=true) String srcType,
                                                   @RequestParam(value="srcName", required=true) String srcName,
                                                   @RequestParam(value="action", required = true) String action) {
        NavigationBean bean = new NavigationBean(dstType, dstName, srcType, srcName, action);
        Neo4JConnection neo4JConnection = Neo4JConnection.getInstance();
        return neo4JConnection.recordConnection(bean);
    }
}
