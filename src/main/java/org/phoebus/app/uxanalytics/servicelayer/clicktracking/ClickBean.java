package org.phoebus.app.uxanalytics.servicelayer.clicktracking;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ClickBean implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private int x;
    private int y;
    private String filename;
    private Long timestamp;

    public ClickBean() {
    }

    public ClickBean(int x, int y, String filename) {
        this.x = x;
        this.y = y;
        this.filename = filename;
        this.timestamp = (Long)(System.currentTimeMillis()/1000);
    }

    public ClickBean(Integer x, Integer y, String filename, Long timestamp) {
        this.x = x;
        this.y = y;
        this.filename = filename;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClickBean(int x, int y, String filename, Long id, Long timestamp) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.filename = filename;
        this.timestamp = timestamp;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getTimestamp() { return timestamp; }

    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

}
