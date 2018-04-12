/**
 * 
 */
package cads.parser.generated;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author daexel
 *
 */


public class Middleware implements Runnable{
	Service server;
public Middleware() throws UnknownHostException, IOException {
	server = new Service(ServiceType.SERVER,50000, 1);
	//Service serviceUpDown = new Service(ServiceType.UPDOWN,50001, 1);
	//Service serviceOpenClose = new Service(ServiceType.GRAPPER,50002, 1);
}
	
	
	
public static void main(String[] args) throws Exception {
	Middleware mid = new Middleware();
	
}



@Override
public void run() {
while(true) {
	server.getServerSocket().setSoTimeout(80000);
	server.getSocket() = server.getServerSocket().accept();
	this.socket = serverSocket.accept();
}
}
	
}
