package org.phoebus.app.uxanalytics.servicelayer.clicktracking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ClickTrackingDatabaseImplMariaDB implements ClickTrackingDatabase{
    @Autowired
    private ClickService clickService;

    private static final Logger logger = LoggerFactory.getLogger(ClickTrackingDatabaseImplMariaDB.class);
    @Override
    @PostMapping(path="/recordClick", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> recordClick(@RequestBody ClickBean click) {
        ClickBean ret = clickService.recordClick(click);
        return ResponseEntity.ok("{\"id\": " + ret.getId() + "}");
    }
}
