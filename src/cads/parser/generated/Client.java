/**
 * 
 */
package cads.parser.generated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.SwingUtilities;

/**
 * @author daexel
 *
 */

public class Client implements Runnable {
	String empfangeneNachricht = null;
	String zuSendendeNachricht;
	String ip = "127.0.0.1"; // localhost
	int port = 11114;
	Socket socket = null;
	/**
	 * @param args
	 */

	

	@Override
	public void run() {

		try {
			socket = new Socket(ip, port);
			while(true) {
			schreibeNachricht(socket, zuSendendeNachricht);
			empfangeneNachricht = leseNachricht(socket);
			System.out.println("Client: "+empfangeneNachricht);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void schreibeNachricht(java.net.Socket socket, String nachricht) throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		printWriter.print(nachricht);
		printWriter.flush();
	}

	String leseNachricht(java.net.Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char[] buffer = new char[200];
		int anzahlZeichen = bufferedReader.read(buffer, 0, 200); // blockiert bis Nachricht empfangen
		String nachricht = new String(buffer, 0, anzahlZeichen);
		return nachricht;
	}

	public static void main(String[] args) {
		//Thread thread = new Thread(new Client());
		//thread.start();
		 SwingUtilities.invokeLater(new Gui());
//		 Thread thread = new Thread(new Gui());
//		 thread.start();
		// System.out.println("Gui erstellt!");
	}
}
