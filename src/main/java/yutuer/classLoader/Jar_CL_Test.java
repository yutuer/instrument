package yutuer.classLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Jar_CL_Test {

	private static Logger log = LoggerFactory.getLogger(Jar_CL_Test.class);

	public static void main(String[] args) throws Exception {
		// AgentClassLoader agentClassLoader = new
		// AgentClassLoader("H:/GitHub/mini/target/dist/mysqlTunnel.excel-0.0.1-SNAPSHOT.jar");
		// Class<?> loadClass =
		// agentClassLoader.loadClass("com.badperson.util.PropertiesReader");
		// log.info("classLoader:{}", loadClass.getClassLoader());
		// log.info("parent:{}", agentClassLoader.getParent());
		// ClassLoader.getSystemClassLoader().loadClass(T.class.getName());

		URL resource = ClassLoader.getSystemClassLoader().getResource("a.jar");
		JarFile jarFile = new JarFile(resource.getPath());
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry nextElement = entries.nextElement();
			if (nextElement.getName().endsWith(".class")) {
				
			}
		}
		jarFile.close();

		AgentJarClassLoader agentJarClassLoader = new AgentJarClassLoader(resource.getPath());
		Class<?> cls = agentJarClassLoader.loadClass("springloaderTest.test.PreFix");
		Object obj = cls.newInstance();
		Method method = cls.getDeclaredMethod("doSomeThing", null);
		method.setAccessible(true);
		method.invoke(obj);
		
	}
}
