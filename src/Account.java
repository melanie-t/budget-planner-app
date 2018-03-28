/**
 * The Account class is a plain old data structure that contains all the information on a given account.
 */
public class Account extends  AbstractUniqueId {
	
	private String bankName;
	private String nickname;
	private int balance;

    /**
     * Default constructor.
     */
	public Account() {
        this(0,"","",0);
	}

	/**
	 * Copy constructor.
	 * @param other Account object
	 */
	public Account(Account other) {
		super(other.getId());
		this.bankName = other.getBankName();
		this.nickname = other.getNickname();
		this.balance = other.getBalance();
	}
    /**
     * Constructor.
     * Accounts created with an id of 0 are assumed to be new. The system will assign a generated id once
     * added to the database.
     * @param accountId account id
     * @param bankName name of the bank
     * @param nickname nickname of the account
     * @param balance current account balance
     */
	public Account(Integer accountId, String bankName, String nickname, int balance) {
		super(accountId);
		this.bankName = bankName;
		this.nickname = nickname;
		this.balance = balance;
	}

    /**
     * Updates this account with the information form the specified account. This does not change the id
     * of this account. It is assumed we are updating with a newer version of the same account.
     * @param other account to update from
     */
    public void updateWith(Account other)
    {
        bankName = other.getBankName();
        nickname = other.getNickname();
        balance = other.getBalance();
    }


    /**
     * Get bank name associated with this account.
     * @return the bank name
     */
	public String getBankName() {return bankName;}

    /**
     * Set bank name associated with this account.
     * @param bankName the bank name
     */
	public void setBankName(String bankName) {this.bankName = bankName;}

    /**
     * Get the nickname of this account.
     * @return nickname
     */
	public String getNickname() {return nickname;}

    /**
     * Set nickname of this account.
     * @param nickname nickname
     */
	public void setNickname(String nickname) {this.nickname = nickname;}

    /**
     * Get the current balance of this account.
     * @return the current balance
     */
	public Integer getBalance() {return balance;}

    /**
     * Set the current balance of this account.
     * @param balance balance
     */
	public void setBalance(int balance) {this.balance = balance;}

    /**
     * Generates a multi-line string that describes the content of the account
     * @return description of this account
     */
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
