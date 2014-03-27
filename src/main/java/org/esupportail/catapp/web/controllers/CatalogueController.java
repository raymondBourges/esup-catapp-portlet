package org.esupportail.catapp.web.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.esupportail.catapp.domain.service.ICatAppServ;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

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
    @RenderMapping
    public String goHome(RenderRequest request, RenderResponse response, ModelMap model) throws IOException, InterruptedException {
        model.addAttribute("resourceURL",
                wrap(response.createResourceURL())
                        .withResId("@@id@@")
                        .withResParam("p1", "__p1__")
                        .withResParam("p2", "__p2__")
                        .withResParam("p3", "__p3__")
                        .withResParam("p4", "__p4__")
                        .resourceUrl);
        PortletURL actionURL = response.createActionURL();
        actionURL.setParameter("action", "@@id@@");
        actionURL.setParameter("p1", "__p1__");
        actionURL.setParameter("p2", "__p2__");
        actionURL.setParameter("p3", "__p3__");
        model.addAttribute("actionURL", actionURL);
        bindInitialModel(request);
        return "catalogue";
    }

    /**
     * Add ressourceURL to spring MVC model
     * @param request
     * @return completed spring MVC model
     */
    protected void bindInitialModel(final RenderRequest request) throws IOException, InterruptedException {
        PortletPreferences prefs = request.getPreferences();
        String userId = request.getRemoteUser();
        String wsUrl = prefs.getValue("wsUrl", null).trim();
        String idDomain = prefs.getValue("idDomain", null).trim();
        catalogueService.initData(wsUrl, idDomain, userId);
    }

    @ResourceMapping(value = "apps")
	public View getApplications(ResourceRequest request) throws IOException, InterruptedException {
        Boolean find = false;
        PortletPreferences prefs = request.getPreferences();
        String[] favoris = prefs.getValues("favoris", null);
        JsonNode appsUser = catalogueService.getApplications();
        ArrayNode allowedFav = new ObjectMapper().createArrayNode();
        ArrayNode disallowedFavs = new ObjectMapper().createArrayNode();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        for (String favori : favoris) {
            for (JsonNode jApp : appsUser) {
                if(favori.equals(jApp.get("code").asText())) {
                    allowedFav.add(jApp);
                    find = true;
                    break;
                }
            }
            if(!find) {
                JsonNode disallowedFav = catalogueService.getApplication(favori);
                if(disallowedFav != null) {
                    disallowedFavs.add(disallowedFav);
                    break;
                }
            }
        }
        view.addStaticAttribute("apps", allowedFav);
        view.addStaticAttribute("noAccessApps", disallowedFavs);
        return view;
	}

    @ResourceMapping(value = "doms")
    public View getDomains(ResourceRequest request) throws IOException, InterruptedException {
//        String userId = request.getRemoteUser();
        String userId = "nhenry";
        PortletPreferences prefs = request.getPreferences();
        String idDomain = prefs.getValue("idDomain", null).trim();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        JsonNode jdoms = catalogueService.getDomainsTree();
//        DomainsTree result = catalogueService.getDomainsTree(idDomain, userId);
        view.addStaticAttribute("doms", jdoms);
        return view;
    }

    /**
     * Receive the key, action parameters from UI.
     * Because the action value is updateFavorite, update updateFavorite will be called.
     * @param request
     * @param response
     * @throws Exception
     */
    @ActionMapping(params="action=updateFavorite")
    public void updateFavorite(@RequestParam(value = "p1") String favCodes,
                               ActionRequest request, ActionResponse response) throws Exception {
        PortletPreferences prefs = request.getPreferences();
        String[] favPrefs = favCodes.split(",");
        try {
            prefs.setValues("favoris", favPrefs);
            prefs.store();
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
