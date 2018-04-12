package cads.parser.generated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;

/**
 * Serviceklasse die die jeweiligen Bewegungsarten mit Sockets abbildet.
 */
public class Service extends Thread {
	private Socket socket;
	private ServerSocket serverSocket;
	private int clientNumber;
	private ServiceType type;
	private Message message;

	public Service(ServiceType type, int port, int clientNumber) throws UnknownHostException, IOException {
		this.serverSocket = new ServerSocket(port);
		log("New ServerSocket");
		//serverSocket.setSoTimeout(8000);
		//this.socket = serverSocket.accept();
		this.type = type;
		this.clientNumber = clientNumber;
		log("New connection with client# " + clientNumber + " at " + socket);
	}

	public void run() {
		try {

			// Decorate the streams so we can send characters
			// and not just bytes. Ensure output is flushed
			// after every newline.
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			message = (Message) ois.readObject();
			System.out.println(message.getType().toString());
			ois.close();
		}
		// Send a welcome message to the client.
		// out.println("Hello, you are client #" + clientNumber + ".");
		// out.println("Enter a line with only a period to quit\n");
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Get messages from the client, line by line; return them
		// capitalized
		finally {
			try {
				socket.close();
			} catch (IOException e) {
				log("Couldn't close a socket, what's going on?");
			}
			log("Connection with client# " + clientNumber + " closed");
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public int getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}

	public ServiceType getType() {
		return type;
	}

	public void setType(ServiceType type) {
		this.type = type;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	private void log(String message) {
		System.out.println(message);
	}

	private Message getMessage() {
		return this.message;
	}

}
