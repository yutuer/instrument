package yutuer.transfer;

import java.lang.instrument.Instrumentation;

public class InstrumentationWrapper {

	private final Instrumentation inst;
	private final MyClassFileTransformer trans;

	public InstrumentationWrapper(Instrumentation inst, MyClassFileTransformer trans) {
		super();
		this.inst = inst;
		this.trans = trans;
	}

	public Instrumentation getInst() {
		return inst;
	}

	public MyClassFileTransformer getTrans() {
		return trans;
	}

}
