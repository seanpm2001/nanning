/*
 * Nanning Aspects
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package com.tirsen.nanning.jelly;

import org.apache.commons.jelly.TagSupport;
import org.apache.commons.jelly.XMLOutput;

/**
 * TODO document TargetTag
 *
 * <!-- $Id: TargetTag.java,v 1.2 2002-12-03 13:55:24 lecando Exp $ -->
 *
 * @author $Author: lecando $
 * @version $Revision: 1.2 $
 */
public class TargetTag extends TagSupport
{
    public void doTag(XMLOutput xmlOutput) throws Exception
    {
        Class targetClass =
                Thread.currentThread().getContextClassLoader().loadClass(getBodyText().trim());
        ((AspectTag) getParent()).setTarget(targetClass);
    }
}
