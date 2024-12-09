package org.phoebus.app.uxanalytics.servicelayer.navigation;

import org.springframework.http.ResponseEntity;

public interface GraphDatabase {

    public ResponseEntity<String> recordNavigation(NavigationBean requestBody);

}
