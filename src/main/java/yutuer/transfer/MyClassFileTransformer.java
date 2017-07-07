package yutuer.transfer;

import java.lang.instrument.ClassFileTransformer;

public interface MyClassFileTransformer extends ClassFileTransformer{

	boolean canRetransform();
	
}
