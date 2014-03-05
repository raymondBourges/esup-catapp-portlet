package org.esupportail.catapp.domain.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.esupportail.catapp.model.Application;
import org.esupportail.catapp.model.Domain;
import org.esupportail.catapp.model.DomainsTree;
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
		WebTarget domainsClient = client.target(wsCatAppServiceURL);
		WebTarget responseWebTarget = domainsClient.path(path);
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
//	public Response getDomains() throws MalformedURLException {
//		WebTarget domains = createWebTarget("domains");
//		return domains.request().get();
//	}

	@Override
	public List<Domain> getDomains(String domId, String user) throws InterruptedException, IOException {
        WebTarget domains = createWebTarget(wsDomainPath)
                            .path(domId)
                            .queryParam(wsUserPath, user);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(domains.request().get(String.class), new TypeReference<List<Domain>>() {
        });
	}

    @Override
    public Domain getDomain(String code, String user) throws InterruptedException, MalformedURLException {
            try {
                WebTarget domain = createWebTarget(wsDomainPath);
                return domain.path(code).request().get(Domain.class);
            } catch (ProcessingException | WebApplicationException e) {
                throw new InterruptedException(e.getMessage());
            }
        }

    @Override
    public DomainsTree getDomainsTree(String domId, String user) throws InterruptedException, MalformedURLException {
        try {
            WebTarget domainsTree = createWebTarget(wsDomainPath);
            return domainsTree.path(domId)
                    .path("/tree")
                    .queryParam(wsUserPath, user)
                    .request().get(DomainsTree.class);
        } catch (ProcessingException | WebApplicationException e) {
            throw new InterruptedException(e.getMessage());
        }
    }
}

//    @JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    @Value
//    static class DomainList {
//        List<Domain> result;
//        @JsonCreator
//        public static DomainList domainList(@JsonProperty("result") List<Domain> result) {
//            return new DomainList(result);
//        }
//    }
//
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    @JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
//    @Value
//    @FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
//    @Wither
//    static class Domain {
//        String code, parent, libelle;
//        Date creation, maj;
//        List<Application> applications;
//        List<Domain> domaines;
//
//        @SuppressWarnings("unchecked")
//        @JsonCreator
//        public static Domain Domain(Map<String, Object> delegate) {
//            return new Domain(
//                    (String) delegate.get("code"),
//                    (String) delegate.get("parent"),
//                    (String) delegate.get("libelle"),
//                    (List<Application>) delegate.get("applications"),
//                    (List<Domain>) delegate.get("domaines"),
//                    (Date) delegate.get("creation"),
//                    (Date) delegate.get("maj"));
//        }
//    }

