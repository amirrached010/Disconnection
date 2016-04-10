package com.etisalat.disconnection.provisioning;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;

public class WebServiceHandler implements SOAPHandler<SOAPMessageContext> {

	private static final Logger log = Logger.getLogger(WebServiceHandler.class);

	public Set<QName> getHeaders() {
		log.debug(">>>>>>>>>>> GetHeaders");

		return null;
	}

	public boolean handleResponse(SOAPMessageContext soapMessageContext) {
		return false;
	}

	public boolean handleMessage(SOAPMessageContext soapMessageContext) {
		log.debug(">>>>>>>>>>> HandleMessage");
		Boolean outboundProperty = (Boolean) soapMessageContext
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		try {

			SOAPMessage soapMessage = soapMessageContext.getMessage();
			SOAPPart soapPart = soapMessage.getSOAPPart();
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
			soapEnvelope.setPrefix("soap");

			SOAPMessage msg = soapMessageContext.getMessage();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				msg.writeTo(out);
			} catch (SOAPException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			String strMsg = new String(out.toByteArray());

			log.debug(strMsg);
		} catch (Exception ex) {

		}

		if (outboundProperty.booleanValue()) {
			log.debug("Outbound message:");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Request :"
					+ soapMessageContext.getMessage().toString());
		} else {
			log.debug("Inbound message:");
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Response :"
					+ soapMessageContext.getMessage().toString());
		}

		return true;
	}

	public boolean handleFault(SOAPMessageContext soapMessageContext) {
		log.debug(">>>>>>>>>>> HandleFault");
		return true;
	}

	public void close(MessageContext messageContext) {
		log.debug(">>>>>>>>>>> Close");

	}

}
