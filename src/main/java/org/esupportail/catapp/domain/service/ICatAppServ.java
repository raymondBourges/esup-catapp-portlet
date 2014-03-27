package org.esupportail.catapp.domain.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public interface ICatAppServ {

    /**
     * Initialise la liste des applications autorisées à l'utilisateur
     * et complète l'arbre
     */
    void initData(String wsUrl, String domId, String user) throws IOException, InterruptedException;

    /**
     * @return L'arbre des domaines à partir de la racine
     * spécifiée dnas les préférences de la portlet
     */
    JsonNode getDomainsTree();

    /**
     * @return La liste de toutes les applications
     */
    JsonNode getApplications();

    List<String> getCodesApplications();

    /**
     * @return L'application correspondant au code
     * @throws InterruptedException IOException
     */
    JsonNode getApplication(String code) throws InterruptedException, IOException;
}
