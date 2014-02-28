package org.esupportail.catapp.domain.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.esupportail.catapp.model.Application;
import org.esupportail.catapp.model.Domaine;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.esupportail.catapp.model.DomainesTree;

import javax.ws.rs.core.Response;

public interface ICatAppServ {

//	/**
//	 * @return La liste de tous les domaines
//	 * @throws InterruptedException
//	 */
//	Response getDomaines() throws InterruptedException;

	/**
	 * @return Le domaine
	 * @throws InterruptedException
	 */
	List<Domaine> getDomaines(String domId, String user) throws InterruptedException, IOException;

    /**
     * @return Le domaine correspondant au code
     * @throws InterruptedException
     */
    Domaine getDomaine(String code, String user) throws InterruptedException, MalformedURLException;

    /**
     * @return L'arbre des domaines dont la racine correspond au code
     * @throws InterruptedException
     */
    DomainesTree getDomainesTree(String domId, String user) throws InterruptedException, MalformedURLException;

	/**
	 * @return La liste de toutes les applications
	 * @throws InterruptedException
	 */
	List<Application> getApplications(String user) throws InterruptedException, IOException;

    /**
     * @return L'application correspondant au code
     * @throws InterruptedException
     */
    Application getApplication(String code, String user) throws InterruptedException, MalformedURLException;
}
