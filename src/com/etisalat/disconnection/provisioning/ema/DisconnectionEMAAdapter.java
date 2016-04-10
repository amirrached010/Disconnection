package com.etisalat.disconnection.provisioning.ema;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import se.ericsson.ema.downstream.DownStreamConnection;
import se.ericsson.ema.downstream.DownStreamException;

import com.etisalat.disconnection.provisioning.ema.models.DisconnectionRequest;
import com.etisalat.disconnection.provisioning.ema.util.EmaException;
import com.etisalat.disconnection.provisioning.ema.util.DisconnectionParser;
import com.etisalat.disconnection.provisioning.ema.util.DisconnectionProcessor;


public class DisconnectionEMAAdapter implements DownStreamConnection {

	private static Logger logger = Logger
			.getLogger(DisconnectionEMAAdapter.class);

	private String[] configuration;

	public void setConfiguration(String[] configuration)
			throws DownStreamException {

		this.configuration = configuration;

	}

	public void connect() throws DownStreamException {
		// TODO Auto-generated method stub

	}

	public void disconnect() throws DownStreamException {
		// TODO Auto-generated method stub

	}

	public String sendMessage(String message) throws DownStreamException {
		PropertyConfigurator
				.configure("/var/sog/sdk/downstream/java/Disconnection/log4j.properties");
		boolean errorOccurred = false;
		String errorCode = null;
		String errorMessage = null;

		if (logger.isInfoEnabled()) {
			logger.info("Start Exceuting command: " + message);
		}
		String result = null;
		try {
			logger.info(message);
			logger.debug("start processCommand");
			result = processCommand(message);
		} catch (EmaException e) {
			logger.error("Failed message: " + e.getMessage() + " errorcode: "
					+ e.getErrorCode(), e);
			errorCode = e.getErrorCode();
			errorMessage = e.getMessage();
			errorOccurred = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed message: " + e.getMessage());
			errorCode = "-5000";
			errorMessage = e.getMessage();
			errorOccurred = true;
			if (e instanceof SOAPFaultException) {
				SOAPFaultException soapException = (SOAPFaultException) e;
				errorCode = soapException.getFault().getDetail()
						.getFirstChild().getTextContent();

				errorMessage = soapException.getFault().getFaultString();
				logger.error("Fault Error Code "
						+ soapException.getFault().getDetail().getChildNodes()
								.item(0).getNodeValue() + " String "
						+ soapException.getFault().getFaultString());
			}

		}
		if (!errorOccurred) {
			if (logger.isInfoEnabled())
				logger.info("Command successeded result: " + result);
			return result;
		}
		if (logger.isInfoEnabled()) {
			logger.info("Command failed errorCode: " + errorCode
					+ " errorMessage:" + errorMessage);
		}
		
		return "ErrorCode:" + errorCode + ",ErrorMessage:" + errorMessage + ";";

	}

	public String processCommand(String message) throws EmaException {
		logger.debug("starting the process Command function");
		String result = "";
		if ((message != null) && (message.trim().length() > 0)) {
			try {
				DisconnectionRequest emaRequest = DisconnectionParser
						.parseRequest(message);
				
				result = DisconnectionProcessor.processRequest(emaRequest,
						configuration);
			} catch (DisconnectionException e) {

				logger.error(e, e);
				throw new EmaException(e.getErrorCode(), e.getMessage());
			}

		}
		if (result.length() > 0) {
			return "Success:" + result;
		}
		return "Success";
	}

	public static void main(String[] args) {
		DisconnectionEMAAdapter adapter = new DisconnectionEMAAdapter();

		try {
			String command = "SET:TESUB:MSISDN,23456789;";

			String[] conf = new String[] { "file:./ODSAPI.asmx.xml" };

			adapter.setConfiguration(conf);
			System.out.println("Request " + command);
			System.out.println("Response " + adapter.sendMessage(command));
			// "CREATE:VIRTUALSIMSUB:MSISDN,1125631544:VIRTUALNUMBERS,1179008213;"));
		} catch (DownStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}