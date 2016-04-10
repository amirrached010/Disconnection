package com.etisalat.disconnection.provisioning.ema.util;

import org.apache.log4j.Logger;

import com.etisalat.disconnection.provisioning.ema.models.DisconnectionRequest;

public class DisconnectionParser {

	private static Logger logger = Logger.getLogger(DisconnectionParser.class);

	public static DisconnectionRequest parseRequest(String message)
			throws EmaException {
		logger.debug("Command before parse " + message);
		String command = EmaAdapterHelper.getCommandType(message);
		String type = EmaAdapterHelper.getNodeType(message);
		if (!type.equals("TESUB")) {
			throw new EmaException("-5002", "Service: " + type
					+ " is not supported, only TESUB is supported.");
		}
		String msisdn = EmaAdapterHelper.getParameter(message,
				"MSISDN");

		

		logger.debug("------------ ------------ --------------");
		logger.debug("command " + command);
		logger.debug("type " + type);
		logger.debug("msisdn " + msisdn);
		

		

		DisconnectionRequest request = new DisconnectionRequest();

		request.setAction(command);
		request.setMsisdn(msisdn);
		

		return request;
	}

	public static void main(String[] arge) {
		String request = "SET:ELEARNINGSUB:ACCOUNT,1:STATUS,SUSPEND;";
		try {
			DisconnectionParser.parseRequest(request);
		} catch (EmaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
