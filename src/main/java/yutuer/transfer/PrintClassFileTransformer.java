package yutuer.transfer;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.TraceClassVisitor;

public class PrintClassFileTransformer implements ClassFileTransformer{

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		try {
			ClassWriter classWriter = new ClassWriter(0);
			TraceClassVisitor traceClassVisitor = new TraceClassVisitor(classWriter, new PrintWriter(System.out));
			ClassReader classReader = new ClassReader(className);
			classReader.accept(traceClassVisitor, 0);
			return classWriter.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
