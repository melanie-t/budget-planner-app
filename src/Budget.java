public class Budget extends AbstractUniqueId{

    private String name;
    private Integer amount;
    private Integer balance;

    public Budget()
    {
        this(0, "", 0, 0);
    }

    public Budget(Budget other)
    {
        this(other.getId(), other.getName(), other.getAmount(), other.getBalance());
    }

    public Budget(Integer budgetId, String name, int amount, int balance)
    {
        super(budgetId);
        this.name = name;
        this.amount = amount;
        this.balance = balance;
    }

    public void updateWith(Budget other)
    {
        this.name = other.getName();
        this.amount = other.getAmount();
        this.balance = other.getBalance();
    }

    public String getName() { return name; }
    public Integer getAmount() { return amount; }
    public Integer getBalance() { return balance; }

    public void setName(String name) { this.name = name;}
    public void setAmount(Integer amount) { this.amount = amount; }
    public void setBalance(Integer balance) { this.balance = balance; }

    public String toString(){
        String output = "";
        output += "\n-------------------\n";
        output += "ID"+" -> " + this.getId() + "\n";
        output += "Name"+" -> " + this.getName() + "\n";
        output += "Amount"+" -> " + this.getAmount() + "\n";
        output += "Balance"+" -> " + this.getBalance() + "\n";
        return output;
    }

}
