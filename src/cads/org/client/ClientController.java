/**
 * 
 */
package cads.org.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

import cads.org.Middleware.Skeleton.ResponsibiltySide;
import cads.org.Middleware.Skeleton.RoboterFactory;
import cads.org.Server.Services.EstopServiceServer;
import cads.org.Server.Services.GrapperServiceServer;
import cads.org.Server.Services.HorizontalServiceServer;
import cads.org.Server.Services.VerticalServiceServer;


/**
 * @author daexel
 * 
 * Am ClientController können sich beliebig viele Sufaces anmelden.
 * Der Controller holt die Orders aus den Queues der Surfaces und gibt diese weiter an den Stub.
 * 
 *
 */
public class ClientController implements Runnable {
	private Surface surface;
	private  ConcurrentLinkedQueue<Feedback> feedbackQueue;
	private Order order=null;
	private Feedback feedback;




	public void addSurface(Surface surface) {
		this.surface=surface;
		//this.stub = new Stub();  ----Referenz zur Networklayer
	}
	
	@Override
	public void run() {      
		while(true) {			
			if(!surface.getQueue().isEmpty()) {
				sendOrder(surface.getQueue().poll());
			}
		}
	}
	
	/**
	 * @param order
	 * 
	 * Gibt die Order an den Stub weiter.
	 */
	public void sendOrder(Order order) {
		RoboterFactory.getService(order.getService(),ResponsibiltySide.CLIENT).move(order);
					
	}
	/**
	 * Thread to receive Messages
	 * 
	 */
	
	public Thread receivingThread = new Thread(new Runnable() {

		@Override
		public void run() {
			try {
				wait();
			} catch (InterruptedException e) {
				//Falls der Thread nicht warten kann
				e.printStackTrace();
			}
			
			//feedbackQueue.add(reveiveFeedback());
			//updateThread.notify();
			//feedback = reveiveFeedback();			
			
		}
		
	});
	
	public Thread updateThread = new Thread(new Runnable() {

		@Override
		public void run() {
			try {
				this.wait();
			} catch (InterruptedException e) {
				//Falls der Thread nicht warten kann
				e.printStackTrace();
			}
			feedback = feedbackQueue.poll();
			if(feedback.getService()==Service.HORIZONTAL) {
				surface.gui.setHorizontalProgressbar(feedback.getValueOfMovement());
			}
			if(feedback.getService()==Service.VERTICAL) {
				surface.gui.setVerticalProgressbar(feedback.getValueOfMovement());
			}
			if(feedback.getService()==Service.GRABBER) {
				System.out.println("Grapper Feedback erhalten");
			}
			
		}
		
	});
	
	
	
	public void reveiveFeedback() {
		
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		ClientController client = new ClientController();
		Surface surface = new Surface();
		client.addSurface(surface);
		client.run();
		
		
		
	}

	

}
