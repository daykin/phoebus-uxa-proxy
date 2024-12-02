package org.phoebus.app.uxanalytics.servicelayer.clicktracking;

import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    @PostMapping(path="/recordClick")
    public ResponseEntity<String> recordClick(@RequestParam(value="x",required=true) Integer x,
                                             @RequestParam(value="y",required=true) Integer y,
                                             @RequestParam(value="filename",required=true) String filename) {
        ClickBean click = new ClickBean(x, y, filename);
        ClickBean ret = clickService.recordClick(click);
        return ResponseEntity.ok("Click recorded with id: " + ret.getId());
    }
}
