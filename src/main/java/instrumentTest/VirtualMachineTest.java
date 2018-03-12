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
		VirtualMachineDescriptor descriptor = null;
		for (VirtualMachineDescriptor virtualMachineDescriptor : VirtualMachine.list()) {
			logger.info("virtualMachineDescriptor: displayName:{}, id:{}",  virtualMachineDescriptor.displayName(), virtualMachineDescriptor.id());
			if(virtualMachineDescriptor.displayName().contains("Testdddd")){
				descriptor = virtualMachineDescriptor;
			}
		}
		
		VirtualMachine vm = VirtualMachine.attach(descriptor);
		if(vm instanceof HotSpotVirtualMachine){
			HotSpotVirtualMachine hsvm = HotSpotVirtualMachine.class.cast(vm);
			Properties systemProperties = hsvm.getSystemProperties();
			logger.info("{}", systemProperties);
			
			String location = systemProperties.getProperty("user.home") + File.separator  + "agent" + File.separator;
			logger.info("location:{}", location);
			hsvm.loadAgent(location + "instrument-1.0-SNAPSHOT-jar-with-dependencies.jar", "remove,yutuer.transfer.PrintClassFileTransformer;aaa.jar");
		}
		vm.detach();
	}
}
