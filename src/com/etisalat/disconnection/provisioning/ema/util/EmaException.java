package com.etisalat.disconnection.provisioning.ema.util;

public class EmaException extends Exception
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String errorCode;

  public EmaException(String errorCode, String errorMessage)
  {
    super(errorMessage);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return this.errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }
}