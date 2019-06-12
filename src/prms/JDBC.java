package prms;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import static com.mongodb.client.model.Sorts.*;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;


@SuppressWarnings("deprecation")
public class JDBC {
	MongoClient mongo;
	MongoDatabase database;
	MongoCollection<Document> collection;
	Document doc;
	ConnectToJDBC obj_socket=null;

	public JDBC()
	{
		mongo=new MongoClient("localhost",27017);
		database=mongo.getDatabase("prms");
	}
	
	// This function is called when the object sent from the socket is a client object.
	// All operations related to the client are carried out here.
	public void funcClient(Client obj,ConnectToJDBC obj_socket) throws IOException
	{
		this.obj_socket=obj_socket;
		collection=database.getCollection("Client");
		Document max=null;
		max=collection.find().sort(descending("cid")).first(); //find the last cid
		int last=1000;
		if(max!=null) last=max.getInteger("cid");
		switch (obj.operation)
		{
		case "register":
			FindIterable<Document> cursor1=collection.find(Filters.eq("username",obj.username));
			FindIterable<Document> cursor2=database.getCollection("Seller").find(Filters.eq("username",obj.username));
			if(!cursor1.iterator().hasNext() && !cursor2.iterator().hasNext()) // if the username isn't already taken
			{
				doc=new Document();
				doc.put("cid", last+1); // The cid can start from 1001,1002 and so on
				doc.put("username", obj.username);
				doc.put("password", obj.password);
				doc.put("name", obj.name);
				doc.put("level", 1); //Initially after creating a new user,level will be 1
				collection.insertOne(doc);
				obj_socket.write(new Client(obj.name,obj.username,obj.password,"inserted",1,last+1,0,null));
			}
			else
			{
				obj_socket.write(new Client(obj.name,obj.username,obj.password,"already_taken",1,last+1,0,null));
			}
			break;
			
		case "signin":
			// verify whether the right pair of username and password exist in the database
			FindIterable<Document> cursor_verify=collection.find(Filters.and(Filters.eq("username",obj.username),Filters.eq("password",obj.password)));
			
			if(!cursor_verify.iterator().hasNext()) //if cursor doesn't have any documents, then no such user exists.
			{
				obj_socket.write(new Client(obj.name,obj.username,obj.password,"not_verified",0,last+1,0,null));
			}
			else
			{
				Document doc=cursor_verify.iterator().next();
				int no_of_reviews=(int)database.getCollection("review").countDocuments(Filters.eq("cid",doc.getInteger("cid"))); //get total reviews from review collection
				ArrayList<Integer> isReviewed=new ArrayList<Integer>();
				MongoCursor<Document> cursor=database.getCollection("review").find(Filters.eq("cid",doc.getInteger("cid"))).iterator();
				while(cursor.hasNext())
					isReviewed.add(cursor.next().getInteger("pid"));
				
				
				// If verified, get all the details of that particular client from the database and write to the client side. This client object will be a parameter to the ClientGui class
				obj_socket.write(new Client(doc.getString("name"),doc.getString("username"),doc.getString("password"),"verified",doc.getInteger("level"),doc.getInteger("cid"),no_of_reviews,isReviewed));
			}
			break;
			
		}
	}
	
