
public class Transaction {
	private Integer	transactionId;
	private Integer accountId;
	private String 	type;
	private String 	date;
	private Integer amount;
	private String  description;

	public Transaction()
	{
        this(0,0,"","0000-00-00",0,"");
	}

    public Transaction(
            Integer transactionId,
            Integer accountId,
            String type,
            String date,
            Integer amount,
            String description)
    {
        super();
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public void updateWith(Transaction other)
    {
        accountId = other.getAccountId();
        type = other.getType();
        date = other.getDate();
        amount = other.getAmount();
        description = other.getDescription();
    }

	public void setId(Integer transactionId)	{
		this.transactionId = transactionId;
	}
	public void setAccountId(Integer accountId) {this.accountId = accountId;}
	public void setType(String type) 			{this.type = type;}
	public void setDate(String date) 			{this.date = date;}
	public void setAmount(Integer amount) 		{this.amount= amount;}
	public void setDescription(String description) 		{this.description = description;}
	
	public Integer 	getId() 			{return transactionId;}
	public Integer 	getAccountId() 		{return accountId;}
	public String 	getType() 			{return type;}
	public String 	getDate() 			{return date;}
	public Integer 	getAmount()			{return amount;}
	public String 	getDescription() 	{return description;}
	
	
	// This is what does the "viewing"
	public String toString(){
		String output = "";
		output += "\n-------------------\n";
		output += "ID"+" -> " + this.getId() + "\n";
		output += "AccountId"+" -> " + this.getAccountId() + "\n";
		output += "Type"+" -> " + this.getType() + "\n";
		output += "Date"+" -> " + this.getDate() + "\n";
		output += "Amount"+" -> " + this.getAmount() + "\n";
		output += "Description"+" -> " + this.getDescription() + "\n";
		return output;
	}
}
