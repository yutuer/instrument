package yutuer;

import java.io.File;
import java.lang.instrument.Instrumentation;

import yutuer.transfer.PrintClassFileTransformer;

public class AgentLaunch {

	public static void premain(String args, Instrumentation inst) {
		inst.addTransformer(new PrintClassFileTransformer(), true);
		System.out.println(String.format("premain success, args:%s, inst:%s", args, inst));
	}

	public static void agentmain(String args, Instrumentation inst) {
		String jarName = args;
		String filePath = "dir" + "/" + jarName;
		File f = new File(filePath);
		System.out.println("filePath:" + filePath + ", exists:" + f.exists());

		 for (Class<?> cls : inst.getAllLoadedClasses()) {
			 System.out.println("LoadedClasses:" + cls.getName());
		 }
		
		JarRedefineClass a = new JarRedefineClass(filePath, inst);
		a.exec();
		
		System.out.println(String.format("agentmain success, args:%s, inst:%s", args, inst));
	}

}
