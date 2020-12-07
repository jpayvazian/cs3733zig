package cs3733.zig.choice.http;

public class CompleteChoiceRequest {
	
	public String idChoice;
	public String idAlternative;
	
	public CompleteChoiceRequest(String idChoice, String idAlternative) {
		this.idChoice = idChoice;
		this.idAlternative = idAlternative;
	}
	
	public CompleteChoiceRequest() { }
	
	public String getIdChoice() { return idChoice; }
	public void setIdChoice(String idChoice) { this.idChoice = idChoice; }
	
	public String getIdAlternative() { return idAlternative; }
	public void setIdAlternative(String idAlternative) { this.idAlternative = idAlternative; }
	
	@Override
	public String toString() {
		return "CompleteChoice(" + idChoice + " with alternative:" + idAlternative + ")";
	}
	
}
