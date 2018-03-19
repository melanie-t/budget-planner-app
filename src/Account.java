
public class Account {
	
	private int accountId;
	private String bankName;
	private String nickname;
	private int balance;

    private static int nextId;
    public static void initNextId(Integer nextId) {Account.nextId = nextId;}
    public static Integer getNextId() { return Account.nextId++; }

	public Account() {
        this(0,"","",0);
	}

	public Account(int accountId, String bankName, String nickname, int balance) {
		super();
		this.accountId = accountId;
		this.bankName = bankName;
		this.nickname = nickname;
		this.balance = balance;
	}

    public void updateWith(Account other)
    {
        bankName = other.getBankName();
        nickname = other.getNickname();
        balance = other.getBalance();
    }

	// ID
	public int getId() {return accountId;}
	public void setId(int accountId) {
		this.accountId = accountId;
	}
	
	public String getBankName() {return bankName;}
	public void setBankName(String bankName) {this.bankName = bankName;}
	
	public String getNickname() {return nickname;}
	public void setNickname(String nickname) {this.nickname = nickname;}
	
	public int getBalance() {return balance;} // this should probably be calculated from the transactions instead of being an attribute
	public void setBalance(int balance) {this.balance = balance;}
	
	// This is what does the "viewing"
	public String toString(){
		String output = "";
		output += "\n-------------------\n";
		output += "ID"+" -> " + this.getId() + "\n";
		output += "BankName"+" -> " + this.getBankName() + "\n";
		output += "NickName"+" -> " + this.getNickname() + "\n";
		output += "Balance"+" -> " + this.getBalance() + "\n";
		return output;
	}
}
