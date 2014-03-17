package org.esupportail.catapp.domain.service.mocks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.esupportail.catapp.domain.service.ICatAppServ;
import org.esupportail.catapp.model.Application;
import org.esupportail.catapp.model.Domain;
import org.esupportail.catapp.model.DomainsTree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MockCatAppService implements ICatAppServ {

    private final String jsonApplications = "[ {\"code\": \"SIFAC-PL\",\"title\": \"Paiement en ligne\",\"caption\": \"paiement en ligne\",\"description\": \"L'application propose un formulaire de demande d'autorisation de paiement en ligne, un outil de suivi des paiements et un mode d'emploi pour créer ces formulaires web\",\"url\": \"https://paiement.univ-rennes1.fr/JSP/menu.jsp\",\"group\": \"\",\"domains\": [\"GFC\"],\"activation\": 1 }, {\"code\": \"SIFAC-CO\",\"title\": \"Sifac consultation\",\"caption\": \"Consultation sifac\",\"description\": \"Accès en consultation, pour les personnes autorisées, aux comptes SIFAC via le web.\",\"url\": \"https://sp-sifac.univ-rennes1.fr/sifacweb/stylesheets/welcome.faces\",\"group\": \"\",\"domains\": [\"GFC\"],\"activation\": 1 }, {\"code\": \"SIFAC-DOC\",\"title\": \"Documentations et procédures Sifac\",\"caption\": \"Documentations et procédure Sifac\",\"description\": \"Accès en TEST, en fonction des droits de chacun, à l'application financière et comptable de l'Université de Rennes 1 et des établissements rattachés : IEP et 'ENSCR.\",\"url\": \"https://sp-sifac.univ-rennes1.fr/index.html\",\"group\": \"\",\"domains\": [\"GFC\"],\"activation\": 0 }, {\"code\": \"SIFAC-GEST\",\"title\": \"Sifac gestion - mandant de TEST 310 : UR1 IEP ENSCR\",\"caption\": \"Sifac gestion - mandant de TEST 310 : UR1 IEP ENSCR\",\"description\": \"Accès en TEST, en fonction des droits de chacun, à l'application financière et comptable de l'Université de Rennes 1 et des établissements rattachés : IEP et 'ENSCR.\",\"url\": \"https://sp-sifac.univ-rennes1.fr/index.html\",\"group\": \"\",\"domains\": [\"GRH\"],\"activation\": 1 }]";

    private static String jsonDomains = "[{\"code\":\"ROOT\",\"caption\":\"Domain racine\",\"parent\":null,\"domains\":[{\"code\":\"ENV-TRAVAIL\",\"caption\":\"Environnement de travail\",\"parent\":\"ROOT\",\"domains\":[],\"applications\":[]},{\"code\":\"OUTILS-COM\",\"caption\":\"Outils de communication\",\"parent\":\"ROOT\",\"domains\":[],\"applications\":[]},{\"code\":\"APPS-METIER\",\"caption\":\"Applications métier\",\"parent\":\"ROOT\",\"domains\":[{\"code\":\"PILOTAGE\",\"caption\":\"Pilotage\",\"parent\":\"APPS-METIER\",\"domains\":[],\"applications\":[]},{\"code\":\"GRH\",\"caption\":\"Gestion Ressources Humaines\",\"parent\":\"APPS-METIER\",\"domains\":[],\"applications\":[    {\"code\":\"SIFAC-GEST\",\"title\":\"Sifac gestion - mandant de TEST 310 :UR1 IEP ENSCR\",\"caption\":\"Sifac gestion - mandant de TEST 310 :UR1 IEP ENSCR\",\"description\":\"Accès en TEST, en fonction des droits de chacun, à l'application financière et comptable de l'Université de Rennes 1 et des établissements rattachés :IEP et 'ENSCR.\",\"url\":\"https://sp-sifac.univ-rennes1.fr/index.html\",\"group\":\"\",\"domains\":[\"GRH\"],\"activation\":1}]},{\"code\":\"GEE\",\"caption\":\"Gestion des enseignements et étudiants\",\"parent\":\"APPS-METIER\",\"domains\":[],\"applications\":[]}],\"applications\":[]}],\"applications\":[]},{\"code\":\"GFC\",\"caption\":\"Gestion financière et comptable\",\"parent\":\"APPS-METIER\",\"domains\":[],\"applications\":[{\"code\":\"SIFAC-PL\",\"title\":\"Paiement en ligne\",\"caption\":\"paiement en ligne\",\"description\":\"L'application propose un formulaire de demande d'autorisation de paiement en ligne, un outil de suivi des paiements et un mode d'emploi pour créer ces formulaires web\",\"url\":\"https://paiement.univ-rennes1.fr/JSP/menu.jsp\",\"group\":\"\",\"domains\":[\"GFC\"],\"activation\":1},{\"code\":\"SIFAC-C0\",\"title\":\"Sifac consultation\",\"caption\":\"Consultation sifac\",\"description\":\"Accès en consultation, pour les personnes autorisées, aux comptes SIFAC via le web.\",\"url\":\"https://sp-sifac.univ-rennes1.fr/sifacweb/stylesheets/welcome.faces\",\"group\":\"\",\"domains\":[\"GFC\"],\"activation\":1},{\"code\":\"SIFAC-DOC\",\"title\":\"Documentations et procédures Sifac\",\"caption\":\"Documentations et procédure Sifac\",\"description\":\"Accès en TEST, en fonction des droits de chacun, à l'application financière et comptable de l'Université de Rennes 1 et des établissements rattachés :IEP et 'ENSCR.\",\"url\":\"https://sp-sifac.univ-rennes1.fr/index.html\",\"group\":\"\",\"domains\":[\"GFC\"],\"activation\":0}]},{\"code\":\"GP\",\"caption\":\"Gestion patrimoine\",\"parent\":\"APPS-METIER\",\"domains\":[],\"applications\":[]}]";

    private static String jsonDomTree = "{ \"domain\" : { \"code\": \"ROOT\", \"caption\": \"Domain racine\", \"parent\": null, \"applications\": [], \"domains\" : [\"ENV-TRAVAIL\",\"OUTILS-COM\",\"APPS-METIER\",\"GFC\",\"GP\"] }, \"subDomains\": [ {  \"domain\" : {  \"code\": \"ENV-TRAVAIL\",  \"caption\": \"Environnement de travail\",  \"parent\": \"ROOT\",  \"applications\": []  },  \"subDomains\": [] }, {  \"domain\" : {  \"code\": \"OUTILS-COM\",  \"caption\": \"Outils de communication\",  \"parent\": \"ROOT\",  \"applications\": []  },  \"subDomains\": [] }, {  \"domain\" : {  \"code\": \"APPS-METIER\",  \"caption\": \"Applications métier\",  \"parent\": \"ROOT\",  \"applications\": [],  \"domains\": [\"PILOTAGE\",\"GRH\",\"GEE\"]  },  \"subDomains\": [  {   \"domain\" : {   \"code\": \"PILOTAGE\",   \"caption\": \"Pilotage\",   \"parent\": \"APPS-METIER\",   \"applications\": []   },   \"subDomains\": []  },  {   \"domain\" : {   \"code\": \"GRH\",   \"caption\": \"Gestion Ressources Humaines\",   \"parent\": \"APPS-METIER\",   \"applications\": [\"SIFAC-GEST\"]   },   \"subDomains\": []  },  {   \"domain\" : {   \"code\": \"GEE\",   \"caption\": \"Gestion des enseignements et étudiants\",   \"parent\": \"APPS-METIER\",   \"applications\": []   },   \"subDomains\": []  }  ] }, {  \"domain\" : {  \"code\": \"GFC\",  \"caption\": \"Gestion financière et comptable\",  \"parent\": \"ROOT\",  \"applications\": [\"SIFAC-PL\",\"SIFAC-CO\",\"SIFAC-DOC\"]  },  \"subDomains\": [] }, {  \"domain\" : {  \"code\": \"GP\",  \"caption\": \"Gestion patrimoine\",  \"parent\": \"ROOT\",  \"applications\": [\"GESTIMMO\",\"VIZELIA\"]  },  \"subDomains\": [] } ]}";
    @Override
    public List<Application> getApplications(String user) throws InterruptedException {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonApplications, new TypeReference<List<Application>>() {});
        } catch (IOException e) {
            return new ArrayList<Application>();
        }
    }

    @Override
    public Application getApplication(final String code) throws InterruptedException {
        Application result = null;
        for(Application app : getApplications("nhenry")) {
            if(app.getCode().equals(code)) {
                result = app;
                break;
            }
        }
        return result;
    }

    @Override
    public Domain getDomain(final String code, final String user) throws InterruptedException {
        Domain result = new Domain();
        for(Domain dom : getDomains(code, user)) {
            if(dom.getCode().equals(code)) {
                result = dom;
                break;
            }
        }
        return result;
    }

    @Override
    public List<Domain> getDomains(String domId, String user) throws InterruptedException {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonDomains, new TypeReference<List<Domain>>() {
            });
        } catch (IOException e) {
            return new ArrayList<Domain>();
        }
    }

    @Override
    public DomainsTree getDomainsTree(String domId, String user) throws InterruptedException {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonDomTree, DomainsTree.class);
        } catch (IOException e) {
            return new DomainsTree();
        }
    }

}
