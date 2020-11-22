package cs3733.zig.choice.http;

public class GetSpecificChoiceRequest {
	private String idChoice;

	public String getIdChoice() {
		return idChoice;
	}

	public void setIdChoice(String idChoice) {
		this.idChoice = idChoice;
	}
	
	public GetSpecificChoiceRequest() { }
	
	public GetSpecificChoiceRequest(String idChoice) { 
		this.idChoice=idChoice;
	}
}
