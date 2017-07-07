package yutuer.transfer;

import java.lang.instrument.IllegalClassFormatException;
import java.net.URL;
import java.security.ProtectionDomain;

public class PrintClassFileTransformer implements MyClassFileTransformer{

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		System.out.println(String.format("loader:%s, className:%s, classBeingRedefined:%s, protectionDomain:%s, classfileBuffer:%s",
				loader, className, classBeingRedefined, protectionDomain, classfileBuffer));
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
		return null;
	}

	@Override
	public boolean canRetransform() {
		return true;
	}
	
}
