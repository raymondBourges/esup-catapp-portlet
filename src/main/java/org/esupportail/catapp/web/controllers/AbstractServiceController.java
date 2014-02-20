package org.esupportail.catapp.web.controllers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.portlet.ModelAndView;


public abstract class AbstractServiceController implements MessageSourceAware{

	protected static final String SERVICE_URL = "catappSrvUrl";
	protected static final String ID_DOMAIN = "idDomain";
	
//	/** SessionRegistry used by this controller */
//	@Autowired protected SessionRegistry sessionRegistry;
//	/** Authenticator used by this controller */
//	@Autowired protected Authenticator authenticator;
//	/** viewSelector used by this controller */
//	@Autowired protected ViewSelectorDefault viewSelector;
	/** messageSource used by this controller */
	@Autowired protected MessageSource messageSource;
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}	
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) {	
		ModelMap model = new ModelMap();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
    	PrintStream print = new PrintStream(output);
    	ex.printStackTrace(print);
    	String exceptionStackTrace = new String(output.toByteArray());
    	
    	String errMsg1 = getErrorMessage(exceptionStackTrace);
    	String errMsg2 = getCausedByMessage(exceptionStackTrace);
        if(!errMsg1.equals("") || !errMsg2.equals("")){
        	model.put("exceptionMessage", errMsg1 + errMsg2);
        }else{
        	model.put("exceptionMessage",ex.getMessage());
        }
        return new ModelAndView("exception", model);
	}
	
	private String getErrorMessage(String errMsg){
		String exceptionMessage = errMsg;
		int indexOfRemoteMessage = exceptionMessage.indexOf("Remote Stack Trace:");
    	if(indexOfRemoteMessage > 1){
    		exceptionMessage = exceptionMessage.substring(indexOfRemoteMessage  + 20);
    		
    		int endIndex = exceptionMessage.indexOf("\n");
    		exceptionMessage = exceptionMessage.substring(0, endIndex);
    		return "<br/>" + exceptionMessage ;
    	}
    	return "";
	}
	
	
	private String getCausedByMessage(String errMsg){
		String exceptionMessage = errMsg;
		int indexOfCausedBy = exceptionMessage.indexOf("Caused by:");
    	if(indexOfCausedBy > 1){
    		exceptionMessage = exceptionMessage.substring(indexOfCausedBy  + 11);
    		int lineIndex = exceptionMessage.indexOf("\n");
    		exceptionMessage = exceptionMessage.substring(0, lineIndex);
    		
    		int doubleColoneIndex = exceptionMessage.indexOf(":") + 1;
    		if(doubleColoneIndex > 0){
    			
    			String className = exceptionMessage.substring(0, doubleColoneIndex - 1);
    			className = className.substring(className.lastIndexOf(".") +1);
    			exceptionMessage = "[" + className + "] " + exceptionMessage.substring(doubleColoneIndex + 1);
    			
    		}
    		return "<br/>" + exceptionMessage;
    	}
    	return "";
	}
	
//	protected NuxeoResource getNuxeoResourceFromPortletSession(PortletRequest request){
//		NuxeoResource nuxeoResource = (NuxeoResource) request.getPortletSession().getAttribute("nuxeoResource");
//		if(nuxeoResource == null){
//			nuxeoResource = new NuxeoResource();
//			request.getPortletSession().setAttribute("nuxeoResource", nuxeoResource);
//			sessionRegistry.registerNewSession(request.getPortletSession().getId(), nuxeoResource);
//		}
//		return nuxeoResource;
//	}
//	
//	protected void makeNuxeoSession(PortletRequest request,NuxeoResource nuxeoResource) throws Exception{
//		PortletPreferences prefs = request.getPreferences();
//		String nuxeoHost = prefs.getValue(NUXEO_HOST, "");
//		String nuxeoSecret = prefs.getValue(NUXEO_SECRET, "");
//		String uid;
//		try {
//			uid = authenticator.getUser().getLogin();
//		} catch (Exception e) {
//			// local test mode with pluto plugin.
//			uid = "Administrator";
//		}
//		nuxeoResource.makeSession(nuxeoHost, uid, nuxeoSecret);
//	}
	
}
