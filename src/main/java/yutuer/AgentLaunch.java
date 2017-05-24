package yutuer;

import java.lang.instrument.Instrumentation;

import org.apache.log4j.Logger;

import yutuer.transfer.PrintClassFileTransformer;

public class AgentLaunch {

	private static Logger log = Logger.getLogger(AgentLaunch.class);
	
	public static void premain(String args, Instrumentation inst) {
        main(args, inst);
        log.info(String.format("premain success, args:%s, inst:%s", args, inst));
    }

    public static void agentmain(String args, Instrumentation inst) {
        main(args, inst);
        log.info(String.format("agentmain success, args:%s, inst:%s", args, inst));
    }

	private static void main(String args, Instrumentation inst) {
		inst.addTransformer(new PrintClassFileTransformer(), true);
	}
    
}
