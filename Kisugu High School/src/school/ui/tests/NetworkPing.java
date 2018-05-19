package school.ui.tests;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class NetworkPing extends JFrame {
	public NetworkPing() {
	}

	public static void main(String[] args) throws UnknownHostException {
		try {
			second_testmethod();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void second_testmethod() throws IOException {
		InetAddress localhost = null;
		try {
			localhost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		// this code assumes IPv4 is used
		byte[] ip = localhost.getAddress();
		for (int i = 0; i < 255; i++) {
			ip[3] = (byte) i;
			InetAddress address = InetAddress.getByAddress(ip);
			if (address.isReachable(1000)) {
				System.out.println("can b pinged");
				// machine is turned on and can be pinged
			} else if (!address.getHostAddress().equals(address.getHostName())) {
				System.out
						.println("Name is......" + address.getHostName() + "\tIP is......." + address.getHostAddress());
				// machine is known in a DNS lookup
			} else {
				System.out.println("nothing");
				// the host address and host name are equal, meaning the host name could not be
				// resolved
			}
		}
	}
}// end of class