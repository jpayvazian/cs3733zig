package cs3733.zig.choice.http;

public class AddRatingRequest {
	
	public boolean rating;
	public String memberName;
	public String idAlternative;
	
	public AddRatingRequest(boolean rating, String memberName, String idAlternative) {
		this.rating = rating;
		this.memberName = memberName;
		this.idAlternative = idAlternative;
	}
	
	public AddRatingRequest() {
		
	}

	public boolean getRating() { return rating; }
	public void setRating(boolean rating) { this.rating = rating; }
	
	public String getMemberName() { return memberName; }
	public void setMemberName(String memberName) { this.memberName = memberName; }
	
	public String getIdAlternative() { return idAlternative; }
	public void setIdAlternative(String idAlternative) { this.idAlternative = idAlternative; }
	
	@Override
	public String toString() {
		String reaction;
		if (rating) { reaction = "approve"; }
		else { reaction = "disapprove"; }
		return "AddRating(" + reaction + " from member " + memberName + " to alternative:" + idAlternative + ")";
	}
}
