/*
 * Nanning Aspects
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package com.tirsen.nanning;

import junit.framework.Test;
import junit.framework.TestSuite;
import com.tirsen.nanning.samples.TraceInterceptorTest;
import com.tirsen.nanning.*;


/**
 * TODO document UnitTestSuite
 *
 * <!-- $Id: UnitTestSuite.java,v 1.2 2002-11-18 20:56:30 tirsen Exp $ -->
 *
 * @author $Author: tirsen $
 * @version $Revision: 1.2 $
 */
public class UnitTestSuite
{
    ///CLOVER:OFF
    public static Test suite()
    {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(AspectClassTest.class);
        suite.addTestSuite(AspectRepositoryTest.class);
        suite.addTestSuite(PerformanceTest.class);
        suite.addTestSuite(AttributesTest.class);
        suite.addTestSuite(TraceInterceptorTest.class);
        suite.addTestSuite(AspectsTest.class);
        return suite;
    }
    ///CLOVER:ON
}