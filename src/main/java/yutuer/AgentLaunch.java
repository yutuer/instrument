package yutuer;

import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import yutuer.transfer.PrintClassFileTransformer;

public class AgentLaunch {

	private static final String ADD = "add";

	private static final Map<String, ClassFileTransformer> transforms = new HashMap<>();

	public static void premain(String args, Instrumentation inst) {
		inst.addTransformer(new PrintClassFileTransformer(), true);
		System.out.println(String.format("premain success, args:%s, inst:%s", args, inst));
	}

	public static void agentmain(String args, Instrumentation inst) {
		String[] argArr = args.split(";");
		if (argArr.length != 1 && argArr.length == 2) {
			return;
		}

		String jarName = argArr[0];
		String filePath = "dir" + "/" + jarName;
		File f = new File(filePath);
		System.out.println("filePath:" + filePath + ", exists:" + f.exists());

		if (argArr.length > 1) {
			boolean addOrRemove = ADD.equals(argArr[1]);
			String transformerName = argArr[2];

			try {
				Class<?> cls = Class.forName(transformerName);
				Constructor<?> declaredConstructor = cls.getDeclaredConstructor();
				if (addOrRemove) {
					if(!transforms.containsKey(transformerName)){
						transforms.put(transformerName, ClassFileTransformer.class.cast(declaredConstructor.newInstance()));
					}
				} else {
					transforms.remove(transformerName);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}

		for (Class<?> cls : inst.getAllLoadedClasses()) {
			System.out.println("LoadedClasses:" + cls.getName());
		}

		JarRedefineClass a = new JarRedefineClass(filePath, inst);
		a.exec();

		System.out.println(String.format("agentmain success, args:%s, inst:%s", args, inst));
	}

}
