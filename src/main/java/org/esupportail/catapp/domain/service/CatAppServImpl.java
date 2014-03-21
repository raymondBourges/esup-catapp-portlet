package org.esupportail.catapp.domain.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationConfig;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.protocol.HTTP;
import org.esupportail.catapp.model.Application;
import org.esupportail.catapp.model.Domain;
import org.esupportail.catapp.model.DomainsTree;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	public List<Application> getApplications() throws InterruptedException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		WebTarget applications = createWebTarget(wsAppPath);
//		applications.request().get().getLinks().iterator().next().getRel();
//        String test = applications.request().get(String.class);
        return mapper.readValue(applications.request().get(String.class), new TypeReference<List<Application>>() {
        });
	}

    @Override
    public Application getApplication(String code) throws InterruptedException, MalformedURLException {
        ObjectMapper mapper = new ObjectMapper();
        WebTarget application = createWebTarget(wsAppPath)
                                .path(code);
//		applications.request().get().getLinks().iterator().next().getRel();
        try {
            Response rsp = application.request().get();
            String entity = application.request().get().readEntity(String.class);
            return mapper.readValue(application.request().get().readEntity(String.class), Application.class);
        } catch (IOException e) {
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
            Invocation.Builder rs = domainsTree.path(domId)
                    .path("/tree")
                    .queryParam(wsUserPath, user)
                    .request();
            Response rsp = rs.get();
            String entity = rsp.readEntity(String.class);
            ObjectMapper treeObjectMapper = new ObjectMapper();
            DomainsTree dt = treeObjectMapper.readValue(entity, DomainsTree.class);
            return dt;
        } catch (ProcessingException | WebApplicationException | IOException e) {
            throw new InterruptedException(e.getMessage());
        }
    }


    public String getResponseBody(final HttpEntity entity) throws IOException, ParseException {
        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }

        InputStream instream = entity.getContent();

        if (instream == null) {
            return "";
        }

        if (entity.getContentLength() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(

                    "HTTP entity too large to be buffered in memory");
        }

        StringBuilder buffer = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(instream, HTTP.UTF_8));

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

        } finally {
            instream.close();
            reader.close();
        }
        return buffer.toString();

    }

}

