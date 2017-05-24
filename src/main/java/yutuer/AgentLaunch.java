package yutuer;

import java.lang.instrument.Instrumentation;
import yutuer.transfer.PrintClassFileTransformer;

public class AgentLaunch {

	public static void premain(String args, Instrumentation inst) {
        main(args, inst);
    }

    public static void agentmain(String args, Instrumentation inst) {
        main(args, inst);
    }

	private static void main(String args, Instrumentation inst) {
		inst.addTransformer(new PrintClassFileTransformer(), true);
	}
    
}
