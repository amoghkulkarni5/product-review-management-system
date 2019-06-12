package prms;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;


public class ReviewThumbnail extends JPanel
{

	private static final long serialVersionUID = 1L;
	JLabel lbltitle;
	JButton btnproductimage;
	String pname,myreview;
	int cost;
	JTextArea taReview;
	JScrollPane JSPmyreview;
	public ReviewThumbnail(Review r)
	{	
		this.myreview=r.comments;
		this.setLayout(null);
		
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		
		lbltitle = new JLabel("Reviewer Level: "+Integer.toString(r.clientlevel));
		lbltitle.setBackground(UIManager.getColor("Button.shadow"));
		lbltitle.setBounds(23, 12, 492, 46);
		lbltitle.setFont(new Font("Century Schoolbook L", Font.PLAIN, 18));
		this.add(lbltitle);
			
		taReview = new JTextArea(myreview);
		taReview.setFont(new Font("DejaVu Sans",Font.BOLD,14));
		taReview.setColumns(4);
		taReview.setLineWrap(true);
		taReview.setWrapStyleWord(true);
		taReview.setTabSize(10);
		taReview.setEditable(false);
		taReview.setBounds(483, 12, 511, 76);
		JSPmyreview=new JScrollPane(taReview);
		JSPmyreview.setBounds(196, 0, 798, 100);
		this.add(JSPmyreview);
		
		JLabel lblRating = new JLabel("Rating: "+ Double.toString(r.rating));
		lblRating.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 15));
		lblRating.setBounds(35, 54, 115, 15);
		add(lblRating);
	}
	public ReviewThumbnail(Product p, Review r)
	{
		this.pname=p.pname;
		this.cost=p.cost;		
		this.myreview=r.comments;
		this.setLayout(null);
		
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		
		lbltitle = new JLabel(pname);
		lbltitle.setBackground(UIManager.getColor("Button.shadow"));
		lbltitle.setBounds(150, 23, 250, 19);
		lbltitle.setFont(new Font("Lucida Sans", Font.BOLD, 20));
		this.add(lbltitle);
		
		
		btnproductimage = new JButton();
		btnproductimage.setBounds(25, 0, 100, 100);
		Image img=p.image.getImage();
		Image newimg=img.getScaledInstance(btnproductimage.getWidth(), btnproductimage.getHeight(), Image.SCALE_SMOOTH);
		btnproductimage.setIcon(new ImageIcon(newimg));
		this.add(btnproductimage);
		
		
		taReview = new JTextArea(myreview);
		taReview.setFont(new Font("DejaVu Sans",Font.BOLD,14));
		taReview.setColumns(4);
		taReview.setLineWrap(true);
		taReview.setWrapStyleWord(true);
		taReview.setTabSize(10);
		taReview.setEditable(false);
		taReview.setBounds(483, 12, 511, 76);
		JSPmyreview=new JScrollPane(taReview);
		JSPmyreview.setBounds(390, 0, 604, 100);
		this.add(JSPmyreview);
		
	}
	public void setTextAreaBackground(Color C)
	{
		taReview.setBackground(C);
		JSPmyreview.setBackground(C);
	}
//	public static void main(String args[])
//	{
//		JFrame F=new JFrame();
//		F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		F.setBounds(0, 0, 1020, 650);
//		F.setLocationRelativeTo(null);
//		F.getContentPane().setBackground(Color.CYAN);
//		F.getContentPane().setLayout(null);
//		
//		String tags[]= {"cba","acc","abc"};
//		product p=new product(5,5,500,8.0,"Head and Shoulders",null,null,null,tags,5.5);
//		review r=new review(5,5,5,6.7,"Bad. Gave me Gonorrhea and Chlimydia Together.",6);
//		
//		ReviewThumbnail panel = new ReviewThumbnail(p,r);
//		panel.setBounds(0, 180, 1020, 100);
//		
//		ReviewThumbnail panel2 = new ReviewThumbnail(r);
//		panel2.setBounds(0, 280, 1020, 100);
//		
//		
//
//		
//		//-----------
//		F.getContentPane().add(panel);
//		F.getContentPane().add(panel2);
//		F.setVisible(true);
//	}
}

