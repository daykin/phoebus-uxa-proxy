package org.phoebus.app.uxanalytics.servicelayer.navigation;

import org.springframework.context.annotation.Bean;

import java.io.Serializable;

public class NavigationBean implements Serializable {

    String dstName;
    String dstType;
    String srcName;
    String srcType;
    String action;

    public NavigationBean() {
    }

    public NavigationBean(String dstType, String dstName, String srcType, String srcName, String action) {
        this.dstName = dstName;
        this.dstType = dstType;
        this.srcName = srcName;
        this.srcType = srcType;
        this.action = action;
    }

    public String getDstName() {
        return dstName;
    }

    public void setDstName(String dstName) {
        this.dstName = dstName;
    }

    public String getDstType() {
        return dstType;
    }

    public void setDstType(String dstType) {
        this.dstType = dstType;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public String getSrcType() {
        return srcType;
    }

    public void setSrcType(String srcType) {
        this.srcType = srcType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
