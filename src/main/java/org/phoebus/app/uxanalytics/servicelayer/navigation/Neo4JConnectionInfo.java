package org.phoebus.app.uxanalytics.servicelayer.navigation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@PropertySource("classpath:application.properties")
public class Neo4JConnectionInfo {
    @Value("${spring.data.neo4j.uri}")
    private String uri;
    @Value("${spring.data.neo4j.username}")
    private String username;
    @Value("${spring.data.neo4j.password}")
    private String password;

    private static final Logger logger = Logger.getLogger(Neo4JConnectionInfo.class.getName());

    private static final String URI_JNDI = "neoURI";
    private static final String USERNAME_JNDI = "neoUser";
    private static final String PASSWORD_JNDI = "neoPassword";

    private static String getJndiVariable(String name) throws Exception {
        JndiTemplate jndiTemplate = new JndiTemplate();
        return (String) jndiTemplate.lookup(name);
    }

    //try JNDI, then application.properties
    public String getUri() {
        try{
            return getJndiVariable(URI_JNDI);
        } catch (Exception e) {
            logger.warning("Could not get URI from JNDI, using application.properties");
            return uri;
        }
    }

    //try JNDI, then application.properties
    public String getUsername() {
        try{
            return getJndiVariable(USERNAME_JNDI);
        } catch (Exception e) {
            logger.warning("Could not get Username from JNDI, using application.properties");
            return uri;
        }
    }

    //try JNDI, then application.properties
    public String getPassword() {
        try{
            return getJndiVariable(PASSWORD_JNDI);
        } catch (Exception e) {
            logger.warning("Could not get password from JNDI, using application.properties");
            return uri;
        }
    }

}
