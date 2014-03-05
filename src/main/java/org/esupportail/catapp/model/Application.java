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
    private String caption;
    private String description;
    private String url;
    private String group;
    private Boolean activation;
    private List<String> domains;

    public Application(final String code, final String title, final String caption, final String description,
                       final String url, final String group, final boolean activation, final String... domains) {
        this.code = code;
        this.title = title;
        this.caption = caption;
        this.description = description;
        this.url = url;
        this.group = group;
        this.activation = activation;
        this.domains = asList(domains);
    }



    @JsonCreator
    public static Application application(@JsonProperty("code") final String code,
                                                @JsonProperty("title") final String title,
                                                @JsonProperty("caption") final String caption,
                                                @JsonProperty("description") final String description,
                                                @JsonProperty("url") final String url,
                                                @JsonProperty("group") final String group,
                                                @JsonProperty("activation") final boolean activation,
                                                @JsonProperty("domains") final String... domains) {
        return new Application(code, title, caption, description, url, group, activation, domains);
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getCaption() {
        return caption;
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

    public Boolean getActivation() {
        return activation;
    }

    public List<String> getDomains() {
        return Collections.unmodifiableList(domains);
    }

    public Application setCode(final String code) {
        this.code = code;
        return this;
    }

    public Application setTitle(final String title) {
        this.title = title;
        return this;
    }

    public Application setCaption(final String caption) {
        this.caption = caption;
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

    public Application setActivation(Boolean activation) {
        this.activation = activation;
        return this;
    }

    public Application setDomains(String... domains) {
        this.domains = asList(domains);
        return this;
    }
}
