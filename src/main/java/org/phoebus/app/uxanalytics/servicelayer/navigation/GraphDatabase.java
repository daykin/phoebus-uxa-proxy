package org.phoebus.app.uxanalytics.servicelayer.navigation;

import org.springframework.http.ResponseEntity;

public interface GraphDatabase {

    public ResponseEntity<String> recordNavigation(String dstType, String dstName, String srcType, String srcName, String action);

}
