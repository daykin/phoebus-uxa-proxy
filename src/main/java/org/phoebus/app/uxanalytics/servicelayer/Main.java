package org.phoebus.app.uxanalytics.servicelayer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/neo4jResource");
            Connection conn = ds.getConnection();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
