package jim.yang.logback;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

public abstract class LogbackConfigurer
{
  public static final String CLASSPATH_URL_PREFIX = "classpath:";
  public static final String XML_FILE_EXTENSION = ".xml";
  private static LoggerContext a = (LoggerContext)LoggerFactory.getILoggerFactory();
  private static JoranConfigurator b = new JoranConfigurator();

  public LogbackConfigurer() {}

  public static void initLogging(String location) throws FileNotFoundException
  {
  String resolvedLocation = SystemPropertyUtils.resolvePlaceholders(location);
  URL url = ResourceUtils.getURL(resolvedLocation);
  if (resolvedLocation.toLowerCase().endsWith(".xml"))
    {
	  b.setContext(a);
	  a.reset();
      try {
      b.doConfigure(url);
      } catch (JoranException ex) {
        throw new FileNotFoundException(url.getPath());
      }
      a.start();
    }
  }

  public static void shutdownLogging()
  {
   a.stop();
  }
  

  public static void setWorkingDirSystemProperty(String key)
  {
	  System.setProperty(key, new File("").getAbsolutePath());
  }
}