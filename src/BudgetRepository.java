import java.sql.ResultSet;
import java.sql.SQLException;

public class BudgetRepository extends AbstractRepository<Budget>
{
    public BudgetRepository(Database database)
    {
        super(database, "budget", "budgetId");
    }

    private Integer noneBudgetId;

    public Integer getNoneBudgetId() { return noneBudgetId; }

    @Override
    public Budget getItem(Integer id) {
        return new Budget(itemMap.get(id));
    }

    @Override
    protected void setItemFromResult(ResultSet result) {
        try {
            Budget budget =  new Budget();
            budget.setId(result.getInt("budgetId"));
            budget.setAmount(result.getInt("amount"));
            budget.setName(result.getString("name"));
            budget.setBalance(result.getInt("balance"));

            System.out.println(result.getInt(primaryKey));
            addItemToMap(budget);
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void initSQLStructure() {
        database.updateSQL(sql.createTable(tableName, primaryKey, "INTEGER"));	//pass "NULL" to auto-increment
        database.updateSQL(sql.addColumn(tableName, "name", "VARCHAR"));
        database.updateSQL(sql.addColumn(tableName, "amount", "INTEGER"));
        database.updateSQL(sql.addColumn(tableName, "balance", "INTEGER"));
        Budget noneBudget = new Budget(
                0, "None",0,0
        );
        saveItem(noneBudget);
    }

    @Override
    public void saveItem(Budget item) {
        SQLValueMap values = new SQLValueMap();
        values.put("name", item.getName());
        values.put("amount", item.getAmount());
        values.put("balance", item.getBalance());


        if(item.getId() == 0) {
            // Insert new item
            Integer generatedId = database.updateSQL( sql.addEntryUsingMap(tableName, values) );
            item.setId(generatedId);
            addItemToMap(item);
            if (item.getName().equals("None"))
            {
                noneBudgetId = item.getId();
            }
        }
        else {
            // Update item in database
            SQLValueMap where = new SQLValueMap();
            where.put(primaryKey, Integer.toString(item.getId()));
            database.updateSQL( sql.updateEntryUsingMap(tableName, values, where) );
            itemMap.get(item.getId()).updateWith(item);
        }
    }
}
