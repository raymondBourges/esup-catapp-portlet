package org.esupportail.catapp.model;

import com.fasterxml.jackson.annotation.*;

@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainsTree {
    private Domain domain;
    private DomainsTree[] subDomains;

    public DomainsTree() {
    }

    public DomainsTree(Domain domain, DomainsTree[] subDomains) {
        this.domain = domain;
        this.subDomains = subDomains;
    }

    @JsonCreator
    public static DomainsTree domTree(@JsonProperty("domain") Domain domain,
                                       @JsonProperty("subDomains") DomainsTree[] subDomains) {
        return new DomainsTree(domain, subDomains);
    }
}
