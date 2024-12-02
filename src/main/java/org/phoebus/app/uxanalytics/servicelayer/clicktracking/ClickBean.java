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
    private String fileName;

    public ClickBean() {
    }

    public ClickBean(Integer x, Integer y, String filename) {
        this.x = x;
        this.y = y;
        this.fileName = filename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClickBean(int x, int y, String fileName, Long id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.fileName = fileName;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
