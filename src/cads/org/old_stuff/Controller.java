/**
 * 
 */
package cads.org.old_stuff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author daexel
 *
 */
public class Controller implements Runnable {
	int port = 11114;
	ServerSocket serverSocket = null;
	String nachricht = null;
	BufferedReader bufferedReader =null;
	char[] buffer=null;

	@Override
	public void run() {
		
		try {
			serverSocket = new ServerSocket(port);
			Socket client = warteAufAnmeldung(serverSocket);
			while(true) {
			nachricht = leseNachricht(client);
			System.out.println("Controller: "+nachricht);
			schreibeNachricht(client, nachricht);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	Socket warteAufAnmeldung(java.net.ServerSocket serverSocket) throws IOException {
		java.net.Socket socket = serverSocket.accept(); // blockiert, bis sich ein Client angemeldet hat
		return socket;
	}

	String leseNachricht(java.net.Socket socket) throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		buffer = new char[200];
		int anzahlZeichen = bufferedReader.read(buffer, 0, 200); // blockiert bis Nachricht empfangen
		String nachricht = new String(buffer, 0, anzahlZeichen);
		return nachricht;
	}

	void schreibeNachricht(java.net.Socket socket, String nachricht) throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		printWriter.print(nachricht);
		printWriter.flush();
	}
	public static void main(String[] args) {
		Thread thread = new Thread(new Controller());
		thread.start();
	}
}
