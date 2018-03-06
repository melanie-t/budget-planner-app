
public class TransactionModel extends AbstractModel{
	Integer	transactionId;
	Integer accountId;
	String 	type;
	String 	date;
	Float 	amount;
	
	public TransactionModel() 
	{
		super();
	}
	
	public void SetId(Integer transactionId)	{
		setIsNewModel(transactionId == 0); // ID of 0 is considered new (not saved in DB)
		this.transactionId = transactionId;		
	}
	public void setAccountId(Integer accountId) {this.accountId = accountId;}
	public void setType(String type) 			{this.type = type;}
	public void setDate(String date) 			{this.date = date;}
	public void setAmount(Float amount) 		{this.amount= amount;}
	
	public Integer 	getId() 		{return transactionId;}
	public Integer 	getAccountId() 	{return accountId;}
	public String 	getType() 		{return type;}
	public String 	getDate() 		{return date;}
	public Float 	getAmount()		{return amount;}
	
	
}
