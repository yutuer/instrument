package instrumentTest;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import sun.tools.attach.HotSpotVirtualMachine;

public class VirtualMachineTest {
	
	private static final Logger logger = LoggerFactory.getLogger(VirtualMachineTest.class);
	
	public static void main(String[] args) throws AttachNotSupportedException, IOException {
		VirtualMachine vm = VirtualMachine.attach("9984");
		if(vm instanceof HotSpotVirtualMachine){
			HotSpotVirtualMachine hsvm = HotSpotVirtualMachine.class.cast(vm);
			InputStream is = hsvm.printFlag("-Xmx");  
			byte[] b = new byte[is.available()];
			is.read(b);
			logger.info("jps result:{}", new String(b));
		}
		vm.detach();
	}
}
