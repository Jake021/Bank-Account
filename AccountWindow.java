package BankAccount;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import java.util.ArrayList;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.Button;
import javax.swing.JComboBox;
import javax.swing.JTextField;


public class AccountWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L; //added to get rid of warning
	private JTextArea outputAreaChecking = new JTextArea();
	private JTextArea outputAreaSavings = new JTextArea();
	public final static String DATA_FILE = "data.txt";
	public final static String ICON = "icon.png";
	private JTextField addFundsTextfield;
	private JTextField textField;	
	private String[] comboOpt = {"Chk", "Sav"};
	private JComboBox<?> comboBox_one = new JComboBox<Object>(comboOpt);
	private JComboBox<?> comboBox_two = new JComboBox<Object>(comboOpt);
	private User user;
	private JTextArea midTextArea = new JTextArea();
	JComboBox<Object> addFundsComboBox;
	ArrayList<User> userList = new ArrayList<User>();
	DecimalFormat dec = new DecimalFormat("#0.00"); //Formatting for the dollar amount
	private JTextArea historyTextArea = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane();
	

	public AccountWindow(User user, ArrayList<User> userlist) {
		
		super();	
		this.user = user;
		setIconImage(Toolkit.getDefaultToolkit().getImage(ICON));
		this.userList = userlist;
		midTextArea.setText("");
		String welcome = "Welcome " +user.getUsername() + " to Cycle Credit online banking.\n";

		midTextArea.setText(welcome);
		
		//**********JFrame creation**********//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550,290);
		setTitle("Account Window");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		panel.add(tabbedPane, BorderLayout.NORTH);
		//**********End of JFrame creation**********//
		
		//**********Start of Account Summary tab**********//
		Panel accountSummary = new Panel();
		tabbedPane.addTab("Account Summary", null, accountSummary, null);
		accountSummary.setLayout(new BorderLayout(0, 0));
		JPanel accountTopPanel = new JPanel();
		accountSummary.add(accountTopPanel, BorderLayout.NORTH);
		
		JLabel checkinglbl = new JLabel("Checking Account");
		accountTopPanel.add(checkinglbl);
		accountTopPanel.add(outputAreaChecking);
		
		updateFile(DATA_FILE);	
		
		outputAreaChecking.setText(String.valueOf("$" + dec.format(user.getChecking())));
		JPanel accountMiddlePanel = new JPanel();
		accountSummary.add(accountMiddlePanel, BorderLayout.CENTER);
		JLabel savingslbl = new JLabel("Savings Account");
		accountMiddlePanel.add(savingslbl);
		accountMiddlePanel.add(outputAreaSavings);
		
		outputAreaSavings.setText("$" + String.valueOf(dec.format(user.getSavings())));
		
		JPanel accountLowerPanel = new JPanel();
		accountSummary.add(accountLowerPanel, BorderLayout.SOUTH);
		
		JLabel lblAccountNumber = new JLabel("Account Number:");
		accountLowerPanel.add(lblAccountNumber);
		
		JTextArea accountNumberTextArea = new JTextArea();
		accountLowerPanel.add(accountNumberTextArea);
		accountNumberTextArea.setText(String.valueOf((user.getUid())));
		//**********End of Account Summary tab**********//
				
		//**********Start of Transfer Panel**********//
		Panel Transfer = new Panel();
		tabbedPane.addTab("Transfer", null, Transfer, null);
		Transfer.setLayout(new BorderLayout(0, 0));
		
		JPanel TransferTopPanel = new JPanel();
		Transfer.add(TransferTopPanel, BorderLayout.NORTH);
		
		JLabel TransferLBTop = new JLabel("Which account are you transfering from");
		TransferTopPanel.add(TransferLBTop);
		
		//Combo box
		TransferTopPanel.add(comboBox_one);
		
		JLabel lblTo = new JLabel("to");
		TransferTopPanel.add(lblTo);
		
		
		TransferTopPanel.add(comboBox_two);
		comboBox_two.setSelectedIndex(1);
		JPanel TransferBottomPanel = new JPanel();
		Transfer.add(TransferBottomPanel, BorderLayout.CENTER);
		
		JLabel lblNewLabel_2 = new JLabel("Enter the amount you wish to transfer: $");
		TransferBottomPanel.add(lblNewLabel_2);
		textField = new JTextField();
		TransferBottomPanel.add(textField);
		textField.setColumns(10);
		
		//Transfer button
		JButton confirmBtn = new JButton("Transfer");
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Add) {	// transfer action
				String btn = Add.getActionCommand();
				String from = (String)comboBox_one.getSelectedItem();
				String to = (String)comboBox_two.getSelectedItem();
				
				try {
					double amt = Double.parseDouble(textField.getText());
					
					if(btn.equals("Transfer")) {
						if(from.equals("Chk") && to.equals("Sav")) {
								if(amt >= 0 && amt < user.getChecking()) {
										
										user.checking -= amt;
										user.savings += amt;
										
										replaceUser(user);
										updateFile("data.txt");
										System.out.println(userList);
										
															
										outputAreaChecking.setText(String.valueOf("$" + dec.format(user.getChecking())));
										outputAreaSavings.setText(String.valueOf("$" + dec.format(user.getSavings())));
										
										String output = "$" + dec.format(amt) + " Transfered to savings\n";
										midTextArea.setText("");
										midTextArea.setText(output);
										historyTextArea.setText(output);


								}else {
									midTextArea.setText("Invalid amount when transfering to savings.\n");
						}
						} else if(from.equals("Sav") && to.equals("Chk")) {
							if(amt >= 0 && amt < user.getSavings()) {
									
										user.checking += amt;
										user.savings -= amt;
										
										replaceUser(user);
										updateFile("data.txt");
										System.out.println(userList);
										
															
										outputAreaChecking.setText(String.valueOf("$" + dec.format(user.getChecking())));
										outputAreaSavings.setText(String.valueOf("$" + dec.format(user.savings)));
										
										String output2 = "$"+ dec.format(amt)+ " Transfered to checking.\n";
										midTextArea.setText("");
										midTextArea.setText(output2);
										historyTextArea.setText(output2);


							}else {
								midTextArea.setText("Invalid amount when transfering to checkings.\n");
							}
						}else {
							midTextArea.setText("");
							midTextArea.append("You cannot transfer from to the same account!\n");
						}
					}	
				}catch(NumberFormatException e) {
					midTextArea.setText("Please only enter numbers!\n");
				}
			}		
		});
		TransferBottomPanel.add(confirmBtn);
		//**********End of transfer panel**********//
		
		
		//**********Start of add funds panel**********//
		Panel addFunds = new Panel();
		tabbedPane.addTab("Add Funds", null, addFunds, null);
		addFunds.setLayout(new BorderLayout(0, 0));
		
		JPanel addFundsMainPanel = new JPanel();
		addFunds.add(addFundsMainPanel, BorderLayout.CENTER);
		addFundsMainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		addFundsMainPanel.add(topPanel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Enter the account you wish to add funds to: ");
		topPanel.add(lblNewLabel);
				
		String[] accounts = {"Checking", "Saving"};
		JComboBox<Object> addFundsComboBox = new JComboBox<Object>(accounts);
		addFundsComboBox.setSelectedIndex(-1);
		topPanel.add(addFundsComboBox);
		
		JPanel middlePanel = new JPanel();
		addFundsMainPanel.add(middlePanel, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Enter the amount you wish to add: $");
		middlePanel.add(lblNewLabel_1);
		
		addFundsTextfield = new JTextField();
		middlePanel.add(addFundsTextfield);
		addFundsTextfield.setColumns(10);
		
		JPanel bottomPanel = new JPanel();
		
		
		addFundsMainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		JButton addFundsButton = new JButton("Confirm");
		addFundsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					//Runs as normal unless anything besides numbers are entered.
					if(addFundsComboBox.getSelectedItem().equals("Checking")) {
		
						double checking = user.getChecking() + Double.valueOf(addFundsTextfield.getText());
						
						if(Double.valueOf(addFundsTextfield.getText()) >= 0) {
							user.setChecking(checking);
							String output = "$"+ addFundsTextfield.getText() + " added to checking, balance now: "
							+ dec.format(user.getChecking()) + "\n";
							midTextArea.setText("");
							midTextArea.setText(output);
							historyTextArea.setText(output);
							
						}else {
							midTextArea.setText("Invalid number entered. Please try again.\n");
						}
					}else if(addFundsComboBox.getSelectedItem().equals("Saving")){ 
						
						
						double savings = user.getSavings() + Double.valueOf(addFundsTextfield.getText());
						if(Double.valueOf(addFundsTextfield.getText()) >= 0) {
							user.setSavings(savings);
							String output2 = "$" + addFundsTextfield.getText() + " added to savings, balance now:  "
							+ dec.format(user.getSavings()) + "\n";
							midTextArea.setText(output2);
							historyTextArea.setText(output2);
						}else {
							midTextArea.setText("Invalid number entered. Please try again.\n");
						}
					}
						
				//Appends if non-numbers entered
				}catch(NumberFormatException ae) {
					midTextArea.setText("Invalid Entry\n");

				}
			
				replaceUser(user);
				updateFile("data.txt");
				
				double checking = user.getChecking();
				double savings = user.getSavings();
				String strCheck = String.valueOf("$" + dec.format(checking));
				String strSavings = String.valueOf("$" + dec.format(savings));
				outputAreaChecking.setText(strCheck);
				outputAreaSavings.setText(strSavings);
						
			
			}
			
		});
		bottomPanel.add(addFundsButton);
		//**********End of add funds panel**********//
		
		//**********Start of History panel**********//
		Panel History = new Panel();
		tabbedPane.addTab("Account History", null, History, null);
		History.setLayout(new BorderLayout(0, 0));		
		History.add(scrollPane, BorderLayout.CENTER);		
		scrollPane.setViewportView(historyTextArea);

		

		//**********End of History Panel**********//
		
		//**********Start of logout/quit feature**********//
		JPanel logoutPanel = new JPanel();
		JPanel midPanel = new JPanel();
		getContentPane().add(logoutPanel, BorderLayout.SOUTH);
		getContentPane().add(midPanel, BorderLayout.CENTER);
		midPanel.setLayout(new BorderLayout(0, 0));
		
		
		midPanel.add(midTextArea);
		
		Button logoutButton = new Button("Log Out"); //Logout button on all the panels.
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); //Closes the frame and terminates the program.		
				LoginScreen login = new LoginScreen("Login Screen");
				login.setLocationRelativeTo(null);
				login.setVisible(true);
				
			}
		});
		
		Button quitBtn = new Button("Quit");
		quitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		logoutPanel.add(logoutButton);
		logoutPanel.add(quitBtn);
	}
	//**********end of logout feature**********//

	
	public void updateFile(String filename) {
		String file1 = filename;
		
		try {
			
			PrintWriter file  = new PrintWriter(new FileWriter(file1));
			
			for (User string : userList) {
				file.println(string);
				
			}
				file.close();
			
		} catch(IOException e){
			System.out.println("ERROR: An error happened when writing to file!\n");
		}
	}
	
	public void replaceUser(User user) {
		
		for(int i = 0; i < userList.size(); i++) {
			if(user.getUid() == userList.get(i).getUid()) {
				userList.get(i).setChecking(user.checking);
				userList.get(i).setSavings(user.savings);
			}
		}
	}
	

	
	public User getUser() {
	
	return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	
}

