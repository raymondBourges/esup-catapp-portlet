package org.esupportail.catapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName(value = "domain")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Domain {

	private String code, caption, parent;
    private List<Application> applications;
    private List<Domain> domains;

    public Domain(){
    }

	public Domain(Domain domain){
	    this.code = domain.getCode();
        this.caption = domain.getCaption();
	    this.parent = domain.getParent();
        this.domains = domain.getDomains();
        this.applications = domain.getApplications();
    }

    public static Domain domain(Domain domain) {
        return new Domain(domain);
    }
    /**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}
	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}
    /**
     	 * @return the applications
     	 */
	public List<Application> getApplications() {
		return applications;
	}
	/**
	 * @return the domains
	 */
	public List<Domain> getDomains() {
		return domains;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}
	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
    /**
     * @param applications the applications to set
     */
    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
    /**
     * @param domains the domains to set
     */
    public void setDomains(List<Domain> domains) {
        this.domains = domains;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((applications == null) ? 0 : applications.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((domains == null) ? 0 : domains.hashCode());
		result = prime * result + ((caption == null) ? 0 : caption.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Domain other = (Domain) obj;
		if (applications == null) {
			if (other.applications != null)
				return false;
		} else if (!applications.equals(other.applications))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (domains == null) {
			if (other.domains != null)
				return false;
		} else if (!domains.equals(other.domains))
			return false;
		if (caption == null) {
			if (other.caption != null)
				return false;
		} else if (!caption.equals(other.caption))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Domain [code=" + code + ", parent=" + parent + ", libelle=" + caption
				+ ", applications=" + applications + ", domains=" + domains
				+ "]";
	}
    
}
