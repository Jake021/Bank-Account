package BankAccount;

public class User {
	public final static String DATA_FILE = "data.txt";
	protected String username;
	protected String password;
	protected int uid;
	protected double checking;
	protected double savings;

	public User(String uName, String pass, double check, double save, int id) {
		super();
		this.username = uName;
		this.password = pass;
		this.uid = id;
		this.checking = check;
		this.savings = save;
	}

	@Override
	public String toString() {

		return username + " " + password + " " + checking + " " + savings + " " + uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public double getChecking() {
		return checking;
	}

	public void setChecking(double checking) {
		this.checking = checking;
	}

	public double getSavings() {
		return savings;
	}

	public void setSavings(double savings) {
		this.savings = savings;
	}

}
