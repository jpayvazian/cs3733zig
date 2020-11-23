package cs3733.zig.choice.http;

public class CreateChoiceRequest {

	public String description;
	public int maxMembers;
	public String[] alternativeNames;
	public String[] alternativeDescriptions;
	
	public void setDescription(String description) { this.description = description; } 
	public String getDescription() { return description; }
	
	public void setMaxMembers(int maxMembers) { this.maxMembers = maxMembers; }  
	public int getMaxMembers() { return maxMembers; }

	public void setAlternativeNames(String[] alternativeNames) { this.alternativeNames = alternativeNames; }
	public String[] getAlternativeNames() { return alternativeNames; }
	
	public void setAlternativeDescriptions(String[] alternativeDescriptions) { this.alternativeDescriptions = alternativeDescriptions; }
	public String[] getAlternativeDescriptions() { return alternativeDescriptions; }
	
	public CreateChoiceRequest (String description, int maxMembers, String[] alternativeNames, String[] alternativeDescriptions) {
		this.description = description;
		this.maxMembers = maxMembers;
		this.alternativeNames = alternativeNames;
		this.alternativeDescriptions = alternativeDescriptions;
	}
	
	public CreateChoiceRequest() {
		
	}

	@Override
	public String toString() {
		int numAlts = alternativeNames.length;
		String second = "";
		String first = "RegisterForChoice(" + description + "," + maxMembers + ","; 
		for (int i = 0; i < numAlts; i++) {
				second += alternativeNames[i] + ":" + alternativeDescriptions[i] + " ";
		}
				return first + second + ")";
	}
}
