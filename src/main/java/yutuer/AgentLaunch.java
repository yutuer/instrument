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

		String jarName = argArr[0];
		String filePath = "dir" + "/" + jarName;
		File f = new File(filePath);
		System.out.println("filePath:" + filePath + ", exists:" + f.exists());

		if (argArr.length > 1) {
			transformManager.parse(inst, argArr[1]);
		}

		for (Class<?> cls : inst.getAllLoadedClasses()) {
			System.out.println("LoadedClasses:" + cls.getName());
		}

		JarRedefineClass a = new JarRedefineClass(filePath, inst);
		a.exec();

		System.out.println(String.format("agentmain success, args:%s, inst:%s", args, inst));
	}

}
