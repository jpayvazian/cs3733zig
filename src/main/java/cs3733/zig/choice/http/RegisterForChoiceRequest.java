package cs3733.zig.choice.http;

public class RegisterForChoiceRequest {

	String memberName;
	String password;
	String idChoice;
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIdChoice() {
		return idChoice;
	}
	public void setIdChoice(String idChoice) {
		this.idChoice = idChoice;
	}
	
	@Override
	public String toString() {
		return "RegisterForChoice(" + memberName + "," + password + "," + idChoice + ")";
	}
	
	
	public RegisterForChoiceRequest(String memberName, String password, String idChoice) {
		this.memberName = memberName;
		this.password = password;
		this.idChoice = idChoice;
	}
	
	public RegisterForChoiceRequest() {
		
	}
}
