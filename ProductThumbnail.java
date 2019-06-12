package prms;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;



public class ProductThumbnail extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JLabel lblProductImage,lblProductName,lblProductPrice;
	private String price, productname;
	private Product P;
	
	public ProductThumbnail(Product Pro)
	{
		//---- Panel Settings -----
		setBackground(Color.WHITE);
		this.setLayout(null);
		
		//---- Adding Product Details to string -----
		this.P=Pro;
		this.productname=P.pname;
		this.price=Integer.toString(P.cost);
		
		//Add Image Object of Product to ImageIcon here
		Image img=Pro.image.getImage();
		Image newimg=img.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
		lblProductImage=new JLabel("",new ImageIcon(newimg),JLabel.CENTER);
		this.add(lblProductImage);
			
		//System.out.println(this.price);
		//System.out.println(this.productname);
		lblProductName=new JLabel(this.productname,SwingConstants.CENTER);
		lblProductPrice=new JLabel("Rs. "+this.price,SwingConstants.CENTER);
		
		this.add(lblProductName);
		this.add(lblProductPrice);
		
	}
	public void setDimensions(int x, int y, int side_length, int textlabelsize)
	{
				// JPanel Bounds
		this.setBounds(x,y,side_length,side_length);
		
				// Product Image
		lblProductImage.setBounds(0,0,side_length,(side_length-textlabelsize));
		
				// Product Name
		lblProductName.setFont(new Font("DejaVu Sans", Font.BOLD, 13));
		lblProductName.setBounds(0,(side_length-textlabelsize),side_length,(textlabelsize/2));
		
				// Product Price
		lblProductPrice.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
		lblProductPrice.setBounds(0,(side_length-textlabelsize)+(textlabelsize/2),side_length,(textlabelsize/2));
		
		this.add(lblProductName);
		this.add(lblProductPrice);

	}
	public void changeTextColour(Color C)
	{
		lblProductName.setForeground(C);
		lblProductPrice.setForeground(C);
	}
	public void changeBackgroundColor(Color C)
	{
		lblProductName.setBackground(C);
		lblProductPrice.setBackground(C);
		setBackground(C);
	}
	
	public Product getProduct()
	{
		return P;
	}
//	public static void main(String args[])
//	{
//		JFrame F=new JFrame();
//		F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		F.setBounds(100,100,500,500);
//		F.getContentPane().setLayout(null);
//		
//		//------- Dummy Data--------
//		String tags[]= {"face","wash","cosmetics","lotion","cream"};
//		product p=new product(5,5,500,8.0,"Head and Shoulders",null,null,null,tags,5.5);
//		
//		//----------------------
//		ProductThumbnail  obj= new ProductThumbnail (p);
//		obj.setDimensions(50, 20, 200, 50);
//		obj.changeBackgroundColor(Color.RED);
//		obj.changeTextColour(Color.CYAN);
//		
//		//----------------------
//		F.getContentPane().add(obj);
//		F.setVisible(true);
//	
//	}
}