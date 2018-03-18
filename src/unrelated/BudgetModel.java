package unrelated;
import AbstractModel;

public class BudgetModel extends AbstractModel
{
		Integer budgetId;
		String type;
		String startDate;
		String endDate;
		Float amount;
		
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
