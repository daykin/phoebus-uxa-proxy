package org.phoebus.app.uxanalytics.servicelayer.clicktracking;

import java.io.Serializable;

public class ClickBean implements Serializable {

    private int x;
    private int y;
    private String tableName;

    public ClickBean() {
    }

    public ClickBean(int x, int y, String tableName) {
        this.x = x;
        this.y = y;
        this.tableName = tableName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
