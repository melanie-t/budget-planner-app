package unrelated;

public class BudgetModel {

	Integer budgetId;
    String type;
	String startDate;
	String endDate;
	Integer amount;

    private static Integer nextId;
    public static Integer getNextId() { return nextId++; }
		
    public BudgetModel()
    {
        super();
    }

    public void SetId(Integer budgetId)	{
        setIsNewModel(budgetId == 0); // ID of 0 is considered new (not saved in DB)
        this.budgetId = budgetId;
    }

    public void setType(String type) 			{this.type = type;}
    public void setStart(String startDate) 			{this.startDate = startDate;}
    public void setEnd(String endDate) 			{this.endDate = endDate;}
    public void setAmount(Float amount) 		{this.amount= amount;}

    public Integer 	getId() 		{return budgetId;}
    public String 	getType() 		{return type;}
    public String 	getStart() 		{return startDate;}
    public String 	getEnd() 		{return endDate;}
    public Float 	getAmount()		{return amount;}
}
