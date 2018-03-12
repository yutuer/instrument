package yutuer.classLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class AgentJarClassLoader extends URLClassLoader {

	public AgentJarClassLoader(final String agentJar) throws MalformedURLException {
		super(new URL[] { new URL("file:" + agentJar) });
	}

	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		final Class<?> loadedClass = findLoadedClass(name);
		if (loadedClass != null) {
			return loadedClass;
		}

		try {
			Class<?> aClass = findClass(name);
			if (resolve) {
				resolveClass(aClass);
			}
			
			if(aClass != null){
				return aClass;
			}
			return super.loadClass(name, resolve);
		} catch (Exception e) {
			return super.loadClass(name, resolve);
		}
	}

}
