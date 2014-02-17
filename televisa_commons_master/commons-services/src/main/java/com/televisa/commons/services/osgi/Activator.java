package com.televisa.commons.services.osgi;

import com.squeakysand.osgi.framework.BasicBundleActivator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bundle activator for com.televisa.generics - televisa-generics-services.
 */
public class Activator extends BasicBundleActivator {

    private static final Logger LOG = LoggerFactory.getLogger(Activator.class);

    public Activator() {
		super(LOG);
	}

}
