
public class Account extends  AbstractUniqueId {
	
	private String bankName;
	private String nickname;
	private int balance;

	public Account() {
        this(0,"","",0);
	}

	public Account(Integer accountId, String bankName, String nickname, int balance) {
		super(accountId);
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


	public String getBankName() {return bankName;}
	public void setBankName(String bankName) {this.bankName = bankName;}
	
	public String getNickname() {return nickname;}
	public void setNickname(String nickname) {this.nickname = nickname;}
	
	public Integer getBalance() {return balance;} // this should probably be calculated from the transactions instead of being an attribute
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
