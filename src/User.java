public class User {

	/*
	 * For iteration 3, I guess we could have a database consisting of usernames
	 * as unique ID, each with a password and a filename for their banking database
	 * When a user "logs in", we load up the db associated with his banking info.
	 *
	 * This way we do not need to have a User reference everywhere in our classes
	 * We could probably have a UserRepository that loads up the same way the other
	 * model are created.
	 */
	private String name;
	private String password;

	public User()
	{
		name = "Jane Doe";
		password = "";
	}

	public String getName() {return name;}
	public boolean isValidPasswork(String password) {return this.password == password;}

}
