package unrelated;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Database;
import SQLStringFactory;
import SQLValueMap;


public class BudgetRepository {
//This class contains access to all of the budgets on the system.
	Database myDatabase;
	SQLStringFactory sql;
	
	String tableName;
	String primaryKey;
	
	
	BudgetMap itemMap; // loaded budget models live here
	
	
	public BudgetRepository(Database myDatabase) {
		this.myDatabase = myDatabase;
		tableName = "budget";
		primaryKey = "accountId";
		this.sql = SQLStringFactory.getInstance();
		
		itemMap = new BudgetMap();
	}
		
	
	//adapting work from Account Repository- if it ain't broke, don't fix it
	
	//========================================
	
		//				CHECKING
		
		//========================================
		protected boolean hasItemCached(Integer itemID) { // check the map not the database
			Boolean hasItemCachedCached = itemMap.get(itemID) != null;
			return hasItemCachedCached;
		}
		
		
		//========================================

		//				SAVING
		
		//========================================
		public void saveItem(BudgetModel budget) {
		
			SQLValueMap values = new SQLValueMap();
			
			values.put("amount", budget.getAmount());
			values.put("end", budget.getEnd());
			values.put("start", budget.getStart());
			values.put("type", budget.getType());
		
			
				
			if(budget.isNew()) {
				//Insert into database
				Integer newId = myDatabase.updateSQL(sql.addEntryUsingMap("budget", values));
				budget.SetId(newId);
				addItemToMap(budget);
			} else {
				//Update item in database
				SQLValueMap where = new SQLValueMap();
				where.put("budgetId", Integer.toString(budget.getId()));
				myDatabase.updateSQL( sql.updateEntryUsingMap("budget", values, where) );
			}
		}
		
		public void deleteItem(Integer itemID) {
			if(itemMap.containsKey(itemID)) 
				itemMap.remove(itemMap.get(itemID));
			myDatabase.updateSQL("DELETE FROM "+tableName+" WHERE "+primaryKey+"='"+itemID+"';");
		}
		
		//========================================
		
		//				GETTING
		
		//========================================
		public BudgetModel getItem(Integer itemID) {
			//Attempt to load from DB if not present
			if(!hasItemCached(itemID))
				loadItem(itemID);
			
			//Return if found
			if(hasItemCached(itemID))
				return itemMap.get(itemID);
			return null;
		}
		
		//will return map off all items in database
		public BudgetMap getMapOfAllItems() {
			loadAll();
			return (BudgetMap)itemMap;
		}
		
		public BudgetList getListOfAllItems() {
			
			//Load Accounts if not listed
			loadAll();
			
			//Initialze Budget
			BudgetList aBudgetList = new BudgetList();
			
			//Loop over hash map
			Iterator it = getMapOfAllItems().entrySet().iterator();
		    while (it.hasNext()) {
		    	//Get map pairs
		        HashMap.Entry pair = (HashMap.Entry)it.next();
		        
		        //Add Account to list
		        aBudgetList.add((BudgetModel) pair.getValue());
		    }
		    
			return aBudgetList;
		}
		
		
		
		//========================================
		
		//				LOADING
		
		//========================================
		protected void loadItem(Integer itemID) {
			SQLValueMap where = new SQLValueMap();
			where.put("budgetId", itemID);
			
			ResultSet result = myDatabase.fetchSQL(sql.selectEntryUsingMap("budget", where));
			try {
				while(result.next())
					setItemFromResult(result);
				
				
			} catch (SQLException e){ 
				System.err.println(e.getMessage());
			}
		}
		
		//Loads all accounts in database
		protected void loadAll() {
			
			//get rid of old items - at least until caching can be properly done
			itemMap.clear();
			
			System.out.println("loadAll");
			SQLValueMap where = new SQLValueMap(); // left blank so where is omitted
			
			ResultSet result = myDatabase.fetchSQL(sql.selectEntryUsingMap("budget", where));
			try {
				while(result.next())
					setItemFromResult(result);
				
			} catch (SQLException e){ 
				System.err.println(e.getMessage());
			}
		}
		
		protected void setItemFromResult(ResultSet result) {
			System.out.println("setItemFromResult");
			try {
				BudgetModel budget =  new BudgetModel();
				budget.SetId(result.getInt("budgetId"));
				budget.setAmount(result.getFloat("amount"));
				budget.setStart(result.getString("start"));
				budget.setEnd(result.getString("end"));
				budget.setType(result.getString("type"));
				addItemToMap(budget);
			} catch (SQLException e){ 
				System.err.println(e.getMessage());
			}
		}
		
		protected void addItemToMap(BudgetModel budget) {
			if(!budget.isNew()) {
				itemMap.put(budget.getId(), budget);
			}
		}
	
	
	
	
	
	
	
	
//SQL DB Structure
	public void reinitSQLStructure() { // reinstall
		destroySQLStructure();	
		initSQLStructure();
	}

	public void initSQLStructure() { // install
		myDatabase.updateSQL(sql.createTable("budget", "accountId", "INTEGER"));	//pass "NULL" to auto-increment
		myDatabase.updateSQL(sql.addColumn("budget", "budgetId", "INTEGER"));
		myDatabase.updateSQL(sql.addColumn("budget", "type", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn("budget", "start", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn("budget", "end", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn("budget", "amount", "INTEGER"));
	}
	
	public void destroySQLStructure() { // uninstall
		myDatabase.updateSQL(sql.deleteTable("budget"));	
	}
}
