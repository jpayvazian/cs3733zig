package cs3733.zig.choice.http;

public class AddFeedbackRequest {
	
	public String memberName;
	public String contents;
	public String idAlternative;
	
	public AddFeedbackRequest(String memberName, String contents, String idAlternative) {
		this.memberName = memberName;
		this.contents = contents;
		this.idAlternative = idAlternative;
	}
	
	public AddFeedbackRequest() {
		
	}
	
	public String getMemberName() { return memberName; }
	public void setMemberName(String memberName) { this.memberName = memberName; }
	
	public String getContents() { return contents; }
	public void setContents(String contents) { this.contents = contents; }
	
	public String getIdAlternative() { return idAlternative; }
	public void setIdAlternative(String idAlternative) { this.idAlternative = idAlternative; }
	
	@Override
	public String toString() {
		return "AddFeedback(" + memberName + " : " + contents + " for alternative:" + idAlternative + ")";
	}

}
