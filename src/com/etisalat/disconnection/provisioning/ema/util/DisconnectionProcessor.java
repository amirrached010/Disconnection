package com.etisalat.disconnection.provisioning.ema.util;

import org.apache.log4j.Logger;

import com.etisalat.disconnection.provisioning.ema.DisconnectionException;
import com.etisalat.disconnection.provisioning.ema.DisconnectionAdapter;
import com.etisalat.disconnection.provisioning.ema.models.DisconnectionRequest;

public class DisconnectionProcessor {

	private static Logger logger = Logger
			.getLogger(DisconnectionProcessor.class);

	public static String processRequest(DisconnectionRequest req,
			String[] configuration) throws DisconnectionException {

		DisconnectionAdapter vs = DisconnectionAdapter.getInstance();
		vs.setConfiguration(configuration);

		if (req.getAction().equals("SET")) {
			logger.debug("At SET command");

			logger.debug("Deactivate TE command");
			vs.deactivateDial(req.getMsisdn());
			logger.debug("Deactivate TE end successfully");

		}

		return "";
	}

}
