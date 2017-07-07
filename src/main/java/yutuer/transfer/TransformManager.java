package yutuer.transfer;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class TransformManager {

	private static final String ADD = "add";

	private static final Map<String, InstWrapper> transforms = new HashMap<>();

	public void parse(Instrumentation inst, String param) {
		String[] transArr = param.split("&");
		for (String trans : transArr) {
			String[] oneTrans = trans.split(",");

			boolean addOrRemove = ADD.equals(oneTrans[0]);
			String transformerName = oneTrans[1];
			try {
				if (addOrRemove) {
					Class<?> cls = Class.forName(transformerName);
					Constructor<?> declaredConstructor = cls.getDeclaredConstructor();
					if (!transforms.containsKey(transformerName)) {
						MyClassFileTransformer transformer = MyClassFileTransformer.class.cast(declaredConstructor
								.newInstance());
						inst.addTransformer(transformer, transformer.canRetransform());
						// 因为inst每次都不同,所以这里要将2个绑定起来存储, 以便删除
						InstWrapper iw = new InstWrapper(inst, transformer);
						transforms.put(transformerName, iw);
					}
				} else {
					InstWrapper iw = transforms.get(transformerName);
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
