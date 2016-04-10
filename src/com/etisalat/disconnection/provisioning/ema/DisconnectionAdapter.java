package com.etisalat.disconnection.provisioning.ema;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.tempuri.ODSAPI;
import org.tempuri.ODSAPISoap;

public class DisconnectionAdapter {

	private static Logger logger = Logger.getLogger(DisconnectionAdapter.class);

	private static DisconnectionAdapter instance;

	private String[] configuration;
	private String vDisconnectionURL;

	// private String vSIMsubscriberURL;

	ODSAPI odsAPI = null;
	ODSAPISoap odsAPISoap = null;

	private DisconnectionAdapter() {

	}

	public static DisconnectionAdapter getInstance() {
		if (instance == null)
			instance = new DisconnectionAdapter();

		return instance;
	}

	private void initializeSoapServer() throws DisconnectionException {
		logger.debug("Initialization Server");
		if (odsAPISoap != null) {
			return;
		}
		if (odsAPI == null) {
			try {
				odsAPI = new ODSAPI(new URL(vDisconnectionURL));
			} catch (MalformedURLException e) {

				logger.error(e, e);
				throw new DisconnectionException("7001", e.getMessage());
			}
		}

		if (odsAPISoap == null) {
			odsAPISoap = odsAPI.getODSAPISoap();
		}
	}

	public int deactivateDial(String msisdn) throws DisconnectionException {
		initializeSoapServer();

		int response = odsAPISoap.deactivateDial(msisdn);
		if (response != 1) {
			if (response == 0) {
				throw new DisconnectionException("1", "Error");
			} else if (response == -1) {
				throw new DisconnectionException("2", "NOT FOUND");
			}
		}
		return 0;

	}

	public void setConfiguration(String[] configuration) {
		logger.debug("DisconnectionURL:" + configuration[0]);

		this.configuration = configuration;
		this.vDisconnectionURL = this.configuration[0];

	}
}
