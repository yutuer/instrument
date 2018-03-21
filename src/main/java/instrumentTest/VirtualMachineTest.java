package instrumentTest;

import java.io.File;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.tools.attach.HotSpotVirtualMachine;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class VirtualMachineTest {
	
	private static final Logger logger = LoggerFactory.getLogger(VirtualMachineTest.class);
	
	public static void main(String[] args) throws Exception {
		String processName = "scene001"; 
		if(args.length > 0){
			processName = args[0];
		}		
		VirtualMachineDescriptor descriptor = null;
		for (VirtualMachineDescriptor virtualMachineDescriptor : VirtualMachine.list()) {
			logger.info("virtualMachineDescriptor: displayName:{}, id:{}",  virtualMachineDescriptor.displayName(), virtualMachineDescriptor.id());
			if(virtualMachineDescriptor.displayName().contains(processName)){
				descriptor = virtualMachineDescriptor;
			}
		}
		
		VirtualMachine vm = VirtualMachine.attach(descriptor);
		if(vm instanceof HotSpotVirtualMachine){
			HotSpotVirtualMachine hsvm = HotSpotVirtualMachine.class.cast(vm);
			Properties systemProperties = hsvm.getSystemProperties();
			logger.info("{}", systemProperties);
			
			String location = systemProperties.getProperty("user.dir") + File.separator  + "agent";
			if(args.length > 1){
				String jarName = "redefine.jar";
				jarName = args[1];
				if(args.length > 2){
					location = args[2];
				}
				logger.info("location:{}", location);
				hsvm.loadAgent(location + File.separator + "instrument-1.0-SNAPSHOT-jar-with-dependencies.jar", 
//						"remove,yutuer.transfer.PrintClassFileTransformer;redefine.jar"
						jarName
						);
			}else{
				logger.info("location:{}", location);
				hsvm.loadAgent(location + File.separator + "instrument-1.0-SNAPSHOT-jar-with-dependencies.jar");
			}
		}
		vm.detach();
	}
}
