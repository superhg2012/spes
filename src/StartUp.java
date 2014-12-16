import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.alcatel.omc.fwk.utilities.trace.TraceManager;
/**
 * Spes Start Up Listener
 * Configed in Web.xml
 * @author gbhe
 *
 */
public class StartUp implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent ctx) {
	}

	@Override
	public void contextInitialized(ServletContextEvent ctx) {
		Parameters.getInstance().loadConfiguration("D:/spes/src/param.cfg", "SPES");
		TraceManager.TrDebug("Spes Start Begin...");
	}

}
