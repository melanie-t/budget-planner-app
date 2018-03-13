
public class AccountModel extends AbstractModel {
	
	private int accountId;
	private String bankName;
	private String nickname;
	private int balance;

	
	public AccountModel() {
		super();
		
		accountId = 0;
		bankName = "";
		nickname = "";
		balance = 0;
	}

	public AccountModel(int accountId, String bankName, String nickname, int balance) {
		super();
		
		this.accountId = accountId;
		this.bankName = bankName;
		this.nickname = nickname;
		this.balance = balance;
	}
	
	// ID
	public boolean hasId(){return accountId != 0;}
	public int getId() {return accountId;}
	public void setId(int accountId) {
		setIsNewModel(accountId == 0); // ID of 0 is considered new (not saved in DB)
		this.accountId = accountId;
	}
	
	// Bank Name
	public boolean hasBankName(){return bankName != "";}
	public String getBankName() {return bankName;}
	public void setBankName(String bankName) {this.bankName = bankName;}
	
	// Nick Name
	public boolean hasNickname(){return nickname != "";}
	public String getNickname() {return nickname;}
	public void setNickname(String nickname) {this.nickname = nickname;}
	
	// Balance
	public boolean hasBalance(){return balance != 0;}
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
