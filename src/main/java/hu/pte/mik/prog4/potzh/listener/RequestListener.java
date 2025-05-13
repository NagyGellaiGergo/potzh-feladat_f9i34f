package hu.pte.mik.prog4.potzh.listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import org.apache.log4j.Logger;

public class RequestListener implements ServletRequestListener {

    private static Logger LOGGER = Logger.getLogger(RequestListener.class);

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        LOGGER.info("Source: " + sre.getSource());
        LOGGER.info("Request: " + sre.getServletRequest());
    }
}
