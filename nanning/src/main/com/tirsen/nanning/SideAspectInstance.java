/*
 * Nanning Aspects
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package com.tirsen.nanning;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;


/**
 * TODO document AspectDefinition
 *
 * <!-- $Id: SideAspectInstance.java,v 1.4 2002-11-25 12:17:07 lecando Exp $ -->
 *
 * @author $Author: lecando $
 * @version $Revision: 1.4 $
 */
class SideAspectInstance {
    private AspectDefinition aspectDefinition;
    private Class interfaceClass;
    private Interceptor[] interceptors;
    private Object target;
    private Interceptor[][] methodInterceptors;

    public SideAspectInstance(AspectDefinition aspectDefinition) {
        this.aspectDefinition = aspectDefinition;
    }

    public void setInterface(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setTarget(Object target) {
        aspectDefinition.checkTarget(target);
        this.target = target;
    }

    public Class getInterfaceClass() {
        return interfaceClass;
    }

    public Interceptor[] getAllInterceptors() {
        return interceptors;
    }

    void setInterceptors(Interceptor[] interceptors) {
        this.interceptors = interceptors;
        Method[] methods = aspectDefinition.getInterfaceClass().getMethods();
        methodInterceptors = new Interceptor[methods.length][];
        List interceptorsForMethod = new ArrayList(methods.length);
        for (int methodIndex = 0; methodIndex < methods.length; methodIndex++) {
            Method method = methods[methodIndex];
            for (int interceptorIndex = 0; interceptorIndex < interceptors.length; interceptorIndex++) {
                Interceptor interceptor = interceptors[interceptorIndex];
                if (interceptor instanceof FilterMethodsInterceptor) {
                    if (((FilterMethodsInterceptor) interceptor).interceptsMethod(method)) {
                        interceptorsForMethod.add(interceptor);
                    }
                } else {
                    interceptorsForMethod.add(interceptor);
                }
            }
            methodInterceptors[methodIndex] =
                    (Interceptor[]) interceptorsForMethod.toArray(new Interceptor[interceptorsForMethod.size()]);
            interceptorsForMethod.clear();
        }
    }

    public Object getTarget() {
        return target;
    }

    public Interceptor[] getInterceptorsForMethod(Method method) {
        return methodInterceptors[aspectDefinition.getMethodIndex(method)];
    }
}
