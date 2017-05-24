package instrumentTest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.tools.attach.HotSpotVirtualMachine;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class VirtualMachineTest {
	
	private static final Logger logger = LoggerFactory.getLogger(VirtualMachineTest.class);
	
	public static void main(String[] args) throws AttachNotSupportedException, IOException {
		for (VirtualMachineDescriptor virtualMachineDescriptor : VirtualMachine.list()) {
			logger.info("virtualMachineDescriptor:{}",  virtualMachineDescriptor);
		}
		
		VirtualMachine vm = VirtualMachine.attach("8592");
		if(vm instanceof HotSpotVirtualMachine){
			HotSpotVirtualMachine hsvm = HotSpotVirtualMachine.class.cast(vm);
//			hsvm.loadAgent(arg0, arg1);
		}
		vm.detach();
	}
}
