package cs3733.zig.choice.http;

import cs3733.zig.choice.model.Alternative;

public class CreateChoiceRequest {

	public String description;
	public int maxMembers;
	public Alternative[] alternatives = new Alternative[5];
	
	public void setDescription(String description) { this.description = description; } 
	public String getDescription() { return description; }
	
	public void setMaxMembers(int maxMembers) { this.maxMembers = maxMembers; }  
	public int getMaxMembers() { return maxMembers; }

	public void setAlternatives(Alternative[] alternatives) { this.alternatives = alternatives; }
	public Alternative[] getAlternatives() { return alternatives; }
	
	public CreateChoiceRequest (String description, int maxMembers, Alternative[] alternatives) {
		this.description = description;
		this.maxMembers = maxMembers;
		this.alternatives = alternatives;
	}
	
	public CreateChoiceRequest() {
		
	}

	@Override
	public String toString() {
		String second = "";
		String first = "RegisterForChoice(" + description + "," + maxMembers + ","; 
		for (int i = 0; i < 5; i++) {
			if (alternatives[i] != null) {
				second += alternatives[i].getName() + "," + alternatives[i].getDescription() + "\n";
				i++;
			}
		}
				return first + second + ")";
	}
}
