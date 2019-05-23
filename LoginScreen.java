package BankAccount;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import java.awt.Toolkit;

public class LoginScreen extends JFrame implements ActionListener {
	

	private static final long serialVersionUID = 1L; //added to get rid of warning
	private JPanel background = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JTextField userTxt = new JTextField();
	private JPasswordField passTxt = new JPasswordField(); 
	private JTextArea loginTextArea = new JTextArea();
	private BufferedImage image;
	public final static String DATA_FILE = "data.txt"; // Making this constant so we can reference it whenever.
	public final static String LOGO = "logo.png";
	public final static String ICON = "icon.png";
	private User user;
	ArrayList<User> userList = new ArrayList<User>();
	
	public LoginScreen(String title) {
		super(title);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ICON)); //Setting the application Icon to icon.png
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(484,343);
		
		getContentPane().add(background, BorderLayout.CENTER);
		background.setLayout(new GridLayout(2,1));
		background.add(topPanel);
		background.add(bottomPanel);
		
		topPanel.setLayout(new BorderLayout());
		
		JPanel Picture = new JPanel();
		topPanel.add(Picture, BorderLayout.SOUTH);
		JLabel picLabel = new JLabel(new ImageIcon(LOGO));
		Picture.add(picLabel);
		
	    
		// just text or image at the top
		bottomPanel.setLayout(new GridLayout(2,1));
		JPanel btmTop = new JPanel();
		btmTop.setLayout(new GridLayout(2,4));
		btmTop.add(new JPanel()); // blank before user name
		JLabel lblUsername = new JLabel("Username ", SwingConstants.RIGHT);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btmTop.add(lblUsername);
		userTxt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btmTop.add(userTxt);
		userTxt.setColumns(10);
		btmTop.add(new JPanel()); // blank after user text field
		btmTop.add(new JPanel()); // blank before password
		JLabel lblPassword = new JLabel("Password ", SwingConstants.RIGHT);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btmTop.add(lblPassword);
		passTxt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		btmTop.add(passTxt);
		passTxt.setColumns(10);
		btmTop.add(new JPanel()); // blank after pass text
		
		JPanel btmBtm = new JPanel();
		btmBtm.setLayout(new GridLayout(2,1));
		
		//LoginButton
		JButton LoginBtn = new JButton("Login");
		LoginBtn.addActionListener(this);
		
		JPanel btmflow = new JPanel();
		btmBtm.add(btmflow);
		btmflow.add(LoginBtn);
		// create account button
		JButton createBtn = new JButton("Create Account");
		btmflow.add(createBtn);
		createBtn.addActionListener(this);
		
		JPanel btmBtmflow = new JPanel();
		btmBtm.add(btmBtmflow);
		btmBtmflow.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		loginTextArea.setEditable(false);
		btmBtmflow.add(loginTextArea);
		
		bottomPanel.add(btmTop);
		bottomPanel.add(btmBtm);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		String nameOfCallingBtn = e.getActionCommand();

		
		if(nameOfCallingBtn.equals("Login")) {
			readFrom();
			clearTextFields();
		} else if(nameOfCallingBtn.equals("Create Account")) {
			createAccount();
			clearTextFields();
		}
		
	}
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(image, 0, 0, this);           
    }
    public void ImagePanel() {
        try {                
           image = ImageIO.read(new File("LOGO"));
        } catch (IOException ex) {
             // handle exception...
        }
     }
	// log in
	public void readFrom() {
		
		setUserList();
			try {
			
				Scanner reader = new Scanner(new FileInputStream(DATA_FILE));
				while(reader.hasNextLine()) {
					String line = reader.nextLine();
					String tokens[] = line.split(" ");
					
					String userText = userTxt.getText();
					@SuppressWarnings("deprecation")
					String passText = passTxt.getText();
			
					String username = tokens[0];
					String password = tokens[1];
					int AccountID = Integer.parseInt(tokens[4]);
					
					
					if(userText.equals(username) && passText.equals(password)) {
						
						loginTextArea.append("Welcome to Cycle Credit!\n");
						
						double checking = Double.parseDouble(tokens[2]);
						double savings = Double.parseDouble(tokens[3]);
						
						//create a new User object
						 this.user = new User(username, password, checking, savings, AccountID);
						
						//launch new window
						dispose();
						
						AccountWindow secondFrame = new AccountWindow(user, userList); // Create AccountWindow
						secondFrame.setLocationRelativeTo(null);
						secondFrame.setVisible(true); 
						
					} else {
						if(!(userText.equals(username) || passText.equals(password))) {
							String output = "Invalid password or Username. Please try again.\n";
							loginTextArea.setText("");
							loginTextArea.append(output);
							
						
						}
					}
					
				}
			        
				reader.close();
			} catch  (FileNotFoundException e) {
				
				e.printStackTrace();

		
				
			}

		}
			

	public void clearTextFields() {
		userTxt.setText("");
		passTxt.setText("");
	}
	
	public void createAccount() {

		try {
			
			PrintWriter file  = new PrintWriter(new FileWriter("data.txt", true));
			String userName = userTxt.getText();
			@SuppressWarnings("deprecation")
			String password = passTxt.getText();

			double checking = 500.0;
			double savings = 0.0;
			String output = "Your account was created. Type your login credentials again to login in.\n";
			 //Creating an account number when someone makes an account so that two accounts are not the same.
			HashSet<Integer> AccountID = new HashSet<Integer>(); //Creating the HashSet to add numbers to is.
			
			for (int i = 0; i < 1; i++) {
				Random rand = new Random();		
				int uid = rand.nextInt(9999)+1;
			    	while (AccountID.contains(uid)) { //while we have already used the number
			    		uid = (int) (Math.random() * 9999);
			    	}
			    	
			        AccountID.add(uid); // Adding the number to the HashSet and then we are able to use it in other places	     
			}
			user = new User(userName, password, checking, savings, AccountID.hashCode());
			loginTextArea.append(output);
			file.println(userName + " " + password + " " + checking + " " + savings + " " + AccountID.hashCode());
			file.close();
						
		
		} catch(IOException e){
			System.out.println("ERROR: An error occured when creating the account. Please try again.\n"
					+ "If this problem occurs again please contact the administrator.\n");
		}
	}
	// this methord reads the data file and stoes all of the info to their own User instance 
	// then adds all Users to the userList arrayList, all users kept in an arrayList to be easily modified
	public void setUserList() {
		try {
			Scanner reader = new Scanner(new FileInputStream(DATA_FILE));
			while(reader.hasNextLine()) {
				String line = reader.nextLine();
				String tokens[] = line.split(" ");

		
				String username = tokens[0];
				String password = tokens[1];
				double checking = Double.parseDouble(tokens[2]);
				double saving = Double.parseDouble(tokens[3]);
				int id = Integer.parseInt(tokens[4]);
				
				User user = new User(username, password, checking, saving, id);
				
				userList.add(user);
				
	}
			reader.close();
	}catch(Exception e) {
		System.out.println("ERROR: Error in setuserlist");
	}
	}
	
	
}