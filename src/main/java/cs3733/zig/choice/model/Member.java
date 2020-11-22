package cs3733.zig.choice.model;

/**
 * 
 * Member class
 *
 */
public class Member {
	final private String name;
	final private String password;
	
	/**
	 * Constructor for Member (regardless if they are created for the first time or not)
	 * @param name
	 * @param string
	 */
	public Member(String name, String password) {
		this.name = name;
		this.password = (password==null)?"":password;
	}
	
	//(might include this in constructor as a field)
	public boolean registerChoice(String id) throws Exception {
		throw new Exception("not implemented");
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public boolean isCorrectPassword(String password2) {
		password2 = (password2==null)?"":password2;
		return password.equals(password2);
	}
	
	@Override
	public String toString() {
		return "USERNAME: " + this.name + " PASSWORD: " + this.password;
	}
}
