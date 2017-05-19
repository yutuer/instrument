package greys.classLoader;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T {
	
	private static Logger log = LoggerFactory.getLogger(T.class);
	
	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
		AgentClassLoader agentClassLoader = new AgentClassLoader("H:/GitHub/mini/target/dist/mysqlTunnel.excel-0.0.1-SNAPSHOT.jar");
		Class<?> loadClass = agentClassLoader.loadClass("com.badperson.util.PropertiesReader");
		log.info("classLoader:{}", loadClass.getClassLoader());
		log.info("parent:{}", agentClassLoader.getParent());
		ClassLoader.getSystemClassLoader().loadClass(T.class.getName());
	}
}
