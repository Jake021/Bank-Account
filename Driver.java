package BankAccount;

public class Driver {


	public static void main(String[] args) {
		
		LoginScreen login = new LoginScreen("Login Screen");
		login.setLocationRelativeTo(null);
		login.setVisible(true);
	}

}
/*****************************************************************
 * Always run from driver now or the program will not run at all.*
 ****************************************************************/

/*****************************************************************************************
* Created By: Nathan Traylor, Tom McDonald, and Jacob Gerval							 *
* This is a bank account program that will take a username and password.				 *
* The program will allow the user to transfer, add funds, view account, and the history. *
******************************************************************************************/
