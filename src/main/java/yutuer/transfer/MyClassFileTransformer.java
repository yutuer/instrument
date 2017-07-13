package yutuer.transfer;

import java.lang.instrument.ClassFileTransformer;

/**
 * 需要实现此接口
 * 
 * @author Administrator
 *
 */
public interface MyClassFileTransformer extends ClassFileTransformer {

	boolean canRetransform();

}
