
public class Transaction extends AbstractUniqueId{
	private Integer associatedAccountId;
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
            Integer associatedAccountId,
            String type,
            String date,
            Integer amount,
            String description)
    {
        super(transactionId);
        this.associatedAccountId = associatedAccountId;
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public void updateWith(Transaction other)
    {
    	setId(other.getId());
        associatedAccountId = other.getAssociatedAccountId();
        type = other.getType();
        date = other.getDate();
        amount = other.getAmount();
        description = other.getDescription();
    }

	public void setAssociatedAccountId(Integer associatedAccountId) {this.associatedAccountId = associatedAccountId;}
	public void setType(String type) 			{this.type = type;}
	public void setDate(String date) 			{this.date = date;}
	public void setAmount(Integer amount) 		{this.amount= amount;}
	public void setDescription(String description) 		{this.description = description;}
	
	public Integer getAssociatedAccountId() 		{return associatedAccountId;}
	public String 	getType() 			{return type;}
	public String 	getDate() 			{return date;}
	public Integer 	getAmount()			{return amount;}
	public String 	getDescription() 	{return description;}
	
	
	// This is what does the "viewing"
	public String toString(){
		String output = "";
		output += "\n-------------------\n";
		output += "ID"+" -> " + this.getId() + "\n";
		output += "AccountId"+" -> " + this.getAssociatedAccountId() + "\n";
		output += "Type"+" -> " + this.getType() + "\n";
		output += "Date"+" -> " + this.getDate() + "\n";
		output += "Amount"+" -> " + this.getAmount() + "\n";
		output += "Description"+" -> " + this.getDescription() + "\n";
		return output;
	}
}
