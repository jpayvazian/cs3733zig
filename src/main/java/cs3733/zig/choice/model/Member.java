package cs3733.zig.choice.model;

import java.util.Optional;
/**
 * Member class
 * @author Luke (Zig)
 *
 */
public class Member {
	final private String name;
	final private Optional<String> password;
	/**
	 * Constructor for Member
	 * @param name
	 * @param password
	 */
	public Member(String name, Optional<String> password) {
		this.name = name;
		this.password = password;
	}
	
	//(might include this in constructor as a field)
	public boolean registerChoice(String id) throws Exception {
		throw new Exception("not implemented");
	}

	public String getName() {
		return name;
	}

	public Optional<String> getPassword() {
		return password;
	}
	
}
