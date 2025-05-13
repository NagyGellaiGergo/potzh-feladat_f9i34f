package hu.pte.mik.prog4.potzh.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.log4j.Logger;

public class ProjectServletContextListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger( ProjectServletContextListener.class );
    private static final String JAVA_SECURITY_PROPERTY ="java.security.auth.login.config";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Source: " + sce.getSource());
        LOGGER.info("Path: " + sce.getServletContext().getContextPath());

        if(System.getProperty(JAVA_SECURITY_PROPERTY) == null) {
            String jaasConfigFile = this.getClass().getClassLoader().getResource("jaas.config").getFile();
            System.setProperty(JAVA_SECURITY_PROPERTY, jaasConfigFile);
        }
    }
}