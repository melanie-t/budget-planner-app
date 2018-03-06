
public class AccountModel extends AbstractModel {
	
	int accountId;
	String bankName;
	String nickname;
	int balance;
	
	public AccountModel() {
		super();
		
		accountId = 0;
		bankName = "";
		nickname = "";
		balance = 0;
	}
	
	// ID
	public boolean hasId(){return accountId != 0;}
	public int getId() {return accountId;}
	public void SetId(int accountId) {
		setIsNewModel(accountId == 0); // ID of 0 is considered new (not saved in DB)
		this.accountId = accountId;
	}
	
	// Bank Name
	public boolean hasBankName(){return bankName != "";}
	public String getBankName() {return bankName;}
	public void setBankName(String bankName) {this.bankName = bankName;}
	
	// Nick Name
	public boolean hasNickName(){return nickname != "";}
	public String getNickName() {return nickname;}
	public void setNickName(String nickname) {this.nickname = nickname;}
	
	// Balance
	public boolean hasBalance(){return balance != 0;}
	public int getBalance() {return balance;} // this should probably be calculated from the transactions instead of being an attribute
	public void setBalance(int balance) {this.balance = balance;}
	
}
