package org.phoebus.app.uxanalytics.servicelayer.clicktracking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface ClickTrackingDatabase {

    public @ResponseBody String recordClick(@RequestParam Integer x, @RequestParam Integer y, @RequestParam String filename);

}
