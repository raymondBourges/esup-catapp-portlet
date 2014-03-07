package org.esupportail.catapp.domain.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.esupportail.catapp.model.Application;
import org.esupportail.catapp.model.Domain;

import org.esupportail.catapp.model.DomainsTree;

public interface ICatAppServ {

//	/**
//	 * @return La liste de tous les domaines
//	 * @throws InterruptedException
//	 */
//	Response getDomains() throws InterruptedException;

	/**
	 * @return Le domaine
	 * @throws InterruptedException
	 */
	List<Domain> getDomains(String domId, String user) throws InterruptedException, IOException;

    /**
     * @return Le domaine correspondant au code
     * @throws InterruptedException
     */
    Domain getDomain(String code, String user) throws InterruptedException, MalformedURLException;

    /**
     * @return L'arbre des domaines dont la racine correspond au code
     * @throws InterruptedException
     */
    DomainsTree getDomainsTree(String domId, String user) throws InterruptedException, MalformedURLException;

	/**
	 * @return La liste de toutes les applications
	 * @throws InterruptedException
	 */
	List<Application> getApplications(String user) throws InterruptedException, IOException;

    /**
     * @return L'application correspondant au code
     * @throws InterruptedException
     */
    Application getApplication(String code) throws InterruptedException, MalformedURLException;
}
