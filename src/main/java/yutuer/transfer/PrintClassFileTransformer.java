package yutuer.transfer;

import java.lang.instrument.IllegalClassFormatException;
import java.net.URL;
import java.security.ProtectionDomain;

public class PrintClassFileTransformer implements MyClassFileTransformer{

	//在转换器使用 addTransformer 注册之后，每次定义新类和重定义类时都将调用该转换器。每次重转换类时还将调用可重转换转换器。
	
	//对新类定义的请求通过 ClassLoader.defineClass 或其本机等价方法进行。
	//对类重定义的请求通过 Instrumentation.redefineClasses或其本机等价方法进行。 
	//对类重转换的请求将通过 Instrumentation.retransformClasses 或其本机等价方法进行。
	
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		System.out.printf("now:%d, %s, loader:%s, className:%s, classBeingRedefined:%s, protectionDomain:%s, classfileBuffer:%s\n",
				System.currentTimeMillis(), Thread.currentThread(), loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
		URL location = protectionDomain.getCodeSource().getLocation();
		System.out.println("location path:" + location.getPath());
		System.out.println("location file:" + location.getFile());
		
//		try {
//			InputStream openStream = location.openStream();
//			byte[] b = A.toBytes(openStream);
//			return b;
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		
//		try {
//			ClassWriter classWriter = new ClassWriter(0);
//			TraceClassVisitor traceClassVisitor = new TraceClassVisitor(classWriter, new PrintWriter(System.out));
//			System.out.println("transform class:" + className);
//			ClassReader classReader = new ClassReader(className);
//			classReader.accept(traceClassVisitor, 0);
//			return classWriter.toByteArray();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		//一个格式良好的类文件缓冲区（转换的结果），如果未执行转换,则返回 null。 
		return null;
	}

	@Override
	public boolean canRetransform() {
		return true;
	}
	
}
