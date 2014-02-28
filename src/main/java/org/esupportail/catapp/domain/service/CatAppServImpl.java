package org.esupportail.catapp.domain.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.esupportail.catapp.model.Application;
import org.esupportail.catapp.model.Domaine;
import org.esupportail.catapp.model.DomainesTree;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;


public class CatAppServImpl implements ICatAppServ {

	private String wsCatAppServiceURL;
	private String wsDomainPath;
	private String wsAppPath;
	private String wsUserPath;
	private WebTarget applications;

    private CatAppServImpl(String wsUrl, String wsDomainPath, String wsUserPath, String wsAppPath) {
		this.wsCatAppServiceURL = wsUrl;
		this.wsDomainPath = wsDomainPath;
		this.wsUserPath = wsUserPath;
		this.wsAppPath = wsAppPath;
	}

	public static CatAppServImpl CatAppService(String wsUrl, String wsDomainPath, String wsUserPath, String wsAppPath)
			throws MalformedURLException {
		return new CatAppServImpl(wsUrl, wsDomainPath, wsUserPath, wsAppPath);
	}
	
	/**
	 * Create the WebTarget.
	 * 
	 * @return WebResource
	 * @throws MalformedURLException
	 */
	public WebTarget createWebTarget(final String path) throws MalformedURLException {
		ClientConfig config = new ClientConfig()
				.connectorProvider(new ApacheConnectorProvider());
		Client client = ClientBuilder.newClient(config);
		WebTarget domainesClient = client.target(wsCatAppServiceURL);
		WebTarget responseWebTarget = domainesClient.path(path);
		return responseWebTarget;
	}

	@Override
	public List<Application> getApplications(String user) throws InterruptedException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		WebTarget applications = createWebTarget(wsAppPath)
                                .queryParam(wsUserPath, user);
//		applications.request().get().getLinks().iterator().next().getRel();
        return mapper.readValue(applications.request().get(String.class), new TypeReference<List<Application>>() {
        });
	}

    @Override
    public Application getApplication(String code, String user) throws InterruptedException, MalformedURLException {
            try {
                WebTarget application = createWebTarget(wsAppPath);
                return application.path(code).request().get(Application.class);
            } catch (ProcessingException | WebApplicationException e) {
                throw new InterruptedException(e.getMessage());
            }
    }

    //	@Override
//	public Response getDomaines() throws MalformedURLException {
//		WebTarget domaines = createWebTarget("domaines");
//		return domaines.request().get();
//	}

	@Override
	public List<Domaine> getDomaines(String domId, String user) throws InterruptedException, IOException {
        WebTarget domaines = createWebTarget(wsDomainPath)
                            .path(domId)
                            .queryParam(wsUserPath, user);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(domaines.request().get(String.class), new TypeReference<List<Domaine>>() {
        });
	}

    @Override
    public Domaine getDomaine(String code, String user) throws InterruptedException, MalformedURLException {
            try {
                WebTarget domaine = createWebTarget(wsDomainPath);
                return domaine.path(code).request().get(Domaine.class);
            } catch (ProcessingException | WebApplicationException e) {
                throw new InterruptedException(e.getMessage());
            }
        }

    @Override
    public DomainesTree getDomainesTree(String domId, String user) throws InterruptedException, MalformedURLException {
        try {
            WebTarget domainesTree = createWebTarget(wsDomainPath);
            return domainesTree.path(domId)
                    .path("/tree")
                    .queryParam(wsUserPath, user)
                    .request().get(DomainesTree.class);
        } catch (ProcessingException | WebApplicationException e) {
            throw new InterruptedException(e.getMessage());
        }
    }
}

//    @JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    @Value
//    static class DomainList {
//        List<Domaine> result;
//        @JsonCreator
//        public static DomainList domainList(@JsonProperty("result") List<Domaine> result) {
//            return new DomainList(result);
//        }
//    }
//
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    @JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
//    @Value
//    @FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
//    @Wither
//    static class Domaine {
//        String code, parent, libelle;
//        Date creation, maj;
//        List<Application> applications;
//        List<Domaine> domaines;
//
//        @SuppressWarnings("unchecked")
//        @JsonCreator
//        public static Domaine Domaine(Map<String, Object> delegate) {
//            return new Domaine(
//                    (String) delegate.get("code"),
//                    (String) delegate.get("parent"),
//                    (String) delegate.get("libelle"),
//                    (List<Application>) delegate.get("applications"),
//                    (List<Domaine>) delegate.get("domaines"),
//                    (Date) delegate.get("creation"),
//                    (Date) delegate.get("maj"));
//        }
//    }

