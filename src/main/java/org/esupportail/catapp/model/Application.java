package org.esupportail.catapp.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Application implements Serializable {

    private String code;
    private String title;
    private String wording;
    private String description;
    private String url;
    private String group;
    private Boolean accessibility;
    private List<String> domaines;

    public Application(final String code, final String title, final String wording, final String description,
                       final String url, final String group, final boolean accessibility, final String... domaines) {
        this.code = code;
        this.title = title;
        this.wording = wording;
        this.description = description;
        this.url = url;
        this.group = group;
        this.accessibility = accessibility;
        this.domaines = asList(domaines);
    }



    @JsonCreator
    public static Application application(@JsonProperty("code") final String code,
                                                @JsonProperty("title") final String title,
                                                @JsonProperty("wording") final String wording,
                                                @JsonProperty("description") final String description,
                                                @JsonProperty("url") final String url,
                                                @JsonProperty("group") final String group,
                                                @JsonProperty("accessibility") final boolean accessibility,
                                                @JsonProperty("domaines") final String... domaines) {
        return new Application(code, title, wording, description, url, group, accessibility, domaines);
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getWording() {
        return wording;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getGroup() {
        return group;
    }

    public Boolean getAccessibility() {
        return accessibility;
    }

    public List<String> getDomaines() {
        return Collections.unmodifiableList(domaines);
    }

    public Application setCode(final String code) {
        this.code = code;
        return this;
    }

    public Application setTitle(final String title) {
        this.title = title;
        return this;
    }

    public Application setWording(final String wording) {
        this.wording = wording;
        return this;
    }

    public Application setDescription(final String description) {
        this.description = description;
        return this;
    }

    public Application setUrl(String url) {
        this.url = url;
        return this;
    }

    public Application setGroup(String group) {
        this.group = group;
        return this;
    }

    public Application setAccessibility(Boolean accessibility) {
        this.accessibility = accessibility;
        return this;
    }

    public Application setDomaines(String... domaines) {
        this.domaines = asList(domaines);
        return this;
    }
}
