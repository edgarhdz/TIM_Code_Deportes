package com.televisa.commons.taglib.osgi;

import com.squeakysand.osgi.framework.BasicBundleActivator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bundle activator for com.televisa.commons - televisa-commons-taglib.
 */
public class Activator extends BasicBundleActivator {

    private static final Logger LOG = LoggerFactory.getLogger(Activator.class);

    public Activator() {
        super(LOG);
    }

}
