
public class TransactionModel extends AbstractModel{
	Integer	transactionId;
	Integer accountId;
	String 	type;
	String 	date;
	Integer amount;
	String  description;
	
	public TransactionModel() 
	{
		super();
		transactionId = 0;
		accountId = 0;
		type = "";
		date = "0000-00-00";
		amount = 0;
		description = "";
	}
	
	public void setId(Integer transactionId)	{
		setIsNewModel(transactionId == 0); // ID of 0 is considered new (not saved in DB)
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
	
}
