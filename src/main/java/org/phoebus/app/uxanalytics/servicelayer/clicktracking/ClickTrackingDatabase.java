package org.phoebus.app.uxanalytics.servicelayer.clicktracking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface ClickTrackingDatabase {

    @PostMapping(path="/recordClick")
    ResponseEntity<String> recordClick(@RequestBody ClickBean click);
}
