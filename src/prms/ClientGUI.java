package prms;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Vector;

import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;


public class ClientGUI extends JFrame 
{
	private static final long serialVersionUID = 1L;
	Client C;
	JPanel products_panel, menu_panel, aboutus_panel, accinfo_panel, myreviews_panel, searchbar_panel;
	JPanel electronics_panel, books_panel, clothing_panel, healthcare_panel, searchproduct_panel;
	Product_Panel P;
	JScrollPane scrollPane;
	private JTextField tfSearchBar;
	String product_search,lastpanel;
	int productno;
	
	Socket clientsocket;
	ObjectOutputStream output;
	ObjectInputStream input;
	
	public ClientGUI(String name, Client C,Socket clientsocket, ObjectOutputStream output, ObjectInputStream input) throws IOException, ClassNotFoundException 
	{
		this.clientsocket=clientsocket;
		this.output=output;
		this.input=input;
		
		lastpanel="home";
		this.C=C;
		getContentPane().setBackground(UIManager.getColor("ProgressBar.foreground"));
		this.setTitle(name);
		setBackground(UIManager.getColor("ProgressBar.selectionBackground"));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1020, 720);
		this.setLocationRelativeTo(null);
	    this.setUndecorated(true);
	    getContentPane().setLayout(null);
	    
	    
	    //------- Main ScrollPane ------
	    scrollPane=new JScrollPane();
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollPane.setBounds(0, 156, 1020, 547);
	    getContentPane().add(scrollPane);
	    
	  //================================= MY ACCOUNT RELATED PANELS =================================
	    
	    //----------------------- Account Info Panel -----------------------
	    
	    accinfo_panel = new JPanel();
	    accinfo_panel.setBackground(new Color( 240, 240, 240 ));
	    accinfo_panel.setBounds(0, 156, 1020, 547);
	    getContentPane().add(accinfo_panel);
	    accinfo_panel.setVisible(false);
	    accinfo_panel.setLayout(null);
	    
	    JLabel lblMyAccount = new JLabel("My Account");
	    lblMyAccount.setForeground(Color.DARK_GRAY);
	    lblMyAccount.setFont(new Font("Monospaced", Font.BOLD, 35));
	    lblMyAccount.setBounds(396, 27, 222, 58);
	    accinfo_panel.add(lblMyAccount);
	    
	    
	   
	    
	    
	    //================================= PRODUCT RELATED PANELS =================================
	    
	    //----------------------- Searched Products Panel --------------
	    searchproduct_panel = new JPanel();
	    searchproduct_panel.setBackground(new Color( 240, 240, 240 ));
	    searchproduct_panel.setBounds(0, 156, 1020, 547);
	    searchproduct_panel.setLayout(null);
	    searchproduct_panel.setVisible(false);
	    getContentPane().add(searchproduct_panel);
	    
	    //----------------------- Products (Home) Panel -----------------------
	    
	    products_panel = new JPanel();
	    scrollPane.setViewportView(products_panel);
	    products_panel.setBackground(new Color(224, 255, 255));
	    products_panel.setVisible(true);
	    products_panel.setLayout(null);
	    
	    JLabel lblFeatured = new JLabel("Featured Products");
	    lblFeatured.setForeground(Color.DARK_GRAY);
	    lblFeatured.setFont(new Font("Monospaced", Font.BOLD, 35));
	    lblFeatured.setBounds(328, 22, 379, 58);
	    products_panel.add(lblFeatured);
	    		
	    //----------------------- My Reviews Panel -----------------------
	    
	    myreviews_panel = new JPanel();
	    myreviews_panel.setBackground(new Color( 240, 240, 240 ));
	    myreviews_panel.setBounds(0, 156, 1020, 547);
	    getContentPane().add(myreviews_panel);
	    myreviews_panel.setVisible(false);
	    myreviews_panel.setLayout(null);
	    
	    JLabel lblMyReviews = new JLabel("My Reviews");
	    lblMyReviews.setForeground(Color.DARK_GRAY);
	    lblMyReviews.setFont(new Font("Monospaced", Font.BOLD, 35));
	    lblMyReviews.setBounds(391, 22, 316, 58);
	    myreviews_panel.add(lblMyReviews);
	    
	  //---------------------  Electronics Panel -----------------------
	    electronics_panel = new JPanel();
	    electronics_panel.setBackground(UIManager.getColor("MenuBar.background"));
	    electronics_panel.setBounds(0, 0, 1020, 547);
	    getContentPane().add(electronics_panel);
	    electronics_panel.setLayout(null);
	    electronics_panel.setVisible(false);
	    
	    JLabel lblElectronics = new JLabel("Electronics");
	    lblElectronics.setForeground(Color.DARK_GRAY);
	    lblElectronics.setFont(new Font("Monospaced", Font.BOLD, 35));
	    lblElectronics.setBounds(381, 22, 326, 58);
	    electronics_panel.add(lblElectronics);
	    
	  //------------------------ Books Panel ---------------------------
	    books_panel = new JPanel();
	    books_panel.setBackground(UIManager.getColor("MenuBar.background"));
	    books_panel.setBounds(0, 0, 1020, 547);
	    getContentPane().add(books_panel);
	    books_panel.setLayout(null);
	    books_panel.setVisible(false);
	    
