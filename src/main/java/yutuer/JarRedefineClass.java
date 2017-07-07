package yutuer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

public class JarRedefineClass {

	private static final Logger log = Logger.getLogger(JarRedefineClass.class);

	private final String jarName;
	private final Instrumentation inst;

	public JarRedefineClass(String jarName, Instrumentation inst) {
		super();
		this.jarName = jarName;
		this.inst = inst;
	}

	public void exec() {
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jarName);
			Enumeration<JarEntry> entries = jarFile.entries();
			inst.appendToSystemClassLoaderSearch(jarFile);
			while (entries.hasMoreElements()) {
				JarEntry nextElement = entries.nextElement();
				String name = nextElement.getName();
				if (name.endsWith(".class")) {
					String clsName = name.replace('/', '.');
					clsName = clsName.substring(0, clsName.lastIndexOf("."));
					Class<?> cls = null;
					try {
						log.info(String.format("开始加载class, className:%s", clsName));
						cls = Class.forName(clsName);
					} catch (Exception e) {
						// 这里不做逻辑, 应该是原来没有的类
						log.error("no care , it is only a tip!!!!");
						log.error("clsName:" + clsName + " can't forName", e);
						try {
							cls = ClassLoader.getSystemClassLoader().loadClass(clsName);
						} catch (ClassNotFoundException e1) {
							log.error("clsName:" + clsName + " can't ClassLoader.getSystemClassLoader().loadClass", e1);
						}
					}
					if (cls != null) {
						InputStream inputStream = jarFile.getInputStream(nextElement);
						byte[] bytes = toBytes(inputStream);
						try {
							inst.redefineClasses(new ClassDefinition[] { new ClassDefinition(cls, bytes) });
						} catch (ClassNotFoundException | UnmodifiableClassException e) {
							log.error("redefineClasses error ", e);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (jarFile != null) {
				try {
					jarFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static byte[] toBytes(InputStream inputStream) throws IOException {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		byte[] arrayOfByte = new byte[1000];
		int i;
		while ((i = inputStream.read(arrayOfByte)) != -1) {
			localByteArrayOutputStream.write(arrayOfByte, 0, i);
		}
		inputStream.close();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}
}
