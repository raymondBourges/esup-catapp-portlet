package org.esupportail.catapp.web.controllers;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Scope("session")
@Controller
@RequestMapping(value = "EDIT")
public class EditPrefController { 

	protected static final String SERVICE_URL = "catappSrvUrl";
	protected static final String ID_DOMAIN = "idDomain";
    
	@RenderMapping
	protected ModelAndView handleRenderRequestInternal(RenderRequest request,
			RenderResponse response, PortletPreferences prefs) throws Exception {
    	ModelMap model = new ModelMap();
    	
		model.put(SERVICE_URL,prefs.getValue(SERVICE_URL, null));
		
		model.put(ID_DOMAIN,prefs.getValue(ID_DOMAIN, null));
		return new ModelAndView("preferences", model);
	}

   @ActionMapping(params = "action=save")
   public void action(ActionRequest request, ActionResponse response) throws PortletException, IOException {  
	   String catappSrvUrl = request.getParameter("catappSrvUrl").trim();
	   String  idDomain = request.getParameter("idDomain").trim();
	   PortletPreferences prefs = request.getPreferences();
               if (catappSrvUrl != null && !catappSrvUrl.equals(""))
                {
                    prefs.reset("catappSrvUrl");
                    prefs.setValue("catappSrvUrl", catappSrvUrl);
                }
               if (idDomain != null && !idDomain.equals(""))
               {
                   prefs.reset("idDomain");
                   prefs.setValue("idDomain", idDomain);
               }
                prefs.store();
                response.setPortletMode(PortletMode.VIEW);
    }
	
}

