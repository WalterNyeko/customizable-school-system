package school.ui.tests;

import java.io.IOException;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.swing.JOptionPane;

public class NetworkConnectionExample {
	public static void main(String[] args) throws UnknownHostException, IOException{
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			
			
			
		    NetworkInterface networkInterface = interfaces.nextElement();
		    System.out.println("Network Interface Name : [" + networkInterface.getDisplayName() + "]");
		    System.out.println("Is It connected? : [" + networkInterface.isUp() + "]");
		    
		    if(networkInterface.isUp()==true) {
		    	for (InterfaceAddress i : networkInterface.getInterfaceAddresses()){
			    	System.out.println("Host Name : "+i.getAddress().getCanonicalHostName());
			    	System.out.println("Host Address : "+i.getAddress().getHostAddress());
			    	
			    	if(i.getAddress().getHostAddress().contains("eth")) {
			    	
			    		JOptionPane.showMessageDialog(null, "Ethernet Connected To: "+networkInterface.getDisplayName());
			    	}else if(i.getAddress().getHostAddress().contains("wlan")) {
			    	
			    		JOptionPane.showMessageDialog(null, "Wifi Connected To: "+networkInterface.getDisplayName());
			    	}
			    }
		    	
		    	
		    	
		    }
		    
		    
//		    if(networkInterface.isPointToPoint()) {
//		    	JOptionPane.showMessageDialog(null, "Point To Point = "+networkInterface.getDisplayName()+" "+networkInterface.getInetAddresses());
//		    }
		    
		    for (InterfaceAddress i : networkInterface.getInterfaceAddresses()){
		    	System.out.println("Host Name : "+i.getAddress().getCanonicalHostName());
		    	System.out.println("Host Address : "+i.getAddress().getHostAddress());
		    }
		}
	}
}