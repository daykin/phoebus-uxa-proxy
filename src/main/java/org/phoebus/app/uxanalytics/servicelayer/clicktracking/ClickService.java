package org.phoebus.app.uxanalytics.servicelayer.clicktracking;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.*;

@Service
public class ClickService {
    @Autowired
    private ClickRepository clickRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClickService.class);

    public ClickBean recordClick(ClickBean click){
        return clickRepository.save(click);
    }

}
