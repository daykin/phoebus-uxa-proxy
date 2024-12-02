package org.phoebus.app.uxanalytics.servicelayer.clicktracking;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jndi.JndiTemplate;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
public class MariaDbDataSource {
    @Bean
    public DataSource dataSource() {
        try {
            InitialContext ctx = new InitialContext();
            String url = (String) ctx.lookup("mariaURI");
            String username = (String) ctx.lookup("mariaUser");
            String password = (String) ctx.lookup("mariaPassword");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);
            config.setDriverClassName("org.mariadb.jdbc.Driver");

            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setIdleTimeout(30000);
            config.setMaxLifetime(1800000);
            config.setConnectionTimeout(30000);
            return new HikariDataSource(config);

        } catch (NamingException e) {
            throw new RuntimeException("Failed to configure HikariCP DataSource", e);
        }
    }
}
