package prms;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.FlowLayout;

public class Product_Panel extends JPanel
{
	private static final long serialVersionUID = 1L;
	JPanel main_panel,WriteReview_Panel,MainInfoPanel ;
	JButton btnProductImage;
	public JButton btnBack, btnSubmit;
	JTextArea tAReview, tADescription;
	JSlider slider_rating;
	JLabel lblRating,lblWriteReview ;
	float sliderval;
	private JLabel lblAverageRating;
	public Product_Panel(Product p, Vector <Review> V)
	{
		this.setBounds(0, 150, 1020, 547);
		this.setBackground(new Color( 240, 240, 240));
		this.setLayout(null);
		
		btnProductImage = new JButton();
		//btnProductImage.setIcon(new ImageIcon(Product_Panel.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Cut@2x.png")));
		//btnProductImage.setEnabled(false);
		btnProductImage.setBounds(100, 30, 300, 300);
		Image img=p.image.getImage();
		Image newimg=img.getScaledInstance(btnProductImage.getWidth(), btnProductImage.getHeight(), Image.SCALE_SMOOTH);
		btnProductImage.setIcon(new ImageIcon(newimg));
		this.add(btnProductImage);
		
		JLabel lblProductName = new JLabel(p.pname,SwingConstants.CENTER);
		lblProductName.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 30));
		lblProductName.setBounds(437, 12, 541, 69);
		this.add(lblProductName);
		
		//========================= Write Review Panel =========================
		
		WriteReview_Panel = new JPanel();
		WriteReview_Panel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new LineBorder(new Color(128, 128, 128), 2)));
		WriteReview_Panel.setBackground(new Color(100, 149, 237));
		WriteReview_Panel.setBounds(0, 352, 1000, 195);
		WriteReview_Panel.setLayout(null);
		
		lblWriteReview = new JLabel("Write your Review!",SwingConstants.CENTER);
		lblWriteReview.setFont(new Font("Waree", Font.BOLD | Font.ITALIC, 17));
		lblWriteReview.setForeground(UIManager.getColor("PopupMenu.background"));
		lblWriteReview.setBounds(31, 0, 913, 44);
		WriteReview_Panel.add(lblWriteReview);
		
		slider_rating = new JSlider();
		slider_rating.setPaintLabels(true);
		slider_rating.setSnapToTicks(true);
		slider_rating.setValue(6);
		slider_rating.setMinimum(2);
		slider_rating.setMaximum(10);
		slider_rating.setForeground(UIManager.getColor("Button.focus"));
		slider_rating.setBackground(Color.ORANGE);
		slider_rating.setBounds(353, 41, 226, 29);
		WriteReview_Panel.add(slider_rating);
		
		lblRating = new JLabel("3.0");
		lblRating.setForeground(UIManager.getColor("CheckBoxMenuItem.background"));
		lblRating.setBackground(UIManager.getColor("CheckBox.background"));
		lblRating.setFont(new Font("Liberation Serif", Font.PLAIN, 18));
		lblRating.setBounds(597, 41, 128, 29);
		WriteReview_Panel.add(lblRating);
		
		tAReview = new JTextArea();
		tAReview.setFont(new Font("Bitstream Vera Serif", Font.PLAIN, 15));
		tAReview.setText("Your Review Here.");
		tAReview.setWrapStyleWord(true);
		tAReview.setLineWrap(true);
		tAReview.setBackground(Color.YELLOW);
		tAReview.setBounds(53, 81, 760, 102);
		WriteReview_Panel.add(tAReview);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBackground(UIManager.getColor("InternalFrame.borderColor"));
		btnSubmit.setBounds(844, 95, 117, 67);
		WriteReview_Panel.add(btnSubmit);
		
		
		
		//========================== Main Panel=============================
		
		tADescription = new JTextArea();
		tADescription.setForeground(new Color(0, 0, 0));
		tADescription.setFont(new Font("Lucida Bright", Font.BOLD, 20));
		
		if(p.ptype instanceof Books)
		{
			Books b=(Books) p.ptype;
			tADescription.setText("Cost: "+Integer.toString(p.cost)+"\n\nCategory: Books"+"\n\nISBN: "+b.isbn+"\n\nAuthor: "+b.author);
		}
		
		else if(p.ptype instanceof Electronics)
		{
			Electronics e=(Electronics) p.ptype;
			tADescription.setText("Cost: "+Integer.toString(p.cost)+"\n\nCategory: Electronics"+"\n\nType: "+e.type+"\n\nBrand: "+e.brand+"\n\nDurability: "+e.durability);
		}
		
		else if(p.ptype instanceof BeautyandHealth)
		{
			BeautyandHealth b=(BeautyandHealth) p.ptype;
			tADescription.setText("Cost: "+Integer.toString(p.cost)+"\n\nCategory: Beauty and Healthcare"+"\n\nBrand: "+b.brand+"\n\nNetWt: "+b.netwt);
		}
		
		else if(p.ptype instanceof Clothing)
		{
			Clothing c=(Clothing) p.ptype;
			tADescription.setText("Cost: "+Integer.toString(p.cost)+"\n\nCategory: Clothing"+"\n\nBrand: "+c.brand+"\n\nMaterial: "+c.material);
		}
		
		tADescription.setLineWrap(true);
		tADescription.setWrapStyleWord(true);
		tADescription.setEditable(false);
		tADescription.setBounds(437, 77, 555, 253);
		this.add(tADescription);
		
		btnBack = new JButton("Back");
		btnBack.setFont(new Font("Bitstream Vera Sans Mono", Font.BOLD, 11));
		btnBack.setForeground(new Color(47, 79, 79));
		btnBack.setBackground(new Color(210,210,210));
		btnBack.setBounds(20, 20, 62, 38);
		this.add(btnBack);
	
		int no_of_reviews=V.size();
		
		MainInfoPanel = new JPanel();
		MainInfoPanel.setBackground(new Color(0, 191, 255));
		MainInfoPanel.setBounds(12, 357, 980, 63);
		add(MainInfoPanel);
		MainInfoPanel.setLayout(null);
		
		JLabel lblTotalReviews = new JLabel("Number of Reviews: "+Integer.toString(no_of_reviews),SwingConstants.CENTER);
		lblTotalReviews.setForeground(Color.WHITE);
		lblTotalReviews.setBounds(0, 5, 297, 58);
		MainInfoPanel.add(lblTotalReviews);
		lblTotalReviews.setFont(new Font("Dialog", Font.BOLD, 15));
		String avgrating,srating;
		try
		{
			avgrating=Double.toString(p.avg_rating*2).substring(0, 4);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			avgrating=Double.toString(p.avg_rating*2);
		}
		catch(StringIndexOutOfBoundsException e)
		{
			avgrating=Double.toString(p.avg_rating*2);
		}
			lblAverageRating = new JLabel("Average Rating: "+avgrating,SwingConstants.CENTER);
			lblAverageRating.setForeground(Color.WHITE);
			lblAverageRating.setBounds(309, 5, 340, 58);
			MainInfoPanel.add(lblAverageRating);
			lblAverageRating.setFont(new Font("Dialog", Font.BOLD, 15));
		
		
		try
		{
			srating=Double.toString(p.sellerrating).substring(0, 4);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			srating=Double.toString(p.sellerrating);
		}
		catch(StringIndexOutOfBoundsException e)
		{
			srating=Double.toString(p.sellerrating);
		}
			
			JLabel lblNewLabel = new JLabel("Seller Rating: "+srating,SwingConstants.CENTER);
			lblNewLabel.setForeground(Color.WHITE);
			lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 15));
			lblNewLabel.setBounds(661, 5, 272, 58);
			MainInfoPanel.add(lblNewLabel);
		
		int ymax=357+63;
		ReviewThumbnail RT;
		
		for(int i=0;i<no_of_reviews;i++)
		{
			RT=new ReviewThumbnail(V.get(i));
			RT.setBounds(0,ymax,1020,100);
			this.add(RT);
			ymax=ymax+100;
		}
		WriteReview_Panel.setBounds(0, ymax, 1000, 195);
		this.add(WriteReview_Panel);
		//System.out.println(ymax);
		this.setPreferredSize(new Dimension(1020, ymax+200));
		
	    slider_rating.addChangeListener(new ChangeListener() 
	    {
	    	public void stateChanged(ChangeEvent e) 
	    	{
	    		sliderval=(float) (slider_rating.getValue()/2.0);
	    		lblRating.setText(Float.toString(sliderval));
	    	}
	    });
	}
	
	public void reviewSubmitted()
	{
		this.slider_rating.setVisible(false);
		this.lblRating.setVisible(false);
		this.tAReview.setVisible(false);
		this.btnSubmit.setVisible(false);
		lblWriteReview.setText("You have Reviewed this Product");
	}
	public double getRating()
	{
		return (this.slider_rating.getValue()/2.0);
	}
	public String getTextReview()
	{
		return(this.tAReview.getText());
	}
	public void setMainInfoPanelBackground(Color C)
	{
		MainInfoPanel.setBackground(C);
	}
	public void setPanelBackground(Color C)
	{
		this.setBackground(C);
	}
	public void setReviewPanelBackground(Color C)
	{
		WriteReview_Panel.setBackground(C);
	}
	public void setSubmitButtonBackground(Color C)
	{
		btnSubmit.setBackground(C);
	}
	public void setTextAreaBackground(Color C)
	{
		tAReview.setBackground(C);
	}
	public void setProductDescriptionBackground(Color C)
	{
		tADescription.setBackground(C);
	}
	public void setBackButtonBackground(Color C)
	{
		btnBack.setBackground(C);
	}
	public void setSliderBackground(Color C)
	{
		slider_rating.setBackground(C);
	}
//	public static void main(String args[])
//	{
//		JFrame F=new JFrame("Product Panel");
//		F.setBounds(50,500,1020,720);
//		F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		F.getContentPane().setLayout(null);
//		F.setUndecorated(true);
//		F.getContentPane().setBackground(Color.CYAN);
//		
//		String tags[]= {"cba","acc","abc"};
//		product p=new product(5,5,500,8.0,"Head and Shoulders",null,null,null,tags,5.5);
//		String myreview="Very good. Excellent GUI and backend. \n MangluDB foreva.\n";
//		String myreview2="Nice Packaging Bro! PRMS is best\n";
//		String myreview3="LOL LOL LOL LOL LOL \n LOL LOL LOL LOL LOL .";
//		myreview=myreview+myreview2+myreview3;
//		review r=new review(5,5,5,6.7,myreview,6);					
//		Vector <review>Vr=new Vector<review>();				
//		for(int i=0;i<20;i++)
//		{
//			Vr.add(r);
//		}
//		Product_Panel P=new Product_Panel(p,Vr);
//		
//		F.getContentPane().add(P);
//		F.setLocationRelativeTo(null);
//		F.setVisible(true);
//	}
}