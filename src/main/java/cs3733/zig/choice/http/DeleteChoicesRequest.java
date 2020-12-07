package cs3733.zig.choice.http;

public class DeleteChoicesRequest {
	private double days;
	
	public DeleteChoicesRequest(double days) {
		this.days = days;
	}
	
	public DeleteChoicesRequest() {	}

	public double getDays() {
		return days;
	}
	
	public void setDays(double days) {
		this.days = days;
	}
}
