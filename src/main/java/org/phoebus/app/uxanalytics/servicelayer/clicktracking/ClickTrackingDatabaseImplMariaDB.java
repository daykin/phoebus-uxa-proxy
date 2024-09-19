package org.phoebus.app.uxanalytics.servicelayer.clicktracking;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path="/clicktracking")
public class ClickTrackingDatabaseImplMariaDB implements ClickTrackingDatabase{
    @Autowired
    private ClickRepository clickRepository;

    @Override
    @PostMapping(path="/recordClick") // Map ONLY POST Requests
    public @ResponseBody String recordClick(Integer x, Integer y, String filename) {
        ClickBean click = new ClickBean(x, y, filename);
        clickRepository.save(click);
        return "Recorded Click";
    }
}