	// This function is called when the object sent from the socket is a seller object.
	// All operations related to the seller are carried out here.
	public void funcSeller(Seller obj,ConnectToJDBC obj_socket) throws IOException
	{
		this.obj_socket=obj_socket;
		collection=database.getCollection("Seller");
		int products[]=null;
		Document max=null;
		max=collection.find().sort(descending("sid")).first(); //find the last sid (this is used when adding a new seller sid after the last one)
		int last=5000;
		if(max!=null) last=max.getInteger("sid");
		switch (obj.operation)
		{
		case "register":
			FindIterable<Document> cursor1=collection.find(Filters.eq("username",obj.username));
			FindIterable<Document> cursor2=database.getCollection("Client").find(Filters.eq("username",obj.username));
			if(!cursor1.iterator().hasNext() && !cursor2.iterator().hasNext()) // if the username isn't already taken
			{
				doc=new Document();
				doc.put("sid", last+1); // The sid can start from 5001,5002 and so on
				doc.put("username", obj.username);
				doc.put("password", obj.password);
				doc.put("name", obj.name);
				doc.put("products", products);
				doc.put("rating", 0.0);
				collection.insertOne(doc);
				obj_socket.write(new Seller(last+1,obj.username,obj.password,"inserted",obj.name,products,0.0));
			}
			else
			{
				obj_socket.write(new Seller(last+1,obj.username,obj.password,"already_taken",obj.name,products,0.0));
			}
			break;
			
		case "signin":
			FindIterable<Document> cursor_verify=collection.find(Filters.and(Filters.eq("username",obj.username),Filters.eq("password",obj.password)));
			if(!cursor_verify.iterator().hasNext())
			{
				obj_socket.write(new Seller(last+1,obj.username,obj.password,"not_verified",obj.name,products,0.0));
			}
			else
			{
				Document doc=cursor_verify.iterator().next();
				
				// If verified, get all the details of that particular seller from the database and write to the client side. This seller object will be a parameter to the seller gui class
				obj_socket.write(new Seller(doc.getInteger("sid"),doc.getString("username"),doc.getString("password"),"verified",doc.getString("name"),(int[])doc.get("products"),doc.getDouble("rating")));
			}
			break;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	void funcProduct(Product obj,ConnectToJDBC obj_socket) throws IOException
	{
		this.obj_socket=obj_socket;
		collection=database.getCollection("product");
		MongoCollection<Document> cs=database.getCollection("seller");
		switch(obj.operation)
		{
		case "toptwelve" :
			int number=Math.min((int)collection.countDocuments(), 12);
			Vector<Product>vec=toprated(number,(String) obj.ptype); //here obj.ptype is going to hold a string with either "all", "electronics", ...
			obj_socket.write(vec);
			break;
		case "search":
			Vector<Product> productvec=new Vector<Product>();
			FindIterable<Document> cur=collection.find(Filters.or(Filters.eq("pname",obj.pname),Filters.in("tags", obj.pname)));
			MongoCursor<Document> iterator=cur.iterator();
			GridFS gfs=new GridFS(mongo.getDB("prms"));
			
			while(iterator.hasNext())
			{
				Document doc=iterator.next();
				GridFSDBFile imageoutput=gfs.findOne(""+doc.getInteger("pid"));
				Image image=ImageIO.read(imageoutput.getInputStream());
				String category=doc.getString("category");
				Document embed=(Document) doc.get("description");
				double sellerrating=(double)cs.find(Filters.eq("sid",doc.getInteger("sid"))).iterator().next().getDouble("rating");
				
				if(category.equals("electronics"))
				{
					Electronics e=new Electronics(embed.getString("type"),embed.getString("brand"),embed.getInteger("durability"));  //changed to add durability
					Product p=new Product(doc.getInteger("pid"),doc.getInteger("sid"),doc.getInteger("cost"),doc.getDouble("avg_rating"),doc.getString("pname"),"result_search",new ImageIcon(image),e,(ArrayList<String>) doc.get("tags"),sellerrating);
					productvec.add(p);
				}
				
				else if(category.equals("books"))
				{
					Books b=new Books(embed.getInteger("isbn"),embed.getString("author"));
					Product p=new Product(doc.getInteger("pid"),doc.getInteger("sid"),doc.getInteger("cost"),doc.getDouble("avg_rating"),doc.getString("pname"),"result_search",new ImageIcon(image),b,(ArrayList<String>) doc.get("tags"),sellerrating);
					productvec.add(p);
				}
				
				else if(category.equals("beautyandhealth"))
				{
					BeautyandHealth b=new BeautyandHealth(embed.getInteger("netwt"),embed.getString("brand"));
					Product p=new Product(doc.getInteger("pid"),doc.getInteger("sid"),doc.getInteger("cost"),doc.getDouble("avg_rating"),doc.getString("pname"),"result_search",new ImageIcon(image),b,(ArrayList<String>) doc.get("tags"),sellerrating);
					productvec.add(p);
				}
				
				else if(category.equals("clothing"))
				{
					Clothing c=new Clothing(embed.getString("material"),embed.getString("brand"));
					Product p=new Product(doc.getInteger("pid"),doc.getInteger("sid"),doc.getInteger("cost"),doc.getDouble("avg_rating"),doc.getString("pname"),"result_search",new ImageIcon(image),c,(ArrayList<String>) doc.get("tags"),sellerrating);
					productvec.add(p);
				}
			}
			obj_socket.write(productvec);
			break;
			
		case "searchwithpid":
			Document dpid=collection.find(Filters.eq("pid",obj.pid)).iterator().next();
			GridFS gfs1=new GridFS(mongo.getDB("prms"));
			GridFSDBFile imagepid=gfs1.findOne(""+dpid.getInteger("pid"));
			Image img=ImageIO.read(imagepid.getInputStream());
			double sellerrating=cs.find(Filters.eq("sid",dpid.getInteger("sid"))).iterator().next().getDouble("rating");
			Product ret=new Product(dpid.getInteger("pid"),dpid.getInteger("sid"),dpid.getInteger("cost"),dpid.getDouble("avg_rating"),dpid.getString("pname"),"result_search",new ImageIcon(img),null,null,sellerrating);
			obj_socket.write(ret);
			break;
			
		}
	}
	

	@SuppressWarnings("unchecked")
	Vector<Product> toprated(int amt,String pcategory) throws IOException
	{
		Document doc1=new Document();
		Document doc3=new Document();
		FindIterable<Document> topten;
		Vector<Product>vec=new Vector<Product>();
		doc1.put("avg_rating",-1);
		if(pcategory.equals("electronics"))
		{doc3.put("category","electronics");}
		if(pcategory.equals("books"))
		{doc3.put("category","books");}
		if(pcategory.equals("beautyandhealth"))
		{doc3.put("category","beautyandhealth");}
		if(pcategory.equals("clothing"))
		{doc3.put("category","clothing");}
		if(pcategory.equals("all"))
		{doc3.put("","");}
		
		MongoCollection<Document> col=database.getCollection("product");
		MongoCollection<Document> cs=database.getCollection("seller");
		if(pcategory.equals("all"))
		{
		 topten=col.find().sort(doc1).limit(amt);
		}
		else {
			 topten=col.find(doc3).sort(doc1).limit(amt);
		}
		MongoCursor<Document> cur=topten.iterator();
		while(cur.hasNext())
		{
			int pid,sid,cost;
			double avg_rating,sellerrating;
			String pname,category;
			BufferedImage image=null;
			ImageIcon imagetosend=null;
			ArrayList<String> tags=null;
		    Document d = cur.next();
		    pid=d.getInteger("pid");
			sid=d.getInteger("sid");
			cost=d.getInteger("cost");
			avg_rating=d.getDouble("avg_rating");
			pname=d.getString("pname");
			category=d.getString("category");//category attribute is saved in collection in mongodb
			tags=(ArrayList<String>) d.get("tags");
			sellerrating=cs.find(Filters.eq("sid",sid)).iterator().next().getDouble("rating");
			
			//Retrieving image
			GridFS gfs=new GridFS(mongo.getDB("prms"));
			GridFSDBFile imageoutput=gfs.findOne(""+pid);
			image=ImageIO.read(imageoutput.getInputStream());
			imagetosend=new ImageIcon(image);
			
			Document embed=(Document) d.get("description");
			
			if(category.equals("electronics"))
			{
				
				Electronics e=new Electronics(embed.getString("type"),embed.getString("brand"),embed.getInteger("durability"));  //changed to add durability
				Product p;
				p= new Product(pid,sid,cost,avg_rating,pname,"",imagetosend,e,tags,sellerrating);
				vec.add(p);
			}
			
			else if(category.equals("books"))
			{
				Books b=new Books(embed.getInteger("isbn"),embed.getString("author"));
				Product p;
				p= new Product(pid,sid,cost,avg_rating,pname,"",imagetosend,b,tags,sellerrating);
				vec.add(p);
			}
			
			else if(category.equals("beautyandhealth"))
			{
				BeautyandHealth b=new BeautyandHealth(embed.getInteger("netwt"),embed.getString("brand"));
				Product p;
				p= new Product(pid,sid,cost,avg_rating,pname,"",imagetosend,b,tags,sellerrating);
				vec.add(p);
			}
			
			else if(category.equals("clothing"))
			{
				Clothing c=new Clothing(embed.getString("material"),embed.getString("brand"));
				Product p;
				p= new Product(pid,sid,cost,avg_rating,pname,"",imagetosend,c,tags,sellerrating);
				vec.add(p);
			}
		    
		}
		return vec;
	}
	
	
	void funcReview(Review obj,ConnectToJDBC obj_socket) throws IOException
	{
		this.obj_socket=obj_socket;
		collection=database.getCollection("review");
		MongoCollection<Document> c1=database.getCollection("Client");
		MongoCollection<Document> c2=database.getCollection("product");
		MongoCollection<Document> c3=database.getCollection("seller");
		
		switch(obj.operation)
		{
		case "addreview" :
			int rcnt=(int)collection.countDocuments(Filters.eq("pid",obj.pid));
			if(obj.comments.equals("")) break;
			
			Document d=new Document();
		    d.put("pid",obj.pid);
		    d.put("sid",obj.sid);
		    d.put("cid",obj.cid);
		    d.put("rating",obj.rating);
		    d.put("comments",obj.comments);
		    
		    int level=c1.find(Filters.eq("cid",obj.cid)).iterator().next().getInteger("level");
		     
		    double avg=0;
		    avg=c2.find(Filters.eq("pid",obj.pid)).iterator().next().getDouble("avg_rating");
		    double newavg=(avg*rcnt*10+obj.rating*level)/((rcnt+1)*10);
		    System.out.println(newavg);
		    Document nd= new Document();
		    Document nd1= new Document();
		    Document nd2= new Document();
		    nd1.put("avg_rating",newavg);
		    nd2.put("pid",obj.pid);
		    nd.put("$set",nd1);
		    c2.updateOne(nd2, nd);
		    collection.insertOne(d);
		    
		    int no_of_reviews=(int)collection.countDocuments(Filters.eq("cid",obj.cid));
		    int total_reviews=(int)Math.round(1.5*Math.exp(level));
		    if(no_of_reviews>total_reviews && level<10) level++;
		    Document updlevel=new Document();
		    Document updlevel1=new Document();
		    Document updlevel2=new Document();
		    updlevel1.put("level",level);
		    updlevel2.put("cid", obj.cid);
		    updlevel.put("$set", updlevel1);
		    c1.updateOne(updlevel2, updlevel);
		    
		    double slrrating=c3.find(Filters.eq("sid",obj.sid)).iterator().next().getDouble("rating");
		    
		    if(slrrating==0)
		    {
		    	slrrating=obj.rating;
		    }
		    else
		    {
		    	slrrating=(slrrating+obj.rating)/2;
		    }
		    Document updrating=new Document();
		    Document updrating1=new Document();
		    Document updrating2=new Document();
		    updrating1.put("rating",slrrating);
		    updrating2.put("sid",obj.sid);
		    updrating.put("$set",updrating1);
		    c3.updateOne(updrating2, updrating);
		    
		    obj_socket.write("review inserted");
			break;
			
		case "userreviews":
			Document ur=new Document();
			
			Vector<Review>rv=new Vector<Review>();
			ur.put("cid",obj.cid);
			
			FindIterable<Document> rs = collection.find(ur);
			MongoCursor<Document> mc=rs.iterator();
			while(mc.hasNext())
			{
				Document dc=mc.next();
				int clientlevel=c1.find(Filters.eq("cid",obj.cid)).iterator().next().getInteger("level"); //get the client level from the client collection
				Review r=new Review(dc.getInteger("pid"),dc.getInteger("sid"),dc.getInteger("cid"),dc.getDouble("rating"),clientlevel,dc.getString("comments"),"returnuserreviews");
				rv.add(r);
			}
			
			obj_socket.write(rv);
			break;
			
		case "productreviews":
			Document pr=new Document();
			
			Vector<Review>rv1=new Vector<Review>();
			pr.put("pid",obj.pid);
			
			FindIterable<Document> rs1 = collection.find(pr);
			MongoCursor<Document> mc1=rs1.iterator();
			while(mc1.hasNext())
			{
				Document dc=mc1.next();
				int clientlevel=c1.find(Filters.eq("cid",dc.getInteger("cid"))).iterator().next().getInteger("level"); //get the client name from the client collection
				Review r=new Review(dc.getInteger("pid"),dc.getInteger("sid"),dc.getInteger("cid"),dc.getDouble("rating"),clientlevel,dc.getString("comments"),"returnuserreviews");
				rv1.add(r);
			}
			
			obj_socket.write(rv1);
			break;
		}
	}
	
	
	
}