	    JLabel lblBooks = new JLabel("Books");
	    lblBooks.setForeground(Color.DARK_GRAY);
	    lblBooks.setFont(new Font("Monospaced", Font.BOLD, 35));
	    lblBooks.setBounds(445, 22, 262, 58);
	    books_panel.add(lblBooks);
	    
	  //----------------------- Clothing Panel -------------------------
	    clothing_panel = new JPanel();
	    clothing_panel.setBackground(UIManager.getColor("MenuBar.background"));
	    clothing_panel.setBounds(0, 0, 1020, 547);
	    getContentPane().add(clothing_panel);
	    clothing_panel.setLayout(null);
	    clothing_panel.setVisible(false);
	    
	    JLabel lblClothing = new JLabel("Clothing");
	    lblClothing.setForeground(Color.DARK_GRAY);
	    lblClothing.setFont(new Font("Monospaced", Font.BOLD, 35));
	    lblClothing.setBounds(423, 22, 284, 58);
	    clothing_panel.add(lblClothing);
	    
	  //----------------- Beauty and Healthcare Panel ------------------
	    healthcare_panel = new JPanel();
	    healthcare_panel.setBackground(UIManager.getColor("MenuBar.background"));
	    healthcare_panel.setBounds(0, 0, 1020, 547);
	    getContentPane().add(healthcare_panel);
	    healthcare_panel.setLayout(null);
	    healthcare_panel.setVisible(false);
	    
	    JLabel lblHealthcare = new JLabel("Beauty and Healthcare");
	    lblHealthcare.setForeground(Color.DARK_GRAY);
	    lblHealthcare.setFont(new Font("Monospaced", Font.BOLD, 35));
	    lblHealthcare.setBounds(302, 22, 481, 58);
	    healthcare_panel.add(lblHealthcare);
	    
	    
	    //============================= About Us, Account Info and My Reviews' Panels =================================
	    
	    //---------------------- About Us Panel ------------------------
	    aboutus_panel=new JPanel();
	    aboutus_panel.setBackground(new Color( 240, 240, 240 ));
	    aboutus_panel.setBounds(0, 156, 1020, 547);
	    aboutus_panel.setVisible(false);
	    getContentPane().add(aboutus_panel);
	    aboutus_panel.setLayout(null);
	    
	    Image imageAmeya=ImageIO.read(getClass().getResource("/Ameya.jpg"));
	    Image newimageAmeya=imageAmeya.getScaledInstance(203, 185, Image.SCALE_SMOOTH);
	    
	    JLabel lblAboutAmeya = new JLabel("Ameya");
	    lblAboutAmeya.setFont(new Font("DejaVu Sans", Font.BOLD, 17));
	    JLabel imgAmeya=new JLabel("",new ImageIcon(newimageAmeya),JLabel.CENTER);
	    
	    lblAboutAmeya.setBounds(157, 135, 203, 185);
	    imgAmeya.setBounds(87,30,203,185);
	    aboutus_panel.add(lblAboutAmeya);
	    aboutus_panel.add(imgAmeya);
	    
	    Image imageAmogh=ImageIO.read(getClass().getResource("/Amogh.JPG"));
	    Image newimageAmogh=imageAmogh.getScaledInstance(203, 185, Image.SCALE_SMOOTH);
	    
	    
	    JLabel lblAboutAmogh = new JLabel("Amogh");
	    lblAboutAmogh.setFont(new Font("DejaVu Sans", Font.BOLD, 17));
	    JLabel imgAmogh=new JLabel("",new ImageIcon(newimageAmogh),JLabel.CENTER);
	    
	    lblAboutAmogh.setBounds(457, 135, 203, 185);
	    imgAmogh.setBounds(387,30,203,185);
	    aboutus_panel.add(lblAboutAmogh);
	    aboutus_panel.add(imgAmogh);
	    
	    Image imageKhisti=ImageIO.read(getClass().getResource("/Khisti.jpg"));
	    Image newimageKhisti=imageKhisti.getScaledInstance(203, 185, Image.SCALE_SMOOTH);
	    
	    
	    JLabel lblAboutKhisti = new JLabel("Atharva");
	    lblAboutKhisti.setFont(new Font("DejaVu Sans", Font.BOLD, 17));
	    JLabel imgKhisti=new JLabel("",new ImageIcon(newimageKhisti),JLabel.CENTER);
	    
	    lblAboutKhisti.setBounds(757, 135, 203, 185);
	    imgKhisti.setBounds(687,30,203,185);
	    aboutus_panel.add(lblAboutKhisti);
	    aboutus_panel.add(imgKhisti);
	    
	  //============================= MENU & MENU BAR RELATED OPERATIONS =================================
	    
	    //--------------------------- Menu ---------------------------
	    
	    menu_panel = new JPanel();
	    menu_panel.setBackground(UIManager.getColor("ComboBox.disabledForeground"));
	    menu_panel.setBounds(0, 85, 1035, 71);
	    getContentPane().add(menu_panel);
	    menu_panel.setLayout(null);
	    
