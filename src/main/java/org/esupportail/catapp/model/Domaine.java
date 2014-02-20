package org.esupportail.catapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Date;
import java.util.List;

@JsonRootName(value = "domaine")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Domaine {

	private String code, wording, parent;
    private List<Application> applications;
    private List<Domaine> domaines;

    public Domaine(){
    }

	public Domaine(Domaine domaine){
	    this.code = domaine.getCode();
        this.wording = domaine.getWording();
	    this.parent = domaine.getParent();
        this.domaines = domaine.getDomaines();
        this.applications = domaine.getApplications();
    }

    public static Domaine domaine(Domaine domaine) {
        return new Domaine(domaine);
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
	 * @return the wording
	 */
	public String getWording() {
		return wording;
	}
    /**
     	 * @return the applications
     	 */
	public List<Application> getApplications() {
		return applications;
	}
	/**
	 * @return the domaines
	 */
	public List<Domaine> getDomaines() {
		return domaines;
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
	 * @param wording the wording to set
	 */
	public void setWording(String wording) {
		this.wording = wording;
	}
    /**
     * @param applications the applications to set
     */
    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
    /**
     * @param domaines the domaines to set
     */
    public void setDomaines(List<Domaine> domaines) {
        this.domaines = domaines;
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
				+ ((domaines == null) ? 0 : domaines.hashCode());
		result = prime * result + ((wording == null) ? 0 : wording.hashCode());
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
		Domaine other = (Domaine) obj;
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
		if (domaines == null) {
			if (other.domaines != null)
				return false;
		} else if (!domaines.equals(other.domaines))
			return false;
		if (wording == null) {
			if (other.wording != null)
				return false;
		} else if (!wording.equals(other.wording))
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
		return "Domaine [code=" + code + ", parent=" + parent + ", libelle=" + wording
				+ ", applications=" + applications + ", domaines=" + domaines
				+ "]";
	}
    
}
