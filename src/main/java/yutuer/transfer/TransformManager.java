package yutuer.transfer;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class TransformManager {

	private static final String ADD = "add";

	private static final Map<String, InstrumentationWrapper> transforms = new HashMap<>();

	public void parse(Instrumentation inst, String param) {
		String[] transArr = param.split("&");
		for (String trans : transArr) {
			String[] oneTrans = trans.split(",");

			boolean add = ADD.equals(oneTrans[0]);
			String transformerName = oneTrans[1];
			try {
				if (add) {
					Class<?> cls = Class.forName(transformerName);
					Constructor<?> declaredConstructor = cls.getDeclaredConstructor();
					if (!transforms.containsKey(transformerName)) {
						//在transform之前load, 所以如果不reDefine的话,是不会走transform的回调的
						new PrepareLoad();
						MyClassFileTransformer transformer = MyClassFileTransformer.class.cast(declaredConstructor
								.newInstance());
						System.out.printf("now:%d %s inst:%s prepare add Transformer:%s\n", System.currentTimeMillis(), Thread.currentThread(), inst, transformer.getClass().getName());
						inst.addTransformer(transformer, transformer.canRetransform());
						System.out.printf("%s end add Transformer:%s\n", Thread.currentThread(), transformer.getClass().getName());
						
						// 因为inst每次都不同,所以这里要将2个绑定起来存储, 以便删除
						// 因为这里会新定义类, 并且上面添加了一个transform类, 所以这里会因进行定义新类而调用transform
						InstrumentationWrapper iw = new InstrumentationWrapper(inst, transformer);
						transforms.put(transformerName, iw);
					}
				} else {
					InstrumentationWrapper iw = transforms.get(transformerName);
					if (iw != null) {
						if (iw.getInst().removeTransformer(iw.getTrans())) {
							transforms.remove(transformerName);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}

}
