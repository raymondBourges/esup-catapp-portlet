package org.esupportail.catapp.domain.service.mocks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.esupportail.catapp.domain.service.ICatAppServ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MockCatAppService implements ICatAppServ {

    private final String jsonApplications = "[ {\"code\": \"SIFAC-PL\",\"title\": \"Paiement en ligne\",\"caption\": \"paiement en ligne\",\"description\": \"L'application propose un formulaire de demande d'autorisation de paiement en ligne, un outil de suivi des paiements et un mode d'emploi pour créer ces formulaires web\",\"url\": \"https://paiement.univ-rennes1.fr/JSP/menu.jsp\",\"group\": \"\",\"domains\": [\"GFC\"],\"activation\": 1 }, {\"code\": \"SIFAC-CO\",\"title\": \"Sifac consultation\",\"caption\": \"Consultation sifac\",\"description\": \"Accès en consultation, pour les personnes autorisées, aux comptes SIFAC via le web.\",\"url\": \"https://sp-sifac.univ-rennes1.fr/sifacweb/stylesheets/welcome.faces\",\"group\": \"\",\"domains\": [\"GFC\"],\"activation\": 1 }, {\"code\": \"SIFAC-DOC\",\"title\": \"Documentations et procédures Sifac\",\"caption\": \"Documentations et procédure Sifac\",\"description\": \"Accès en TEST, en fonction des droits de chacun, à l'application financière et comptable de l'Université de Rennes 1 et des établissements rattachés : IEP et 'ENSCR.\",\"url\": \"https://sp-sifac.univ-rennes1.fr/index.html\",\"group\": \"\",\"domains\": [\"GFC\"],\"activation\": 0 }, {\"code\": \"SIFAC-GEST\",\"title\": \"Sifac gestion - mandant de TEST 310 : UR1 IEP ENSCR\",\"caption\": \"Sifac gestion - mandant de TEST 310 : UR1 IEP ENSCR\",\"description\": \"Accès en TEST, en fonction des droits de chacun, à l'application financière et comptable de l'Université de Rennes 1 et des établissements rattachés : IEP et 'ENSCR.\",\"url\": \"https://sp-sifac.univ-rennes1.fr/index.html\",\"group\": \"\",\"domains\": [\"GRH\"],\"activation\": 1 }]";

    private static String jsonDomains = "[{\"code\":\"ROOT\",\"caption\":\"Domain racine\",\"parent\":null,\"domains\":[{\"code\":\"ENV-TRAVAIL\",\"caption\":\"Environnement de travail\",\"parent\":\"ROOT\",\"domains\":[],\"applications\":[]},{\"code\":\"OUTILS-COM\",\"caption\":\"Outils de communication\",\"parent\":\"ROOT\",\"domains\":[],\"applications\":[]},{\"code\":\"APPS-METIER\",\"caption\":\"Applications métier\",\"parent\":\"ROOT\",\"domains\":[{\"code\":\"PILOTAGE\",\"caption\":\"Pilotage\",\"parent\":\"APPS-METIER\",\"domains\":[],\"applications\":[]},{\"code\":\"GRH\",\"caption\":\"Gestion Ressources Humaines\",\"parent\":\"APPS-METIER\",\"domains\":[],\"applications\":[    {\"code\":\"SIFAC-GEST\",\"title\":\"Sifac gestion - mandant de TEST 310 :UR1 IEP ENSCR\",\"caption\":\"Sifac gestion - mandant de TEST 310 :UR1 IEP ENSCR\",\"description\":\"Accès en TEST, en fonction des droits de chacun, à l'application financière et comptable de l'Université de Rennes 1 et des établissements rattachés :IEP et 'ENSCR.\",\"url\":\"https://sp-sifac.univ-rennes1.fr/index.html\",\"group\":\"\",\"domains\":[\"GRH\"],\"activation\":1}]},{\"code\":\"GEE\",\"caption\":\"Gestion des enseignements et étudiants\",\"parent\":\"APPS-METIER\",\"domains\":[],\"applications\":[]}],\"applications\":[]}],\"applications\":[]},{\"code\":\"GFC\",\"caption\":\"Gestion financière et comptable\",\"parent\":\"APPS-METIER\",\"domains\":[],\"applications\":[{\"code\":\"SIFAC-PL\",\"title\":\"Paiement en ligne\",\"caption\":\"paiement en ligne\",\"description\":\"L'application propose un formulaire de demande d'autorisation de paiement en ligne, un outil de suivi des paiements et un mode d'emploi pour créer ces formulaires web\",\"url\":\"https://paiement.univ-rennes1.fr/JSP/menu.jsp\",\"group\":\"\",\"domains\":[\"GFC\"],\"activation\":1},{\"code\":\"SIFAC-C0\",\"title\":\"Sifac consultation\",\"caption\":\"Consultation sifac\",\"description\":\"Accès en consultation, pour les personnes autorisées, aux comptes SIFAC via le web.\",\"url\":\"https://sp-sifac.univ-rennes1.fr/sifacweb/stylesheets/welcome.faces\",\"group\":\"\",\"domains\":[\"GFC\"],\"activation\":1},{\"code\":\"SIFAC-DOC\",\"title\":\"Documentations et procédures Sifac\",\"caption\":\"Documentations et procédure Sifac\",\"description\":\"Accès en TEST, en fonction des droits de chacun, à l'application financière et comptable de l'Université de Rennes 1 et des établissements rattachés :IEP et 'ENSCR.\",\"url\":\"https://sp-sifac.univ-rennes1.fr/index.html\",\"group\":\"\",\"domains\":[\"GFC\"],\"activation\":0}]},{\"code\":\"GP\",\"caption\":\"Gestion patrimoine\",\"parent\":\"APPS-METIER\",\"domains\":[],\"applications\":[]}]";

    private static String jsonDomTree = "{ \"domain\" : { \"code\": \"ROOT\", \"caption\": \"Domain racine\", \"parent\": null, \"applications\": [], \"domains\" : [\"ENV-TRAVAIL\",\"OUTILS-COM\",\"APPS-METIER\",\"GFC\",\"GP\"] }, \"subDomains\": [ {  \"domain\" : {  \"code\": \"ENV-TRAVAIL\",  \"caption\": \"Environnement de travail\",  \"parent\": \"ROOT\",  \"applications\": []  },  \"subDomains\": [] }, {  \"domain\" : {  \"code\": \"OUTILS-COM\",  \"caption\": \"Outils de communication\",  \"parent\": \"ROOT\",  \"applications\": []  },  \"subDomains\": [] }, {  \"domain\" : {  \"code\": \"APPS-METIER\",  \"caption\": \"Applications métier\",  \"parent\": \"ROOT\",  \"applications\": [],  \"domains\": [\"PILOTAGE\",\"GRH\",\"GEE\"]  },  \"subDomains\": [  {   \"domain\" : {   \"code\": \"PILOTAGE\",   \"caption\": \"Pilotage\",   \"parent\": \"APPS-METIER\",   \"applications\": []   },   \"subDomains\": []  },  {   \"domain\" : {   \"code\": \"GRH\",   \"caption\": \"Gestion Ressources Humaines\",   \"parent\": \"APPS-METIER\",   \"applications\": [\"SIFAC-GEST\"]   },   \"subDomains\": []  },  {   \"domain\" : {   \"code\": \"GEE\",   \"caption\": \"Gestion des enseignements et étudiants\",   \"parent\": \"APPS-METIER\",   \"applications\": []   },   \"subDomains\": []  }  ] }, {  \"domain\" : {  \"code\": \"GFC\",  \"caption\": \"Gestion financière et comptable\",  \"parent\": \"ROOT\",  \"applications\": [\"SIFAC-PL\",\"SIFAC-CO\",\"SIFAC-DOC\"]  },  \"subDomains\": [] }, {  \"domain\" : {  \"code\": \"GP\",  \"caption\": \"Gestion patrimoine\",  \"parent\": \"ROOT\",  \"applications\": [\"GESTIMMO\",\"VIZELIA\"]  },  \"subDomains\": [] } ]}";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static List<String> codesApp = new ArrayList<String>();
    private static ArrayNode allJApps = MAPPER.createArrayNode();
    private static JsonNode jDomTree = MAPPER.createArrayNode();


    public JsonNode getMockApplications() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonApplications);
        return node;
    }

    @Override
    public JsonNode getApplications() {
        return this.allJApps;
    }

    @Override
    public List<String> getCodesApplications() {
        return this.codesApp;
    }

    @Override
    public JsonNode getApplication(final String code) throws InterruptedException, IOException {
        Iterator<JsonNode> elements = getMockApplications().elements();
        while(elements.hasNext()) {
            JsonNode jDom = elements.next();
            String appCode = jDom.path("code").textValue();
            if(appCode.equals(code)) {
                return jDom;
            }
        }
        return MAPPER.createObjectNode();
    }

    @Override
    public JsonNode getDomainsTree() {
        return this.jDomTree;
    }

    @Override
    public void initData(String wsUrl, String domId, String user) throws IOException, InterruptedException {
        JsonNode nodeTree = MAPPER.readTree(jsonDomTree);
        JsonNode apps = nodeTree.path("domain").path("applications");
        if(apps.isArray()) {
            ((ObjectNode)nodeTree.path("domain")).put("applications", AppsForDom(apps));
        }
        jDomTree = completeTree(nodeTree);
    }

    private JsonNode AppsForDom(JsonNode domApps) throws IOException, InterruptedException {
        ObjectNode apps;
        String appString;
        JsonNode jApp;
        ArrayNode jApps = MAPPER.createArrayNode();
        Iterator<JsonNode> elements = domApps.elements();
        while(elements.hasNext()){
            appString = elements.next().asText();
            jApp = getApplication(appString);
            if(!codesApp.contains(jApp.get("code"))) {
                codesApp.add(jApp.get("code").asText());
                allJApps.add(jApp);
            }
            jApps.add(appString);
        }
        return jApps;
    }

    private ObjectNode completeTree(JsonNode nodeTree) throws IOException, InterruptedException {
        JsonNode jDom;
        JsonNode apps;
        JsonNode jSubDom = nodeTree.path("subDomains");
        if (!jSubDom.isMissingNode()) {
            Iterator<JsonNode> elements = jSubDom.path("domain").elements();
            while(elements.hasNext()) {
                jDom = elements.next();
                apps = jDom.path("applications");
                if(apps.isArray()) {
                    ((ObjectNode)jDom).put("applications", AppsForDom(apps));
                }
                if(!jDom.path("subDomains").isMissingNode()) {
                    completeTree(jDom);
                }
            }
        }
        return null;
    }

}
