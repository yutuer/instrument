package yutuer;

import java.io.File;
import java.lang.instrument.Instrumentation;

import yutuer.transfer.MyClassFileTransformer;
import yutuer.transfer.PrintClassFileTransformer;
import yutuer.transfer.TransformManager;

public class AgentLaunch {

	private static final TransformManager transformManager = new TransformManager();

	public static void premain(String args, Instrumentation inst) {
		MyClassFileTransformer trans = new PrintClassFileTransformer();
		inst.addTransformer(trans, trans.canRetransform());

		System.out.println(String.format("premain success, args:%s, inst:%s", args, inst));
	}

	public static void agentmain(String args, Instrumentation inst) {
		String[] argArr = args.split(";");

		String transformers = argArr[0];
		if (argArr.length > 0) {
			transformManager.parse(inst, transformers);
		}

//		for (Class<?> cls : inst.getAllLoadedClasses()) {
//			System.out.println("LoadedClasses:" + cls.getName());
//		}
		
		if(argArr.length > 1){
			String newCodeJar = "hotswap" + File.separator + argArr[1];
			File f = new File(newCodeJar);
			System.out.println("filePath:" + newCodeJar + ", exists:" + f.exists()); 

			JarRedefineClass a = new JarRedefineClass(newCodeJar);
			a.exec(inst);
		}

		System.out.println(String.format("agentmain success, args:%s, inst:%s", args, inst));
	}

}
