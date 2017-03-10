package jim.yang.logback;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class LogbackConfigListener implements ServletContextListener
{
  public LogbackConfigListener() {}
  
  public void contextInitialized(ServletContextEvent event)
  {
  LogbackWebConfigurer.initLogging(event.getServletContext());
  }
  
  public void contextDestroyed(ServletContextEvent event) {
  LogbackWebConfigurer.shutdownLogging(event.getServletContext());
  }
}