	    JMenuBar Menubar=new JMenuBar();
	    Menubar.setBounds(0, 0, 1025, 71);
	    
	    JMenu mnAccountInfo = new JMenu("    My Account    ");
	    mnAccountInfo.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
	    mnAccountInfo.setForeground(new Color(255, 255, 255));
	    mnAccountInfo.setBounds(35, 45, 95, 19);
	    
	    JMenuItem mntmMyReviews = new JMenuItem("My Reviews");
	    mntmMyReviews.setFont(new Font("Dialog", Font.BOLD, 15));
	    mntmMyReviews.setForeground(UIManager.getColor("ScrollBar.track"));
	    mntmMyReviews.setBackground(UIManager.getColor("Separator.foreground"));
	    mnAccountInfo.add(mntmMyReviews);
	    
	    JMenuItem mntmAccInfo = new JMenuItem("Account Info");
	    mntmAccInfo.setFont(new Font("Dialog", Font.BOLD, 15));
	    mntmAccInfo.setForeground(UIManager.getColor("ScrollPane.background"));
	    mntmAccInfo.setBackground(UIManager.getColor("Separator.foreground"));
	    mnAccountInfo.add(mntmAccInfo);
	    
	    JMenuItem mntmLogout = new JMenuItem("Logout");
	    mntmLogout.setFont(new Font("Dialog", Font.BOLD, 15));
	    mntmLogout.setForeground(UIManager.getColor("ScrollPane.background"));
	    mntmLogout.setBackground(UIManager.getColor("Separator.foreground"));
	    mnAccountInfo.add(mntmLogout);
	    
	    menu_panel.add(Menubar);
	    Menubar.setForeground(new Color(255, 255, 255));
	    Menubar.setBackground(new Color(100, 149, 237));
	    Menubar.add(mnAccountInfo);
	    
	    JMenu mnProductCatalogMenu = new JMenu("    Product Catalog    ");
	    mnProductCatalogMenu.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
	    mnProductCatalogMenu.setForeground(new Color(255, 255, 255));
	    
	    Menubar.add(mnProductCatalogMenu);
	    
	    JMenuItem mntmHome = new JMenuItem("Home");
	    mntmHome.setForeground(UIManager.getColor("ScrollPane.background"));
	    mntmHome.setFont(new Font("Dialog", Font.BOLD, 15));
	    mntmHome.setBackground(UIManager.getColor("Separator.foreground"));
	    mnProductCatalogMenu.add(mntmHome);
	    
	    JMenuItem mntmElectronics = new JMenuItem("Electronics");
	    mntmElectronics.setForeground(UIManager.getColor("ScrollPane.background"));
	    mntmElectronics.setFont(new Font("Dialog", Font.BOLD, 15));
	    mntmElectronics.setBackground(UIManager.getColor("Separator.foreground"));
	    mnProductCatalogMenu.add(mntmElectronics);
	    
	    JMenuItem mntmBeautyAndHealthcare = new JMenuItem("Beauty and Healthcare");
	    mntmBeautyAndHealthcare.setForeground(UIManager.getColor("ScrollPane.background"));
	    mntmBeautyAndHealthcare.setFont(new Font("Dialog", Font.BOLD, 15));
	    mntmBeautyAndHealthcare.setBackground(UIManager.getColor("Separator.foreground"));
	    mnProductCatalogMenu.add(mntmBeautyAndHealthcare);
	    
	    JMenuItem mntmBooks = new JMenuItem("Books");
	    mntmBooks.setForeground(UIManager.getColor("Separator.background"));
	    mntmBooks.setFont(new Font("Dialog", Font.BOLD, 15));
	    mntmBooks.setBackground(UIManager.getColor("Separator.foreground"));
	    mnProductCatalogMenu.add(mntmBooks);
	    
	    JMenuItem mntmClothing = new JMenuItem("Clothing");
	    mntmClothing.setForeground(UIManager.getColor("ScrollPane.background"));
	    mntmClothing.setFont(new Font("Dialog", Font.BOLD, 15));
	    mntmClothing.setBackground(UIManager.getColor("Separator.foreground"));
	    mnProductCatalogMenu.add(mntmClothing);
	    
	    searchbar_panel = new JPanel();
	    searchbar_panel.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
	    Menubar.add(searchbar_panel);
	    searchbar_panel.setLayout(null);
	    
	    tfSearchBar = new JTextField();
	    tfSearchBar.setBounds(35, 18, 262, 33);
	    searchbar_panel.add(tfSearchBar);
	    
	    JButton btnSearch = new JButton("");
	    btnSearch.setBounds(325, 18, 33, 33);
	    URL urlSearch=getClass().getResource("/SearchButton.png");
	    Image imagesearch=ImageIO.read(urlSearch).getScaledInstance(btnSearch.getWidth(), btnSearch.getHeight(), Image.SCALE_SMOOTH);
	    btnSearch.setIcon(new ImageIcon(imagesearch));	    
	    searchbar_panel.add(btnSearch);
	    
	    JMenu nmAboutUs = new JMenu("         About Us         ");
	    nmAboutUs.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
	    nmAboutUs.setForeground(new Color(255, 255, 255));
	    Menubar.add(nmAboutUs);
	    
