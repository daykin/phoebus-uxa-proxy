package org.phoebus.app.uxanalytics.servicelayer.clicktracking;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jndi.JndiTemplate;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Primary
@Configuration
public class MariaDbDataSource {
    static final Logger logger = LoggerFactory.getLogger(MariaDbDataSource.class);
    @Bean
    public DataSource dataSource() {
        try {
            InitialContext ctx = new InitialContext();
            String url = (String) ctx.lookup("jdbc/phoebus/uxa/mariadb/uri");
            String username = (String) ctx.lookup("jdbc/phoebus/uxa/mariadb/user");
            String password = (String) ctx.lookup("jdbc/phoebus/uxa/mariadb/password");

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
            throw new RuntimeException("Failed to configure HikariCP DataSource - message: "+e.getMessage(), e);
        }
    }
}
