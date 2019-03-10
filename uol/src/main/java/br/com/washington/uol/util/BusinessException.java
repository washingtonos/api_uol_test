package br.com.washington.uol.util;

public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String description;
	private String statusCode;
	private String detail;

	public BusinessException() {
	}

	public BusinessException(String description, String statusCode, String detail) {
		super(detail);
		this.description = description;
		this.statusCode = statusCode;
		this.detail = detail;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String getMessage() {
		return "[" + getStatusCode() + "] :: " + getDescription() + " -> " + getDetail();
	}
}
