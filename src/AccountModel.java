
public class AccountModel {
	
	boolean boolNew;
	int accountId;
	String bankName;
	String nickname;
	int balance;
	
	public AccountModel() {
		boolNew = true;
		accountId = 0;
		bankName = "";
		nickname = "";
		balance = 0;
	}
	
	// ID
	public int getId() {return accountId;}
	public void SetId(int accountId) {
		this.accountId = accountId;
		this.boolNew = false;
	}
	
	// Is new
	// used to determine wither id has been set
	public boolean isNew() {return boolNew;}
	
	// Bank Name
	public String GetBankName() {return bankName;}
	public void SetBankName(String bankName) {this.bankName = bankName;}
	
	// Nick Name
	public String GetNickName() {return nickname;}
	public void SetNickName(String nickname) {this.nickname = nickname;}
	
	// Balance
	public int GetBalance() {return balance;} // this should probably be calculated from the transactions instead of being an attribute
	public void SetBalance(int balance) {this.balance = balance;}
	
}
