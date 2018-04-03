/**
 * The Transaction class is a plain old data structure that contains all the information on a given transaction.
 */
public class Transaction extends AbstractUniqueId{
    private static String[] transactionTypes = new String[]{"Entertainment", "Food", "Rent", "Social", "Tuition", "Utilities", "Other"};
	private Integer associatedAccountId;
	private String 	type;
	private String 	date;
	private Integer amount;
	private String  description;

    /**
     * Default constructor.
     */
	public Transaction()
	{
        this(0,0,"","0000-00-00",0,"");
	}

    /**
     * Copy constructor.
     * @param other Transaction object
     */
	public Transaction(Transaction other) {
	    super(other.getId());
        this.associatedAccountId = other.getAssociatedAccountId();
        this.type = other.getType();
        this.date = other.getDate();
        this.amount = other.getAmount();
        this.description = other.getDescription();
	}

    /**
     * Constructor.
     * Transactions created with an id of 0 are assumed to be new. The system will assign a generated id once
     * added to the database.
     * @param transactionId transaction id
     * @param associatedAccountId id of the associated account
     * @param type type of transaction
     * @param date date of the transaction
     * @param amount amount of the transaction
     * @param description description of the transaction
     */
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

    /**
     * Updates this transaction with the information form the specified transaction. This does not change the id
     * of this transaction. It is assumed we are updating with a newer version of the same transaction.
     * @param other account to update from
     */
    public void updateWith(Transaction other)
    {
    	setId(other.getId());
        associatedAccountId = other.getAssociatedAccountId();
        type = other.getType();
        date = other.getDate();
        amount = other.getAmount();
        description = other.getDescription();
    }

    /**
     * Set the associated account id
     * @param associatedAccountId account id
     */
	public void setAssociatedAccountId(Integer associatedAccountId) {this.associatedAccountId = associatedAccountId;}

    /**
     * Set the type of transaction
     * @param type type of transaction
     */
	public void setType(String type) 			{this.type = type;}

    /**
     * Set the date of the transaction
     * @param date date of transaction
     */
	public void setDate(String date) 			{this.date = date;}

    /**
     * Set the amount for the the transaction
     * @param amount amount
     */
	public void setAmount(Integer amount) 		{this.amount= amount;}

    /**
     * Set the description for the transaction
     * @param description description
     */
	public void setDescription(String description) 		{this.description = description;}

    /**
     * Get the associated account's id
     * @return accound id
     */
	public Integer getAssociatedAccountId() 		{return associatedAccountId;}

    /**
     * Get the type of the transaction
     * @return type
     */
	public String 	getType() 			{return type;}

    /**
     * Get the date of the transaction
     * @return date
     */
	public String 	getDate() 			{return date;}

    /**
 	 * Get the amount of the transaction
     * @return amount
     */
	public Integer 	getAmount()			{return amount;}
	public String 	getDescription() 	{return description;}

    /**
     * Get the possible types of transactions
     * @return transactionTypes
     */
	public static String[] getTransactionTypes() {return transactionTypes;}

    /**
     * Generates a multi-line string that describes the content of the transaction
     * @return description of this transaction
     */
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
