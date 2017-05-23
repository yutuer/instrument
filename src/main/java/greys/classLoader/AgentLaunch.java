package greys.classLoader;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class AgentLaunch {

	public static void premain(String args, Instrumentation inst) {
        main(args, inst);
    }

    public static void agentmain(String args, Instrumentation inst) {
        main(args, inst);
    }

	private static void main(String args, Instrumentation inst) {
		inst.addTransformer(new Transformer(), true);
		
		try {
			ClassDefinition def = new ClassDefinition(Transformer.class, new byte[]{});
			inst.redefineClasses(def);
		} catch (ClassNotFoundException | UnmodifiableClassException e) {
			e.printStackTrace();
		}
	}
    
}
