/*
 * Nanning Aspects
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package com.tirsen.nanning.definition;

import com.tirsen.nanning.definition.InterceptorDefinition;
import com.tirsen.nanning.definition.DefinitionAwareInterceptor;
import com.tirsen.nanning.attribute.Attributes;
import com.tirsen.nanning.definition.FilterMethodsInterceptor;

import java.lang.reflect.Method;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * basic interceptor with methodNameFilter support.
 * TODO document BasicInterceptor
 *
 * <!-- $Id: BasicInterceptor.java,v 1.1 2003-01-12 13:25:40 tirsen Exp $ -->
 *
 * @author $Author: tirsen $
 * @version $Revision: 1.1 $
 */

public abstract class BasicInterceptor implements DefinitionAwareInterceptor, FilterMethodsInterceptor {
    public static String METHOD_NAME_FILTER_ATTRIBUTE_NAME = "methodNameFilter";

    private InterceptorDefinition interceptorDefinition = null;

    /**
     * This method reads the methodNameFilter attribute from interceptor definition
     * compiles it as a pattern and matches with the passed method's name.
     * If the pattern matches it returns true.
     * If there is not methodNameFilter attribute specified then returns true.
     *
     * @param method
     * @return
     */
    public boolean interceptsMethod(Method method) {
        InterceptorDefinition interceptorDefinition = getInterceptorDefinition();
        String methodNameFilterPattern = (String) interceptorDefinition.getAttribute(METHOD_NAME_FILTER_ATTRIBUTE_NAME);

        if (methodNameFilterPattern != null) {
            methodNameFilterPattern = (String) interceptorDefinition.getAttribute(METHOD_NAME_FILTER_ATTRIBUTE_NAME);
        } else {
            // try the runtime-attributes
            try {
                methodNameFilterPattern = Attributes.getAttribute(this.getClass(), METHOD_NAME_FILTER_ATTRIBUTE_NAME);
                // qdox returns [.*] as [. ] so replace [. ] with [.*]; qdox should fix this or we have got to come up with a better work-around
                methodNameFilterPattern = Pattern.compile(". ").matcher(methodNameFilterPattern).replaceAll(".*");

            } catch (Exception e) {
                // bad stuff happend so let it intercept
                return true;
            }

        }
        if (methodNameFilterPattern != null) {
            Matcher m = Pattern.compile(methodNameFilterPattern).matcher(method.getName());
            return m.matches();
        }
        // no attribute specified return true.
        return true;
    }

    public InterceptorDefinition getInterceptorDefinition() {
        return interceptorDefinition;
    }

    public void setInterceptorDefinition(InterceptorDefinition interceptorDefinition) {
        this.interceptorDefinition = interceptorDefinition;
    }
}