package jim.yang.logback;

import java.io.FileNotFoundException;
import javax.servlet.ServletContext;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.util.WebUtils;

public abstract class LogbackWebConfigurer
{
  public static final String CONFIG_LOCATION_PARAM = "logbackConfigLocation";
  public static final String REFRESH_INTERVAL_PARAM = "logbackRefreshInterval";
  public static final String EXPOSE_WEB_APP_ROOT_PARAM = "logbackExposeWebAppRoot";
  
  public LogbackWebConfigurer() {}
  
  public static void initLogging(ServletContext servletContext)
  {
   if (a(servletContext)) {
     WebUtils.setWebAppRootSystemProperty(servletContext);
    }

    String location = servletContext.getInitParameter("logbackConfigLocation");
    if (location != null)
    {
      try
      {

       if (!ResourceUtils.isUrl(location))
        {
         location = SystemPropertyUtils.resolvePlaceholders(location);
          location = WebUtils.getRealPath(servletContext, location);
        }
         servletContext.log("Initializing logback from [" + location + "]");
         
         LogbackConfigurer.initLogging(location);
      }
      catch (FileNotFoundException ex) {
        throw new IllegalArgumentException("Invalid 'logbackConfigLocation' parameter: " + ex.getMessage());
      }
    }
  }

  public static void shutdownLogging(ServletContext servletContext)
  {
    servletContext.log("Shutting down logback");
    try {
      LogbackConfigurer.shutdownLogging();
    }
    finally {
     if (a(servletContext)) {
         WebUtils.removeWebAppRootSystemProperty(servletContext);
      }
    }
  }

  private static boolean a(ServletContext servletContext)
  {
     String exposeWebAppRootParam = servletContext.getInitParameter("logbackExposeWebAppRoot");
     return (exposeWebAppRootParam == null) || (Boolean.valueOf(exposeWebAppRootParam).booleanValue());
  }
}