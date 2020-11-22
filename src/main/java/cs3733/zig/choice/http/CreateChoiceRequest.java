package cs3733.zig.choice.http;

public class CreateChoiceRequest {

	public String description;
	public int numMembers;
	public String[] alternativeNames;
	public String[] alternativeDescriptions;
	
	public void setDescription(String description) { this.description = description; } 
	public String getDescription() { return description; }
	
	public void setMaxMembers(int maxMembers) { this.numMembers = maxMembers; }  
	public int getMaxMembers() { return numMembers; }

	public void setAlternativeNames(String[] alternativeNames) { this.alternativeNames = alternativeNames; }
	public String[] getAlternativeNames() { return alternativeNames; }
	
	public void setAlternativeDescriptions(String[] alternativeDescriptions) { this.alternativeDescriptions = alternativeDescriptions; }
	public String[] getAlternativeDescriptions() { return alternativeDescriptions; }
	
	public CreateChoiceRequest (String description, int numMembers, String[] alternativeNames, String[] alternativeDescriptions) {
		this.description = description;
		this.numMembers = numMembers;
		this.alternativeNames = alternativeNames;
		this.alternativeDescriptions = alternativeDescriptions;
	}
	
	public CreateChoiceRequest() {
		
	}

	@Override
	public String toString() {
		int numAlts = alternativeNames.length;
		String second = "";
		String first = "RegisterForChoice(" + description + "," + numMembers + ","; 
		for (int i = 0; i < numAlts; i++) {
				second += alternativeNames[i] + ":" + alternativeDescriptions[i] + " ";
		}
				return first + second + ")";
	}
}
