package org.esupportail.catapp.web.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.esupportail.catapp.model.Application;
import org.esupportail.catapp.model.Domaine;
import org.esupportail.catapp.domain.service.ICatAppServ;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.portlet.mvc.AbstractController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import static org.esupportail.catapp.web.controllers.CatalogueController.ResourceUrlW.wrap;

@Controller("catalogueController")
@RequestMapping("VIEW")
public class CatalogueController {

	private ICatAppServ catalogueService;

    @Autowired
	private CatalogueController(ICatAppServ catalogueService) {
		this.catalogueService = catalogueService;
	}

    public static CatalogueController catalogueController(
			ICatAppServ catalogueService) {
		return new CatalogueController(catalogueService);
	}

    /**
     * Log instance.
     */
    private static final Log LOG = LogFactory.getLog(CatalogueController.class);

	/**
	 * Traitement 'Render'
	 * 
	 * @param request
	 *            RenderRequest la requête
	 * @param response
	 *            RenderResponse la réponse
	 */
    /**
     * Traitement 'Render'
     *
     * @param request
     *            RenderRequest la requête
     * @param response
     *            RenderResponse la réponse
     */
    @RenderMapping
    public String goHome(RenderRequest request, RenderResponse response, ModelMap model) throws IOException {
        String userId = request.getRemoteUser();
        model.addAttribute("resourceURL",
                wrap(response.createResourceURL())
                        .withResId("@@id@@")
                        .withResParam("p1", "__p1__")
                        .withResParam("p2", "__p2__")
                        .withResParam("p3", "__p3__")
                        .withResParam("p4", "__p4__")
                        .resourceUrl);
        return "catalogue";
    }

    @ResourceMapping(value = "apps")
	public View getApplications(ResourceRequest request) throws IOException, InterruptedException {
        Boolean find = false;
		String userId = request.getRemoteUser();
        PortletPreferences prefs = request.getPreferences();
        String[] favoris = prefs.getValues("favoris", null);
        List<String> removedApp = new ArrayList<String>();
        List<Application> appsUser = catalogueService.getApplications(userId);
        List<Application> foundFav = new ArrayList<Application>();
        List<Application> lostFav = new ArrayList<Application>();
        ArrayList<String> removedFav = new ArrayList<String>();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        for (String favori : favoris) {
            for(Application app : appsUser) {
                if(app.getCode().equals(favori)) {
                    foundFav.add(app);
                    find = true;
                    break;
                }
            }
            if(!find) {
                Application rmvApp = catalogueService.getApplication(favori, userId);
                if(rmvApp != null) {
                    lostFav.add(rmvApp);
                } else {
                    removedFav.add(favori);
                }
            }
            find = false;
        }
        try {
            String[] newFav = new String[removedFav.size()];
            prefs.setValues("favoris", removedFav.toArray(newFav));
        } catch (ReadOnlyException e) {
            LOG.debug(e.getMessage());
        }
        view.addStaticAttribute("apps", foundFav);
        view.addStaticAttribute("removedApps", lostFav);
        return view;
	}

    @ResourceMapping(value = "doms")
    public View getDomaines(ResourceRequest request) throws IOException, InterruptedException {
		String userId = request.getRemoteUser();
        PortletPreferences prefs = request.getPreferences();
        String idDomain = prefs.getValue("idDomain", null).trim();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        List<Domaine> result = catalogueService.getDomaines(idDomain, userId);
        view.addStaticAttribute("doms", result);
        return view;
    }

    @ActionMapping(params="action=deleteFavori")
    public void deleteFavorite(ResourceRequest request, @RequestParam("p1") final String query) {
        PortletPreferences prefs = request.getPreferences();
        String[] favoris = prefs.getValues("favoris", null);
        ArrayList<String> holdedFav = new ArrayList<String>();
        for (String favori : favoris) {
            if(!favori.equals(query)) {
                holdedFav.add(favori);
            }
        }
        String[] newFav = new String[holdedFav.size()];
        try {
            prefs.setValues("favoris", holdedFav.toArray(newFav));
        } catch (ReadOnlyException e) {
            LOG.debug("La propriété 'favoris' n'est pas modifiable");
        }
    }

    public static final class ResourceUrlW {
        private final ResourceURL resourceUrl;
        ResourceUrlW withResId(String resId) {
            resourceUrl.setResourceID(resId);
            return this;
        }
        ResourceUrlW withResParam(String key, String value) {
            resourceUrl.setParameter(key, value);
            return this;
        }

        private ResourceUrlW(ResourceURL resourceUrl) {
            this.resourceUrl = resourceUrl;
        }

        public static ResourceUrlW wrap(ResourceURL resourceUrl) {
            return new ResourceUrlW(resourceUrl);
        }

        public ResourceURL getResourceUrl() {
            return this.resourceUrl;
        }
    }

}
