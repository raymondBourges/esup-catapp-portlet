package org.esupportail.catapp.web.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.*;

import org.esupportail.catapp.model.Application;
import org.esupportail.catapp.domain.service.ICatAppServ;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.esupportail.catapp.model.DomainsTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        PortletPreferences prefs = request.getPreferences();
        String[] favoris = prefs.getValues("favoris", null);
        List<Application> appsUser = catalogueService.getApplications();
        List<Application> foundFav = new ArrayList<Application>();
        List<Application> noAccessFav = new ArrayList<Application>();
        ArrayList<String> keepedFav = new ArrayList<String>();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        for (String favori : favoris) {
            for(Application app : appsUser) {
                if(app.getCode().equals(favori)) {
                    foundFav.add(app);
                    find = true;
                    keepedFav.add(favori);
                    break;
                }
            }
            if(!find) {
                Application noAccessApp = catalogueService.getApplication(favori);
                if(noAccessApp != null) {
                    noAccessFav.add(noAccessApp);
                    keepedFav.add(favori);
                }
            }
            find = false;
        }
        try {
            String[] newFav = new String[keepedFav.size()];
            prefs.setValues("favoris", keepedFav.toArray(newFav));
        } catch (ReadOnlyException e) {
            LOG.debug(e.getMessage());
        }
        view.addStaticAttribute("apps", foundFav);
        view.addStaticAttribute("noAccessApps", noAccessFav);
        return view;
	}


    @ResourceMapping(value = "getAppsByDom")
    public View getAppsByDom(ResourceRequest request, @RequestParam("p1") final String query) throws IOException, InterruptedException {
        String[] apps = query.split(",");
        List<Application> appsByDom = new ArrayList<Application>();
        for (int i = 0; i < apps.length; i++) {
            Application app = catalogueService.getApplication(apps[i]);
            if(app != null) {
                appsByDom.add(app);
            }
        }
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.addStaticAttribute("appsByDom", appsByDom);
        return view;
    }

    @ResourceMapping(value = "doms")
    public View getDomains(ResourceRequest request) throws IOException, InterruptedException {
//        String userId = request.getRemoteUser();
        String userId = "nhenry";
        PortletPreferences prefs = request.getPreferences();
        String idDomain = prefs.getValue("idDomain", null).trim();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        DomainsTree result = catalogueService.getDomainsTree(idDomain, userId);
        view.addStaticAttribute("doms", result);
        return view;
    }

    @ResourceMapping(value = "updateFavori")
    public View updateFavorite(ResourceRequest request, @RequestParam("p1") final String query) {
        PortletPreferences prefs = request.getPreferences();
        String[] favoris = prefs.getValues("favoris", null);
        String test = query;
//        try {
//            prefs.setValues("favoris", holdedFav.toArray(newFav));
//            message = "L'application "+query+" est supprimée des favoris";;
//        } catch (ReadOnlyException e) {
//            LOG.debug("La propriété 'favoris' n'est pas modifiable");
//            message = "L'application "+query+" n'a pas pu être supprimée";
//        }
        MappingJackson2JsonView view = new MappingJackson2JsonView();
//        view.addStaticAttribute("message", message);
        return view;
    }

    @ResourceMapping(value = "deleteFavori")
    public View deleteFavorite(ResourceRequest request, @RequestParam("p1") final String query) throws IOException, ValidatorException {
        PortletPreferences prefs = request.getPreferences();
        String[] favoris = prefs.getValues("favoris", null);
        ArrayList<String> holdedFav = new ArrayList<String>();
        String message;
        for (String favori : favoris) {
            if(!favori.equals(query)) {
                holdedFav.add(favori);
            }
        }
        String[] newFav = new String[holdedFav.size()];
        try {
            prefs.setValues("favoris", holdedFav.toArray(newFav));
            prefs.store();
            message = "L'application "+query+" est supprimée des favoris";;
        } catch (ReadOnlyException e) {
            LOG.debug("La propriété 'favoris' n'est pas modifiable");
            message = "L'application "+query+" n'a pas pu être supprimée";
        }
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.addStaticAttribute("message", message);
        return view;
    }

    @ResourceMapping(value = "addFavori")
    public View addFavorite(ResourceRequest request, @RequestParam("p1") final String query) throws IOException, ValidatorException {
        PortletPreferences prefs = request.getPreferences();
        String[] favoris = prefs.getValues("favoris", null);
        ArrayList<String> holdedFav = new ArrayList<String>();
        String message;
        for (String favori : favoris) {
            holdedFav.add(favori);
        }
        holdedFav.add(query);
        String[] newFav = new String[holdedFav.size()];
        try {
            prefs.setValues("favoris", holdedFav.toArray(newFav));
            prefs.store();
            message = "L'application "+query+" a été ajouter aux favoris";;
        } catch (ReadOnlyException e) {
            LOG.debug("La propriété 'favoris' n'est pas modifiable");
            message = "L'application "+query+" n'a pas pu être ajoutée";
        }
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.addStaticAttribute("message", message);
        return view;
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
