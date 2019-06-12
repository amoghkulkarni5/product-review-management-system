package prms;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.Panel;

import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class LoginPage extends JFrame 
{
	private static final long serialVersionUID = 10L;
	Client C;
	int xx,xy;
	private JTextField txtUsername;
	private JPasswordField pwdEnterPassword;
	private JTextField Fullname;
	private JTextField Username;
	private JPasswordField passwordField;
	
	Socket clientsocket;
	ObjectOutputStream output;
	ObjectInputStream input;
	public LoginPage(String name,Socket clientsocket,ObjectOutputStream output,ObjectInputStream input) 
	{
		this.clientsocket=clientsocket;
		this.output=output;
		this.input=input;
		
		this.setTitle(name);
		setBackground(UIManager.getColor("ScrollBar.background"));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1020, 720);
		this.setLocationRelativeTo(null);
	    getContentPane().setLayout(null);
	    
	    JPanel blue_panel = new JPanel();
	    blue_panel.addMouseMotionListener(new MouseMotionAdapter() 
	    {
	    	int lastX;
	    	int lastY;
	    	@SuppressWarnings("unused")
			public void MousePressed(MouseEvent e)
	    	{
	    		lastX=e.getXOnScreen();
	    		lastY=e.getYOnScreen();
	    	}
	    	@Override
	    	public void mouseDragged(MouseEvent e) 
	    	{
	    		int x,y;
	    		x=e.getXOnScreen();
	    		y=e.getYOnScreen();
	    		setLocation(getLocationOnScreen().x+x-lastX ,getLocationOnScreen().y+y-lastY);
	    		lastX=x;
	    		lastY=y;
	    	}
	    });
	    blue_panel.setBackground(new Color(100, 149, 237));
	    blue_panel.setBounds(0, 0, 1309, 302);
	    
	    JPanel mainloginpanel = new JPanel();        // This panel houses 2 panels- signup and signin panel
	    mainloginpanel.setBackground(Color.WHITE);
	    mainloginpanel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new LineBorder(new Color(192, 192, 192), 2)));
	    //mainloginpanel.setBounds(350, 101, 620, 541);
	    mainloginpanel.setBounds(200, 100, 620, 541);
	    getContentPane().add(mainloginpanel);
	    mainloginpanel.setLayout(null);
	        
	    // ********************************** SignUp Panel **********************************
	        
	    JPanel signup_panel = new JPanel();
	    signup_panel.setBackground(new Color(255, 255, 255));
	    signup_panel.setBounds(10, 123, 600, 382);
	    mainloginpanel.add(signup_panel);
	    signup_panel.setLayout(null);
	    
	    JButton btnRegisterNow = new JButton("Register Now");
	    btnRegisterNow.setForeground(new Color(255, 255, 255));
	    btnRegisterNow.setBackground(new Color(100, 149, 237));
	    btnRegisterNow.setBounds(292, 296, 151, 45);
	    signup_panel.add(btnRegisterNow);
	    
	    JButton btnBack = new JButton("Back");
	    btnBack.setBackground(new Color(240, 248, 255));
	    btnBack.setBounds(152, 296, 143, 45);
	    signup_panel.add(btnBack);
	    
	    JLabel lblFullName = new JLabel("Full Name");
	    lblFullName.setBounds(125, 38, 91, 15);
	    signup_panel.add(lblFullName);
	    
	    Fullname = new JTextField();
	    Fullname.setBounds(218, 29, 244, 34);
	    signup_panel.add(Fullname);
	    Fullname.setColumns(10);
	    
	    JLabel lblUsername = new JLabel("Username");
	    lblUsername.setBounds(125, 98, 125, 15);
	    signup_panel.add(lblUsername);
	    
	    JLabel alreadytaken=new JLabel("This username is already taken. Please enter a new one.");
	    alreadytaken.setFont(new Font("Liberation Mono", Font.BOLD, 14));
	    alreadytaken.setForeground(Color.RED);
	    alreadytaken.setBounds(120, 230, alreadytaken.getPreferredSize().width, 45);
	    alreadytaken.setVisible(false);
	    signup_panel.add(alreadytaken);
	    
	    Username = new JTextField();
	    Username.setBounds(218, 89, 244, 34);
	    signup_panel.add(Username);
	    Username.setColumns(10);
	    
	    JLabel lblPassword = new JLabel("Password");
	    lblPassword.setBounds(125, 159, 113, 15);
	    signup_panel.add(lblPassword);
	    
	    passwordField = new JPasswordField();
	    passwordField.setBounds(218, 150, 244, 34);
	    signup_panel.add(passwordField);
	    
	    JRadioButton rdbtnReviewer2 = new JRadioButton("Reviewer");
	    rdbtnReviewer2.setBackground(new Color(255, 255, 255));
	    rdbtnReviewer2.setBounds(245, 218, 107, 23);
	    signup_panel.add(rdbtnReviewer2);
	    
	    JRadioButton rdbtnSeller2 = new JRadioButton("Seller");
	    rdbtnSeller2.setBackground(new Color(255, 255, 255));
	    rdbtnSeller2.setBounds(359, 218, 91, 23);
	    signup_panel.add(rdbtnSeller2);
	    signup_panel.setVisible(false);
	    
	    JLabel lblIAmA2 = new JLabel("I am a :");
	    lblIAmA2.setFont(new Font("Liberation Mono", Font.BOLD, 14));
	    lblIAmA2.setBounds(152, 207, 193, 45);
	    signup_panel.add(lblIAmA2);
	    signup_panel.setVisible(false);
	    lblIAmA2.setVisible(false);
	    
	    //*************************** SignIn Panel*************************** 
	    Panel signin_panel = new Panel();
	    signin_panel.setBounds(10, 123, 600, 382);
	    mainloginpanel.add(signin_panel);
	    signin_panel.setLayout(null);
	    signin_panel.setVisible(true);
	    
	    JLabel lblEnterUsername = new JLabel("Enter Username");
	    lblEnterUsername.setBounds(112, 68, 140, 18);
	    signin_panel.add(lblEnterUsername);
	    lblEnterUsername.setFont(new Font("Liberation Mono", Font.BOLD, 15));
	    
	    txtUsername = new JTextField();
	    txtUsername.setToolTipText(" ");
	    txtUsername.setBounds(252, 60, 206, 34);
	    signin_panel.add(txtUsername);
	    txtUsername.setColumns(10);
	    
	    JLabel lblEnterPassword = new JLabel("Enter Password");
	    lblEnterPassword.setBounds(112, 130, 126, 18);
	    signin_panel.add(lblEnterPassword);
	    lblEnterPassword.setFont(new Font("Liberation Mono", Font.BOLD, 15));
	    
	    pwdEnterPassword = new JPasswordField();
	    pwdEnterPassword.setBounds(252, 118, 206, 39);
	    signin_panel.add(pwdEnterPassword);
	    
	    JLabel lblIAmA = new JLabel("I am a :");
	    lblIAmA.setFont(new Font("Liberation Mono", Font.BOLD, 14));
	    lblIAmA.setBounds(267, 187, 78, 15);
	    signin_panel.add(lblIAmA);
	    lblIAmA.setVisible(false);
	    
	    JLabel incorrect=new JLabel("Incorrect username or password. Please try again.");
	    incorrect.setFont(new Font("Liberation Mono", Font.BOLD, 14));
	    incorrect.setForeground(Color.RED);
	    incorrect.setBounds(120, 230, incorrect.getPreferredSize().width, 45);
	    incorrect.setVisible(false);
	    signin_panel.add(incorrect);
	    
	    JRadioButton rdbtnReviewer = new JRadioButton("Reviewer");
	    rdbtnReviewer.setBackground(new Color(255, 255, 255));
	    rdbtnReviewer.setBounds(184, 216, 119, 23);
	    signin_panel.add(rdbtnReviewer);
	    
	    JRadioButton rdbtnSeller = new JRadioButton("Seller");
	    rdbtnSeller.setBackground(new Color(255, 255, 255));
	    rdbtnSeller.setBounds(331, 216, 97, 23);
	    rdbtnSeller.setVisible(false);
	    signin_panel.add(rdbtnSeller);
	    
	    JButton btnSignIn = new JButton("Sign In");
	    btnSignIn.setBounds(230, 294, 147, 45);
	    signin_panel.add(btnSignIn);
	    btnSignIn.setForeground(new Color(255, 255, 255));
	    btnSignIn.setBackground(new Color(100, 149, 237));
	    
	    JButton btnSignUp = new JButton("Sign Up");
	    btnSignUp.setBounds(87, 294, 147, 45);
	    signin_panel.add(btnSignUp);
	    btnSignUp.setForeground(new Color(0, 0, 0));
	    btnSignUp.setBackground(new Color(240, 248, 255));
	    
	    JButton btnClose = new JButton("Close");
	    btnClose.setBounds(371, 294, 147, 45);
	    signin_panel.add(btnClose);
	    btnClose.setForeground(new Color(0, 0, 0));
	    btnClose.setBackground(new Color(240, 248, 255));
	    
	    
	  //------------------------ Button Action Listeners -----------------------------
	    
	    			//Signin Panel Buttons
	    btnClose.addActionListener(new ActionListener() 		//CLOSE BUTTON
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		try
	    		{
	    			output.writeObject("Close");
				} 
	    		catch (IOException e1) 
	    		{
					e1.printStackTrace();
				}
	    		System.exit(EXIT_ON_CLOSE);

	    	}
	    });
	    
	    btnSignUp.addActionListener(new ActionListener() 		//SIGNUP BUTTON
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		signin_panel.setVisible(false);
	    		signup_panel.setVisible(true);
	    	}
	    });
	    
	    btnSignIn.addActionListener(new ActionListener() 		// SIGN IN BUTTON
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		String password,operation,username; 
	    		// Get username, password
	    		username=txtUsername.getText();
	    		password= String.valueOf(pwdEnterPassword.getPassword());
	    		operation="signin";
	    		
	    		// Commenting the seller section since seller is not defined

//	    		if(rdbtnSeller.isSelected())
//	    		{
//	    			int products[] = null; //empty array for passing to constructor - won't hold anything
//	    			S=new Seller(0,username,password,operation,"",products,0.0); // all of the not required arguments are either 0 or ""
//	    			try {
//						output.writeObject(S);
//						Seller retObject=(Seller) input.readObject();
//						if(retObject.operation.equals("verified"))
//						{
//							System.out.println("user verified successfully.");
//							System.out.println(retObject.name);
//							System.out.println(retObject.rating);
//							System.out.println(retObject.sid);
//							System.out.println(retObject.username);
//							
//							//Call the Seller's UI page here.
//						}
//						else
//						{
//							incorrect.setVisible(true);
//						}
//						
//						
//					} catch (IOException | ClassNotFoundException e1) {
//						e1.printStackTrace();
//					}
//	    		}
//	    		else
//	    		{
	    			C=new Client ("",username,password,operation,0,0,0,null); // all of the non required arguments are either 0 or ""
	    			try {
						output.writeObject(C);
						Client retObject=(Client) input.readObject();
						if(retObject.operation.equals("verified"))
						{
							System.out.println("user verified successfully.");
							System.out.println(retObject.cid);
							System.out.println(retObject.level);
							System.out.println(retObject.name);
							
							new ClientGUI("Client Template",retObject,clientsocket,output,input);
							setVisible(false);
						}
						else
						{
							incorrect.setVisible(true);
						}
						
					} catch (IOException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}
//	    		}	    		
	    	}
	    });

	    
	    			//Signup Panel Buttons
	    
	    btnBack.addActionListener(new ActionListener() 			//BACK BUTTON
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		Fullname.setText("");
				Username.setText("");
				passwordField.setText("");
				rdbtnSeller2.setSelected(false);
				rdbtnReviewer2.setSelected(false);
				alreadytaken.setVisible(false);
				incorrect.setVisible(false);
	    		
	    		signup_panel.setVisible(false);
	    		signin_panel.setVisible(true);
	    	}
	    });
	    
	    btnRegisterNow.addActionListener(new ActionListener() 		//REGISTER BUTTON
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		String name,password,operation,username;
	    		// Get username,password
	    		username=Username.getText();
	    		password= String.valueOf(passwordField.getPassword());
	    		operation="register";
	    		name=Fullname.getText();
	    		

	    		// Commenting out the seller section since it is not defined
//	    		if(rdbtnSeller2.isSelected())
//	    		{
//	    			S=new Seller(name,username,password,operation);
//	    			
//	    			try {
//						output.writeObject(S);
//						Seller retObject=(Seller) input.readObject();
//						System.out.println("Username"+retObject.username);
//						
//						if(retObject.operation.equals("inserted"))
//						{
//							Fullname.setText("");
//							Username.setText("");
//							passwordField.setText("");
//							rdbtnSeller2.setSelected(false);
//							rdbtnReviewer2.setSelected(false);
//							
//							signup_panel.setVisible(false);
//				    		signin_panel.setVisible(true);
//				    		System.out.println("operation: "+retObject.operation);
//						}
//						else if(retObject.operation.equals("already_taken"))
//						{
//							alreadytaken.setVisible(true);
//						}
//						
//					} catch (IOException | ClassNotFoundException e1) {
//						e1.printStackTrace();
//					}
//	    			
//	    		}
//	    		else
//	    		{
	    			C=new Client(name,username,password,operation);
	    			try {
						output.writeObject(C);
						Client retObject=(Client) input.readObject();
						if(retObject.operation.equals("inserted"))
						{
							Fullname.setText("");
							Username.setText("");
							passwordField.setText("");
							rdbtnSeller2.setSelected(false);
							rdbtnReviewer2.setSelected(false);
							
							signup_panel.setVisible(false);
				    		signin_panel.setVisible(true);
				    		System.out.println("operation: "+retObject.operation);
						}
						else if(retObject.operation.equals("already_taken"))
						{
							alreadytaken.setVisible(true);
						}
						
					} catch (IOException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}
//	    		}
	    	}
	    });
	    //------------------------ Radio Button Action Listeners ----------------------------
	    
	    				//Signin Panel Radio Buttons
	    rdbtnSeller.addActionListener(new ActionListener() 
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		if (rdbtnReviewer.isSelected())
	    		{
	    			rdbtnReviewer.setSelected(false);
	    		}
	    	}
	    });
	    
	    rdbtnReviewer.addActionListener(new ActionListener() 
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		if (rdbtnSeller.isSelected())
	    		{
	    			rdbtnSeller.setSelected(false);
	    		}
	    	}
	    });
	    
	    rdbtnReviewer.setVisible(false);
	    
	    				//Signup Panel Radio Buttons
	    rdbtnSeller2.addActionListener(new ActionListener() 
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		if (rdbtnReviewer2.isSelected())
	    		{
	    			rdbtnReviewer2.setSelected(false);
	    		}
	    	}
	    });
	    
	    rdbtnSeller2.setVisible(false);
	    
	    rdbtnReviewer2.addActionListener(new ActionListener() 
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		if (rdbtnSeller2.isSelected())
	    		{
	    			rdbtnSeller2.setSelected(false);
	    		}
	    	}
	    });
	    
	    rdbtnReviewer2.setVisible(false);
	    
	    //------------- Main Panel, which houses signin and signup panels ------------------------
	    
	    JLabel lblProductReviewManagement = new JLabel("PRODUCT REVIEW MANAGEMENT SYSTEM");
	    lblProductReviewManagement.setForeground(UIManager.getColor("ScrollBar.darkShadow"));
	    lblProductReviewManagement.setFont(new Font("Monospaced", Font.BOLD, 24));
	    lblProductReviewManagement.setBounds(77, 51, 464, 39);
	    mainloginpanel.add(lblProductReviewManagement);
	    
	    getContentPane().add(blue_panel);
	    
	}
}
