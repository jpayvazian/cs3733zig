package cs3733.zig.choice.http;

public class DeleteChoicesResponse {
	final public int nmbrDeleted;
	final public int statusCode;
	final public String error;
	
	public DeleteChoicesResponse(int statusCode, int nmbrDeleted) {
		this.nmbrDeleted = nmbrDeleted;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public DeleteChoicesResponse(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.nmbrDeleted = -1;
	}
	
	@Override
	public String toString() {
		if (statusCode / 100 == 2) {
			return "Result(Choices deleted)";
		} else {
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}

}
