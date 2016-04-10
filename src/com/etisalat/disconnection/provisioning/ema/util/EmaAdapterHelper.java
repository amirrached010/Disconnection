package com.etisalat.disconnection.provisioning.ema.util;

public class EmaAdapterHelper {
	public static String getCommandType(String message) throws EmaException {

		String command = null;
		if (message.indexOf(":") != -1) {
			command = message.substring(0, message.indexOf(":"));
		} else {
			throw new EmaException("5003", "No command specified");
		}
		return command;
	}

	public static String getNodeType(String message) throws EmaException {
		String type = null;
		if (message.indexOf(":") != -1) {
			message = message.substring(message.indexOf(":") + 1);
			if (message.indexOf(":") != -1) {
				type = message.substring(0, message.indexOf(":"));
			} else {
				throw new EmaException("5004", "No Node type specified");
			}
		}
		return type;
	}

	public static String getParameter(String message, String parameterName)
			throws EmaException {
		String parameterValue = null;
		boolean errorOccurred = false;
		if (message.indexOf(":") != -1) {
			message = message.substring(message.indexOf(":") + 1);
			if (message.indexOf(":") != -1) {
				message = message.substring(message.indexOf(":") + 1);
				if (message.indexOf(parameterName + ",") != -1) {
					message = message.substring(message.indexOf(parameterName
							+ ",")
							+ (parameterName + ",").length());
					if (message.indexOf(":") != -1) {
						parameterValue = message.substring(0,
								message.indexOf(":"));
					} else if (message.indexOf(";") != -1) {
						parameterValue = message.substring(0,
								message.indexOf(";"));
					} else
						errorOccurred = true;
				} else {
					errorOccurred = true;
				}
			} else {
				errorOccurred = true;
			}
		} else {
			errorOccurred = true;
		}

		if (errorOccurred) {
			throw new EmaException("5005", "Parameter: " + parameterName
					+ " is not specified.");
		}
		return parameterValue;
	}

	public static String getParameterNotMandatory(String message,
			String parameterName) {
		String parameterValue = null;
		boolean errorOccurred = false;
		if (message.indexOf(":") != -1) {
			message = message.substring(message.indexOf(":") + 1);
			if (message.indexOf(":") != -1) {
				message = message.substring(message.indexOf(":") + 1);
				if (message.indexOf(parameterName + ",") != -1) {
					message = message.substring(message.indexOf(parameterName
							+ ",")
							+ (parameterName + ",").length());
					if (message.indexOf(":") != -1) {
						parameterValue = message.substring(0,
								message.indexOf(":"));
					} else if (message.indexOf(";") != -1) {
						parameterValue = message.substring(0,
								message.indexOf(";"));
					} else
						errorOccurred = true;
				} else {
					errorOccurred = true;
				}
			} else {
				errorOccurred = true;
			}
		} else {
			errorOccurred = true;
		}

		if (errorOccurred) {
			parameterValue = null;
		}
		return parameterValue;
	}

	public static long getNumericParameter(String message, String parameterName)
			throws EmaException {
		String parameterValue = getParameter(message, parameterName);
		long result = 0L;
		try {
			result = Long.parseLong(parameterValue);
		} catch (Exception e) {
			throw new EmaException("5006", "Parameter: " + parameterName
					+ " must be numeric, value = " + parameterValue);
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			System.out
					.println(EmaAdapterHelper
							.getParameter(
									"SET:BLACKBERRYSUB:IMSI,602030047873979:TRANS_ID,312540519:MSISDN,1112091533:SERVICE_TYPE,9:ACTION,ACTIVATE:LANG,1;",
									"LANG"));
		} catch (EmaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}