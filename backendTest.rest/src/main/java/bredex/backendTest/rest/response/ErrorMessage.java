package bredex.backendTest.rest.response;

public class ErrorMessage extends Response {
	
	private int statusCode;
	private boolean success;
		
	public ErrorMessage(int statusCode, boolean success) {
		super();
		// TODO Auto-generated constructor stub
		this.statusCode = statusCode;
		this.success = success;
	}

	public int getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	@Override
	public String toString() {
		return "ErrorMessage [statusCode=" + statusCode + ", success=" + success + ", toString()=" + super.toString()
				+ "]";
	}
	
	
	
	

}