	    JMenu mnClose = new JMenu("        Close        ");
	    mnClose.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
	    Menubar.add(mnClose);
	    mnClose.setForeground(new Color(255, 255, 255));
	    
	    
	    
	    
	  //============================================= Menu Bar Action Listeners =============================================
	    
	    
	    
	    //-------------------------MY ACCOUNT MENU ACTION LISTENERS --------------------------
	    
	    mntmMyReviews.addActionListener(new ActionListener()  //My Account-> My reviews
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		products_panel.setVisible(false);
	    		aboutus_panel.setVisible(false);
	    		accinfo_panel.setVisible(false);
	    		myreviews_panel.setVisible(true);
	    		electronics_panel.setVisible(false);
	    		books_panel.setVisible(false);
	    		clothing_panel.setVisible(false);
	    		healthcare_panel.setVisible(false);
	    		searchbar_panel.setVisible(true);
	    		lastpanel="myreviews";
	    		scrollPane.setViewportView(myreviews_panel);
	    	}
	    });
	    
	    mntmAccInfo.addActionListener(new ActionListener()  //My Account-> Account Info
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		products_panel.setVisible(false);
	    		aboutus_panel.setVisible(false);
	    		accinfo_panel.setVisible(true);
	    		myreviews_panel.setVisible(false);
	    		electronics_panel.setVisible(false);
	    		books_panel.setVisible(false);
	    		clothing_panel.setVisible(false);
	    		healthcare_panel.setVisible(false);
	    		searchbar_panel.setVisible(true);
	    		lastpanel="accinfo";
	    		scrollPane.setViewportView(accinfo_panel);
	    	}
	    });
	    
	    mntmLogout.addActionListener(new ActionListener()  //My Account-> Log Out
	    	    {
	    	    	public void actionPerformed(ActionEvent e) 
	    	    	{
	    	    		LoginPage loginpageobject=new LoginPage("Product Review Management System",clientsocket,output,input);
	    	    		setVisible(false);
	    	    		loginpageobject.setUndecorated(true);
	    				loginpageobject.setVisible(true);
	    				
	    	    	}
	    	    });
	   

	    //-------------------------PRODUCT MENU-ITEMS ACTION LISTENERS --------------------------
	    
	    mntmHome.addActionListener(new ActionListener()  //Products->Home
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		products_panel.setVisible(true);
	    		aboutus_panel.setVisible(false);
	    		accinfo_panel.setVisible(false);
	    		myreviews_panel.setVisible(false);
	    		electronics_panel.setVisible(false);
	    		books_panel.setVisible(false);
	    		clothing_panel.setVisible(false);
	    		healthcare_panel.setVisible(false);
	    		searchbar_panel.setVisible(true);
	    		lastpanel="home";
	    		scrollPane.setViewportView(products_panel);
	    	}
	    });
	    
	    mntmClothing.addActionListener(new ActionListener()  //Products->Clothing
	    {
	    	@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) 
	    	{
	    		Vector<Product> V=null;
	    		try {
					output.writeObject(new Product(0,0,"","toptwelve",null,"clothing",null,0));
					V=(Vector<Product>) input.readObject();
			    	setPanel(clothing_panel,V);
					
				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
	    		products_panel.setVisible(false);
	    		aboutus_panel.setVisible(false);
	    		accinfo_panel.setVisible(false);
	    		myreviews_panel.setVisible(false);
	    		electronics_panel.setVisible(false);
	    		books_panel.setVisible(false);
	    		clothing_panel.setVisible(true);
	    		healthcare_panel.setVisible(false);
	    		searchbar_panel.setVisible(true);
	    		lastpanel="clothing";
	    		scrollPane.setViewportView(clothing_panel);
	    	}
	    });
	    
	    mntmBooks.addActionListener(new ActionListener()  //Products->Books
	    {
	    	@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) 
	    	{
	    		Vector<Product> V=null;
	    		try {
					output.writeObject(new Product(0,0,"","toptwelve",null,"books",null,0));
					V=(Vector<Product>) input.readObject();
			    	setPanel(books_panel,V);
					
				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
	    		
	    		products_panel.setVisible(false);
	    		aboutus_panel.setVisible(false);
	    		accinfo_panel.setVisible(false);
	    		myreviews_panel.setVisible(false);
	    		electronics_panel.setVisible(false);
	    		books_panel.setVisible(true);
	    		clothing_panel.setVisible(false);
	    		healthcare_panel.setVisible(false);
	    		searchbar_panel.setVisible(true);
	    		lastpanel="books";
	    		scrollPane.setViewportView(books_panel);
	    	}
	    });
	    
	    mntmBeautyAndHealthcare.addActionListener(new ActionListener()  //Products-> Beauty and Healthcare
	    {
	    	@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) 
	    	{
	    		Vector<Product> V=null;
	    		try {
					output.writeObject(new Product(0,0,"","toptwelve",null,"beautyandhealth",null,0));
					V=(Vector<Product>) input.readObject();
			    	setPanel(healthcare_panel,V);
					
				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
	    		
	    		products_panel.setVisible(false);
	    		aboutus_panel.setVisible(false);
	    		accinfo_panel.setVisible(false);
	    		myreviews_panel.setVisible(false);
	    		electronics_panel.setVisible(false);
	    		books_panel.setVisible(false);
	    		clothing_panel.setVisible(false);
	    		healthcare_panel.setVisible(true);
	    		searchbar_panel.setVisible(true);
	    		lastpanel="beauty";
	    		scrollPane.setViewportView(healthcare_panel);
	    	}
	    });
	    
	    mntmElectronics.addActionListener(new ActionListener()  //Products->Electronics
	    {
	    	@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) 
	    	{
	    		Vector<Product> V=null;
	    		try {
					output.writeObject(new Product(0,0,"","toptwelve",null,"electronics",null,0));
					V=(Vector<Product>) input.readObject();
			    	setPanel(electronics_panel,V);
					
				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
	    		
	    		products_panel.setVisible(false);
	    		aboutus_panel.setVisible(false);
	    		accinfo_panel.setVisible(false);
	    		myreviews_panel.setVisible(false);
	    		electronics_panel.setVisible(true);
	    		books_panel.setVisible(false);
	    		clothing_panel.setVisible(false);
	    		healthcare_panel.setVisible(false);
	    		searchbar_panel.setVisible(true);
	    		lastpanel="electronics";
	    		scrollPane.setViewportView(electronics_panel);
	    	}
	    });
	    
	    //-------------------------SEARCH ACTION LISTENER ---------------------------
	    
	    btnSearch.addActionListener(new SearchButtonPressed());
	    
	   //-------------------------ABOUT US ACTION LISTENER --------------------------
	    nmAboutUs.addMouseListener(new MouseAdapter() 
	    {
	    	@Override
	    	public void mouseClicked(MouseEvent e) 
	    	{
	    		products_panel.setVisible(false);
	    		aboutus_panel.setVisible(true);
	    		accinfo_panel.setVisible(false);
	    		myreviews_panel.setVisible(false);
	    		electronics_panel.setVisible(false);
	    		books_panel.setVisible(false);
	    		clothing_panel.setVisible(false);
	    		healthcare_panel.setVisible(false);
	    		searchbar_panel.setVisible(true);
	    		lastpanel="aboutus";
	    		scrollPane.setViewportView(aboutus_panel);
	    	}
	    });
	    
	  //-------------------------CLOSE ACTION LISTENER --------------------------
	    mnClose.addMouseListener(new MouseAdapter() 
	    {
	    	@Override
	    	public void mouseClicked(MouseEvent e) 
	    	{
	    		try {
					output.writeObject("Close");
				} 
	    		catch (IOException e1) {
					e1.printStackTrace();
				}
	    		System.exit(EXIT_ON_CLOSE);	    		
	    	}
	    });
	    
	    //============================================= Upper Panel =============================================
	    
	    JPanel upper_panel = new JPanel();
	    upper_panel.setBackground(UIManager.getColor("Menu.acceleratorForeground"));
	    upper_panel.setBounds(-5, 0, 1030, 86);
	    getContentPane().add(upper_panel);
	    upper_panel.setLayout(null);
	    
	    upper_panel.addMouseMotionListener(new MouseMotionAdapter() 
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
	    
	    JLabel lblProductReviewManagement = new JLabel("PRODUCT REVIEW MANAGEMENT SYSTEM");
	    lblProductReviewManagement.setBackground(UIManager.getColor("PopupMenu.background"));
	    lblProductReviewManagement.setBounds(287, 24, 464, 39);
	    upper_panel.add(lblProductReviewManagement);
	    lblProductReviewManagement.setForeground(UIManager.getColor("ProgressBar.selectionForeground"));
	    lblProductReviewManagement.setFont(new Font("Monospaced", Font.BOLD, 24));
	    
	    JLabel lblName = new JLabel(C.name);
	    lblName.setForeground(UIManager.getColor("ScrollBar.highlight"));
	    lblName.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 15));
	    lblName.setAlignmentX(CENTER_ALIGNMENT);
	    lblName.setBounds(32, 22, 205, 15);
	    upper_panel.add(lblName);
	    
	    JLabel lblLevel = new JLabel("Level: "+String.valueOf(C.level));
	    lblLevel.setForeground(UIManager.getColor("ScrollBar.highlight"));
	    lblLevel.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 11));
	    lblLevel.setBounds(32, 37, 205, 15);
	    lblLevel.setAlignmentX(CENTER_ALIGNMENT);
	    upper_panel.add(lblLevel);
	    
	    JLabel lblType = new JLabel();
	    
	    //Commenting out as seller functionality is not added
	    //if(C.reviewer)
	    	lblType.setText("Reviewer");
	    //else
	    	//lblType.setText("Seller");
	    lblType.setForeground(UIManager.getColor("ScrollBar.highlight"));
	    lblType.setAlignmentX(CENTER_ALIGNMENT);
	    lblType.setBounds(32, 49, 205, 15);
	    upper_panel.add(lblType);
	    
	    
	    URL url=getClass().getResource("/PRMS-logo.png");
	    Image productimage=ImageIO.read(url).getScaledInstance(86, 86, Image.SCALE_SMOOTH);
	    JLabel lblImage=new JLabel("",new ImageIcon(productimage),JLabel.CENTER);
	    lblImage.setBounds(918, 0, 86, 86);
	    //lblImage.setIcon(productimage);
	    upper_panel.add(lblImage);
    
	    //======================================================================================================
	    
	    JLabel lblCopyright = new JLabel("C- Amogh, Ameya, Khisti-  2018");
	    lblCopyright.setFont(new Font("DejaVu Serif", Font.PLAIN, 13));
	    lblCopyright.setForeground(UIManager.getColor("ColorChooser.foreground"));
	    lblCopyright.setBounds(807, 703, 201, 17);
	    getContentPane().add(lblCopyright);
	    
	    
	    
	    this.setIconImage(ImageIO.read(getClass().getResource("/PRMS-logo.png")));
	    this.setVisible(true);
	    
	    
	    
	    setDefaultPanelsOnStart();
	    
	}
	
	@SuppressWarnings("unchecked")
	private void setDefaultPanelsOnStart() throws IOException, ClassNotFoundException
	{
//    	String tags[]= {"face","wash","cosmetics","lotion","cream"};
//    	product test;
//    	Vector <product> V= new Vector<product>();
//    	
//    	for(int i=0;i<30;i++)
//    	{
//    		test=new product(5,5,50, 8.0,"Garnier Face Wash","addtoUI",null,"PROD_TYPE",tags,5.5);
//    		V.add(test);
//    	}

		//Send the request for top ten products from all categories
		output.writeObject(new Product(0,0,"","toptwelve",null,"all",null,0));
		Vector<Product> V=(Vector<Product>) input.readObject();
		
		output.writeObject(new Review(0,0,C.cid,0,0,"","userreviews"));
		Vector<Review> userreview=(Vector<Review>) input.readObject();
		Vector<Product> prod=new Vector<Product>();
		for(int i=0;i<userreview.size();i++)
		{
			output.writeObject(new Product(userreview.get(i).pid,0,0,0,"","searchwithpid",null,null,null,0));
			Product prod1=(Product) input.readObject();
			prod.add(prod1);
		}
		setReviewPanel(prod,userreview);
		 
		 setAccountPanel();
    	
    	setPanel(products_panel,V);
//    	setPanel(electronics_panel,V);
//    	setPanel(books_panel,V);
//    	setPanel(clothing_panel,V);
//    	setPanel(healthcare_panel,V);
	}
	
	public String Search_Product()
	{
		return this.product_search;
	}
	
	public void setAccountPanel()
	{
		JTextArea txt=new JTextArea();
		txt.setForeground(new Color(0, 0, 0));
		txt.setFont(new Font("Lucida Bright", Font.CENTER_BASELINE, 25));
		
		txt.setText("\n\tName: "+C.name+"\n\n\tUsername: "+C.username+"\n\n\tLevel: "+C.level+"\n\n\tTotal products reviewed: "+C.no_of_reviews);
		
		txt.setLineWrap(true);
		txt.setWrapStyleWord(true);
		txt.setEditable(false);
		txt.setBackground(UIManager.getColor("Separator.foreground"));
		txt.setForeground(new Color(255,255,255));
		accinfo_panel.setBounds(0, 0, 750, 300);
		
		txt.setBounds((int)(accinfo_panel.getWidth()/4.5), (int)(accinfo_panel.getHeight()/2.5), 750, 300);
		
		accinfo_panel.setBackground(new Color(255,255,255));
		accinfo_panel.add(txt);
		accinfo_panel.setPreferredSize(new Dimension(accinfo_panel.getBounds().width,accinfo_panel.getBounds().height));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
	}
	
	
	public void setReviewPanel(Vector <Product> P,Vector <Review> R)
	{
		ReviewThumbnail RT;
		int x=0,y=100,width=1020,height=100;
	
		for (int i=0;i<R.size();i++)
		{
			RT=new ReviewThumbnail(P.get(i),R.get(i));
			RT.setBounds(x,y,width,height);
			RT.setBackground(new Color( 240, 240, 240 ));
			RT.setTextAreaBackground(new Color( 240, 240, 240 ));
			if(i%2==0)
			{
				RT.setBackground(Color.WHITE);
				RT.setTextAreaBackground(Color.WHITE);
			}
			myreviews_panel.add(RT);
			y=y+height;
		}
		
		myreviews_panel.setPreferredSize(new Dimension(myreviews_panel.getBounds().width,y));
	    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
	}
	public void setPanel(JPanel P, Vector <Product> V)
	{  	
		//---  Sets the panel passed in parameter with the products in the Vector ---
	    int x,y,xspacing,yspacing,side;
	    x=110; y=100; xspacing=300; yspacing=300; side=200;
	    int counter=V.size();
	    productno=0;
    	ProductThumbnail  obj;
	    for(int i=0; counter>0 ;i=i++)
	    {
	    	x=110;
	    	for(int j=0;j<3;j++)
	    	{
				obj= new ProductThumbnail( V.get(productno) );
				productno++;
				obj.setDimensions(x, y, side, side/4);
				obj.changeBackgroundColor(Color.WHITE);
				obj.changeTextColour(Color.BLACK);
				obj.addMouseListener(new MouseAdapter() {
					@SuppressWarnings("unchecked")
					@Override
					public void mouseClicked(MouseEvent e) 
					{
						ProductThumbnail obj=(ProductThumbnail)e.getSource();
						Product P=obj.getProduct();
						
						//*********************************************
						//-------- Accept Vector Here ---------
						//*********************************************
						
						Vector<Review> Vr=null;
						try {
							output.writeObject(new Review(P.pid,0,0,0,0,"","productreviews"));
							Vr=(Vector<Review>) input.readObject();
						} catch (IOException | ClassNotFoundException e1) {
							e1.printStackTrace();
						}
						 
						//--------- DUMMY DATA ----------------
//						String myreview="Very good. Excellent GUI and backend. \n MangluDB foreva.\n";
//						String myreview2="Nice Packaging Bro! PRMS is best\n";
//						String myreview3="LOL LOL LOL LOL LOL \n LOL LOL LOL LOL LOL .";
//						myreview=myreview+myreview2+myreview3;
//						Review r=new Review(5,5,5,6.7,myreview,6);					
//						Vector <review>Vr=new Vector<review>();				
//						for(int i=0;i<20;i++)
//						{
//							Vr.add(r);
//						}
						showProductPanel(P,Vr);
					}
					
				});
				P.add(obj);
				counter--;
				if(counter==0) break;
				x=x+xspacing;
	    	}
	    	y=y+yspacing;
	    }
	    
	    P.setPreferredSize(new Dimension(P.getBounds().width,y));
	    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
	    P.setBackground(new Color( 240, 240, 240 ));
	}
	
	public void showProductPanel(Product Pro, Vector<Review> V)
	{
		P=new Product_Panel(Pro,V);
		P.setBackButtonBackground(new Color( 245, 245, 245));
		P.setPanelBackground(new Color( 250, 250, 250 ));
		P.setReviewPanelBackground(new Color(100, 149, 237));
		P.setTextAreaBackground(new Color( 245, 245, 245));
		P.setSubmitButtonBackground(UIManager.getColor("ComboBox.disabledForeground"));
		P.setSliderBackground(new Color(100, 149, 237));
		P.setMainInfoPanelBackground(new Color(100, 149, 237));
		
		P.btnBack.addActionListener(new BackButtonListener());
		
		// Check if the user has already reviewed this product
		if(C.isReviewed.contains(Pro.pid))
		{
			System.out.println("true");
			P.reviewSubmitted();
		}
		
		
		else
		{
			P.btnSubmit.addActionListener(new ActionListener()
			{

				@SuppressWarnings("unchecked")
				@Override
				public void actionPerformed(ActionEvent e) 
				{			
				int pid,sid,cid;
				double rating;
				String text;
				int clientlevel;
				
				cid=C.cid;
				pid=Pro.pid;
				sid=Pro.sid;
				rating= P.getRating();
				text=P.getTextReview();
				clientlevel=C.level;
				Review r=new Review(pid,sid,cid,rating,clientlevel,text,"addreview");
				
				System.out.println(pid);
				System.out.println(sid);
				System.out.println(cid);
				System.out.println(rating);
				System.out.println(text);
				System.out.println(clientlevel);
				
				String in=null;
				Product prod=null;
				try {
					output.writeObject(r);
					in=(String) input.readObject();
					
					output.writeObject(new Client ("",C.username,C.password,"signin",0,0,0,null));
					C=(Client) input.readObject();

				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
//				
//				Vector<Review> Vr=null;
//				try {
//					output.writeObject(new Review(Pro.pid,0,0,0,0,"","productreviews"));
//					Vr=(Vector<Review>) input.readObject();
//				} catch (IOException | ClassNotFoundException e1) {
//					e1.printStackTrace();
//				}
//				showProductPanel(prod, Vr);
				P.btnSubmit.setEnabled(false);
				}
				
			});
		}
		
		
		
		products_panel.setVisible(false);
		aboutus_panel.setVisible(false);
		accinfo_panel.setVisible(false);
		myreviews_panel.setVisible(false);
		electronics_panel.setVisible(false);
		books_panel.setVisible(false);
		clothing_panel.setVisible(false);
		healthcare_panel.setVisible(false);
		searchbar_panel.setVisible(true);
		P.setVisible(true);
		scrollPane.setViewportView(P);
	}
	
	//------------------------------ Action Listener Class for Searching --------------------------------
	class SearchButtonPressed implements ActionListener
	{
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e) 
		{
    		product_search= tfSearchBar.getText();			// String to be Searched
    		
    		/*--------------------------------------------
    		 * Ameya's Socket Code to get the Vector of Products
    		 * --------------------------------------------
    		 */
    		Vector<Product> V=null;
    		try {
    			// Send a product object where the product_search is the string to be searched. Operation is "search"
				output.writeObject(new Product(0,0,product_search,"search",null,null,null,0));
				V=(Vector<Product>) input.readObject();
			} 
    		catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}    		
    		
    		
    		
    		//--- Temporary Data (To be Deleted) ---
    		System.out.println(V.get(0).pname);
    		setPanel(searchproduct_panel,V);
    		
    		// --------- Show the results -----------
    		products_panel.setVisible(false);
    		aboutus_panel.setVisible(false);
    		accinfo_panel.setVisible(false);
    		myreviews_panel.setVisible(false);
    		electronics_panel.setVisible(false);
    		books_panel.setVisible(false);
    		clothing_panel.setVisible(false);
    		healthcare_panel.setVisible(false);
    		searchbar_panel.setVisible(true);
    		lastpanel="search";
    		scrollPane.setViewportView(searchproduct_panel);
    		searchproduct_panel.setVisible(true);
			
		}
		
	}
	class BackButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(lastpanel.equals("beauty"))
			{
	    		products_panel.setVisible(false);
	    		aboutus_panel.setVisible(false);
	    		accinfo_panel.setVisible(false);
	    		myreviews_panel.setVisible(false);
	    		electronics_panel.setVisible(false);
	    		books_panel.setVisible(false);
	    		clothing_panel.setVisible(false);
	    		healthcare_panel.setVisible(true);
	    		searchbar_panel.setVisible(true);
	    		lastpanel="beauty";
	    		scrollPane.setViewportView(healthcare_panel);
			}
			else if (lastpanel.equals("books"))
			{
	    		products_panel.setVisible(false);
	    		aboutus_panel.setVisible(false);
	    		accinfo_panel.setVisible(false);
	    		myreviews_panel.setVisible(false);
	    		electronics_panel.setVisible(false);
	    		books_panel.setVisible(true);
	    		clothing_panel.setVisible(false);
	    		healthcare_panel.setVisible(false);
	    		searchbar_panel.setVisible(true);
	    		lastpanel="books";
	    		scrollPane.setViewportView(books_panel);
			}
			else if (lastpanel.equals("home"))
			{
	    		products_panel.setVisible(true);
	    		aboutus_panel.setVisible(false);
	    		accinfo_panel.setVisible(false);
	    		myreviews_panel.setVisible(false);
	    		electronics_panel.setVisible(false);
	    		books_panel.setVisible(false);
	    		clothing_panel.setVisible(false);
	    		healthcare_panel.setVisible(false);
	    		searchbar_panel.setVisible(true);
	    		lastpanel="home";
	    		scrollPane.setViewportView(products_panel);
			}
			else if (lastpanel.equals("clothing"))
			{
	    		products_panel.setVisible(false);
	    		aboutus_panel.setVisible(false);
	    		accinfo_panel.setVisible(false);
	    		myreviews_panel.setVisible(false);
	    		electronics_panel.setVisible(false);
	    		books_panel.setVisible(false);
	    		clothing_panel.setVisible(true);
	    		healthcare_panel.setVisible(false);
	    		searchbar_panel.setVisible(true);
	    		lastpanel="clothing";
	    		scrollPane.setViewportView(clothing_panel);
			}
			else if (lastpanel.equals("electronics"))
			{
	    		products_panel.setVisible(false);
	    		aboutus_panel.setVisible(false);
	    		accinfo_panel.setVisible(false);
	    		myreviews_panel.setVisible(false);
	    		electronics_panel.setVisible(true);
	    		books_panel.setVisible(false);
	    		clothing_panel.setVisible(false);
	    		healthcare_panel.setVisible(false);
	    		searchbar_panel.setVisible(true);
	    		lastpanel="electronics";
	    		scrollPane.setViewportView(electronics_panel);
			}
			else if (lastpanel.equals("search"))
			{
	    		products_panel.setVisible(false);
	    		aboutus_panel.setVisible(false);
	    		accinfo_panel.setVisible(false);
	    		myreviews_panel.setVisible(false);
	    		electronics_panel.setVisible(false);
	    		books_panel.setVisible(false);
	    		clothing_panel.setVisible(false);
	    		healthcare_panel.setVisible(false);
	    		searchbar_panel.setVisible(true);
	    		lastpanel="search";
	    		scrollPane.setViewportView(searchproduct_panel);
			}
		}
		
	}
	
//	public static void main(String args[]) throws IOException
//	{
//		Client C=new Client(312,"Amogh Kulkarni","amogh5","abcd","display",5,true);
//		@SuppressWarnings("unused")
//		ClientGUI Test=new ClientGUI("Client Template",C);
//		
//		String myreview="Very good. Excellent GUI and backend. \n10/10 will buy.\nMangluDB foreva.\n";
//		String myreview2="ABC ABC ABC \n ABC ABC ABC ABC ABC ABC ABC \n";
//		String myreview3="LOL LOL LOL LOL LOL \n LOLOLOLOLOLOLOLOOL.";
//		myreview=myreview+myreview2+myreview3;
//		String tags[]= {"cba","acc","abc"};
//		product p=new product(5,5,500,8.0,"Head and Shoulders",null,null,null,tags,5.5);
//		review r=new review(5,5,5,6.7,myreview,6);
//		
//		Vector <product>Vp=new Vector<product>();
//		Vector <review>Vr=new Vector<review>();
//		
//		for(int i=0;i<20;i++)
//		{
//			Vp.add(p);
//			Vr.add(r);
//		}
//		
//		Test.setReviewPanel(Vp,Vr);
//	    Test.setDefaultPanelsOnStart();
//	}
}
