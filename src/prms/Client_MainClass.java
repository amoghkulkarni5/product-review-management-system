package prms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


// This is the main class for the client. Main method will be in this class. Execution of the client side code will begin from here.

public class Client_MainClass extends Thread {
	LoginPage loginpageobject=null; // The first page to be displayed is the login page.
	Socket clientsocket;
	int attempts;
	ObjectOutputStream output;
	ObjectInputStream input;
	
	public Client_MainClass(String ip,int port) throws InterruptedException, IOException
	{
		attempts=0;
		// Created the socket to connect to server. One client will have only one socket connection.
		// The reference of that socket should be passed to all the UI classes.
		while(true)
		{
			try {
				clientsocket=new Socket(ip,4998);
				output=new ObjectOutputStream(clientsocket.getOutputStream());
				input=new ObjectInputStream(clientsocket.getInputStream());
				loginpageobject=new LoginPage("Product Review Management System",clientsocket,output,input);
				loginpageobject.setUndecorated(true);
				loginpageobject.setVisible(true);
				break;
			} 
			catch (IOException e) {
				if(attempts<10)
				{
					attempts++;
					System.out.println("Could not connect. Reconnecting...");
					Thread.sleep(3000);
				}
				else
				{
					System.out.println("Error connecting to the server.");
					break;
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		new Client_MainClass("localhost",4998);
	}
}
