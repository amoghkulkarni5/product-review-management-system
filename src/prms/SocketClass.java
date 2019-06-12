package prms;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;



// This is the main server class. Server execution will start from here.
public class SocketClass extends Thread {
	
	ServerSocket serversocket;
	JDBC obj_JDBC=null; // Declare a JDBC class object. This reference will be passed to the ConnectToJDBC object. So only one JDBC object is created, only reference is passed.
	
	public SocketClass(int port) throws IOException
	{
		serversocket=new ServerSocket(port);
		obj_JDBC=new JDBC(); // Create the JDBC class object.
	}
	
	public void run()
	{
		// This will constantly check for new connections from the client.
		// As soon as a client wants to connect, it creates a new ConnectToJDBC object for that socket connection.
		while(true)
		{
			try {
				Socket socket=serversocket.accept();
				System.out.println("New client connected.");
				new ConnectToJDBC(socket,obj_JDBC); // Also pass the reference of the JDBC object.
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Main method of the server side code.
	public static void main(String[] args) throws IOException {
		SocketClass mainobject=new SocketClass(4998);
		mainobject.start();
	}
}

//For each client i.e. for each socket connection, a new ConnectToJDBC object is created (but the SAME JDBC object is for all)
class ConnectToJDBC extends Thread
{
	Socket socket;
	ObjectOutputStream output;
	ObjectInputStream input;
	JDBC obj_JDBC=null;
	
	public ConnectToJDBC(Socket socket,JDBC obj_JDBC) throws IOException
	{
		// Get the references of the socket and the JDBC object
		this.socket=socket;
		this.obj_JDBC=obj_JDBC;
		
		// Create object output/input streams for sending and receiving objects over the socket
		output=new ObjectOutputStream(socket.getOutputStream());
		input=new ObjectInputStream(socket.getInputStream());
		this.start();
	}
	
	public void run()
	{
		try {
			Object obj=input.readObject(); //read the object from the client.
			
			// Call one of the methods of the JDBC object for client, seller, product, or review
			if(obj instanceof Client)
			{
				obj_JDBC.funcClient((Client)obj,this); 
			}
			
			else if(obj instanceof Seller)
			{
				obj_JDBC.funcSeller((Seller)obj,this);
			}
			
			else if(obj instanceof Product)
			{
				obj_JDBC.funcProduct((Product)obj,this);
			}
			
			else if(obj instanceof Review)
			{
				obj_JDBC.funcReview((Review)obj,this);
			}
			
			//If user passes a String object
			else if(obj instanceof String)
			{
				if(((String)obj).equals("Close"))
				this.interrupt();
				System.out.println("Client disconnected");
				socket.close();
			}
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	//Writing an object to the client side. After this is executed, it will go to the run() method where it will wait to read an object sent over from the client.
	public void write(Object obj) throws IOException
	{
		output.writeObject(obj);
		run();
	}
}


class Client implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String name, username, password,operation;
	int level,cid,no_of_reviews;
	ArrayList<Integer> isReviewed;
	
	public Client()
	{
		name=username=password=operation="";
		level=cid=no_of_reviews=0;
		isReviewed=new ArrayList<Integer>();
	}

	// Constructor during creation of new client
	public Client(String name, String username, String password, String operation)
	{
		this.name=name;
		this.username=username;
		this.password=password;
		this.operation=operation;
		level=cid=no_of_reviews=0;
		isReviewed=new ArrayList<Integer>();
	}
	
	// Constructor during retrieval of client
	public Client(String name, String username, String password, String operation,int level,int cid,int no_of_reviews,ArrayList<Integer> isReviewed)
	{
		this.name=name;
		this.username=username;
		this.password=password;
		this.operation=operation;
		this.level=level;
		this.cid=cid;
		this.no_of_reviews=no_of_reviews;
		this.isReviewed=isReviewed;
	}
}

class Product implements Serializable
{
	private static final long serialVersionUID = 2L;
	
	int pid,sid,cost;
	double avg_rating,sellerrating;
	String pname,operation;
	ImageIcon image;
	Object ptype;
	ArrayList<String> tags;
	   
	//Constructor during creation of product
	public Product(int sid,int cost,String pname,String operation,ImageIcon image,Object ptype,ArrayList<String> tags,double sellerrating)
	{
		this.sid=sid;
		this.cost=cost;
		this.pname=pname;
		this.operation=operation;
		this.image=image;
		this.ptype=ptype;
		this.tags=tags;
		this.sellerrating=sellerrating;
	}
	   
	// Constructor during retrieval/display of product
	public Product(int pid,int sid,int cost,double avg_rating,String pname,String operation,ImageIcon image,Object ptype,ArrayList<String> tags,double sellerrating)
	{
		this.pid=pid;
		this.sid=sid;
		this.cost=cost;
		this.pname=pname;
		this.operation=operation;
		this.image=image;
		this.avg_rating=avg_rating;
		this.ptype=ptype;
		this.tags=tags;
		this.sellerrating=sellerrating;
	}
}


class Books implements Serializable
{
	private static final long serialVersionUID = 90L;
	int isbn;
	String author;
	   
	public Books(int isbn,String author)
	{
		this.isbn=isbn;
		this.author=author;
	}
}

class Electronics implements Serializable
{
	private static final long serialVersionUID = 91L;
	String type,brand;
	int durability;
	
	public Electronics(String type,String brand,int durability)
	{
		this.type=type;
		this.brand=brand;
		this.durability=durability;
	}
}

class BeautyandHealth implements Serializable
{
	private static final long serialVersionUID = 92L;
	int netwt;
	String brand;
	   
	public BeautyandHealth(int netwt,String brand)
	{
		this.netwt=netwt;
		this.brand=brand;
	}
}

class Clothing implements Serializable
{
	private static final long serialVersionUID = 93L;
	String material,brand;
	
	public Clothing(String material,String brand)
	{
		this.material=material;
		this.brand=brand;
	} 
}

class Seller implements Serializable
{
	private static final long serialVersionUID = 3L;
	
	int sid;
	double rating; //Seller's rating
	String username,password,name,operation;
	int products[];
	
	public Seller()
	{
		sid=0;
		rating=0;
		username=password=name=operation="";
	}
	
	// Constructor during creation of new Seller
	public Seller(String name,String username,String password,String operation)
	{
		this.name=name;
		this.username=username;
		this.password=password;
		this.operation=operation;
	}
	
	//Constructor during retrieval of Seller
	public Seller(int sid,String username,String password,String operation,String name,int products[],double rating)
	{
		this.sid=sid;
		this.username=username;
		this.password=password;
		this.operation=operation;
		this.name=name;
		this.products=products;
		this.rating=rating;
	}
}


class Review implements Serializable
{
	private static final long serialVersionUID = 4L;
	
	int pid,sid,cid,clientlevel;
	double rating;
	String comments,operation;
	
	
	// Also taking the client name, as while displaying all reviews of a product, we should also display the name of the client
	// Note that we are NOT storing the client name in the review collection in MongoDB, only the cid.
	// We will have to take the name of the client from the client collection while constructing the review object.
	public Review(int pid,int sid,int cid,double rating,int clientlevel,String comments,String operation)
	{
		this.pid=pid;
		this.sid=sid;
		this.cid=cid;
		this.rating=rating;
		this.comments=comments;
		this.operation=operation;
		this.clientlevel=clientlevel;
	}
}
