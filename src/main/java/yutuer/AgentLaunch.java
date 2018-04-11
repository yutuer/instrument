package yutuer;

import java.io.File;
import java.lang.instrument.Instrumentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yutuer.transfer.MyClassFileTransformer;
import yutuer.transfer.PrintClassFileTransformer;
import yutuer.transfer.TransformManager;

public class AgentLaunch {
	
	private static final Logger logger = LoggerFactory.getLogger(AgentLaunch.class);
	
	private static final TransformManager transformManager = new TransformManager();

	public static void premain(String args, Instrumentation inst) {
		MyClassFileTransformer trans = new PrintClassFileTransformer();
		inst.addTransformer(trans, trans.canRetransform());

		logger.info(String.format("premain success, args:%s, inst:%s", args, inst));
	}

	public static void agentmain(String args, Instrumentation inst) {
//		for (Class<?> cls : inst.getAllLoadedClasses()) {
//			System.out.println("LoadedClasses:" + cls.getName());
//		}
		logger.info("args:" + args);	
		if(args != null && args.length() > 0){
			String[] argArr = args.split(";");
			
			if(argArr.length > 0){
				String newCodeJar = "agent" + File.separator + "hotswap" + File.separator + argArr[0];
				File f = new File(newCodeJar);
				logger.info("filePath:" + newCodeJar + ", exists:" + f.exists()); 
				
				JarRedefineClass a = new JarRedefineClass(newCodeJar);
				boolean isExec = a.exec(inst);
				logger.info(String.format("JarRedefineClass run success, args:%s, inst:%s, execResult:%b", args, inst, isExec));
			}

			if (argArr.length > 1) {
				String transformers = argArr[1];
				transformManager.parse(inst, transformers);
			}
		}
		logger.info(String.format("agentmain success, args:%s, inst:%s", args, inst));
	}

}
