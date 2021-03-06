/**
 * TmriJaxRpcOutNewAccessServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.8  Built on : May 19, 2018 (07:06:11 BST)
 */
package com.xs.jt.base.module.out.service.client;


/**
 *  TmriJaxRpcOutNewAccessServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class TmriJaxRpcOutNewAccessServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public TmriJaxRpcOutNewAccessServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public TmriJaxRpcOutNewAccessServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for writeObjectOutNew method
     * override this method for handling normal response from writeObjectOutNew operation
     */
    public void receiveResultwriteObjectOutNew(
        com.xs.jt.base.module.out.service.client.TmriJaxRpcOutNewAccessServiceStub.WriteObjectOutNewResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from writeObjectOutNew operation
     */
    public void receiveErrorwriteObjectOutNew(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for writeObjectOut method
     * override this method for handling normal response from writeObjectOut operation
     */
    public void receiveResultwriteObjectOut(
        com.xs.jt.base.module.out.service.client.TmriJaxRpcOutNewAccessServiceStub.WriteObjectOutResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from writeObjectOut operation
     */
    public void receiveErrorwriteObjectOut(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for queryObjectOut method
     * override this method for handling normal response from queryObjectOut operation
     */
    public void receiveResultqueryObjectOut(
        com.xs.jt.base.module.out.service.client.TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from queryObjectOut operation
     */
    public void receiveErrorqueryObjectOut(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for queryObjectOutNew method
     * override this method for handling normal response from queryObjectOutNew operation
     */
    public void receiveResultqueryObjectOutNew(
        com.xs.jt.base.module.out.service.client.TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutNewResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from queryObjectOutNew operation
     */
    public void receiveErrorqueryObjectOutNew(java.lang.Exception e) {
    }
}
