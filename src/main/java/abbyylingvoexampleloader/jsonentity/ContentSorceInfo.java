package abbyylingvoexampleloader.jsonentity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.Date;

public class ContentSorceInfo {
    private String title;
    private String authors;
    private String[] copyrights;
    private String linkText;
    private String linkUrl;
    @JsonIgnore
    private String links;
    private boolean isSite;
    private String translators;
    @JsonIgnore
    private Date actualDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String[] getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String[] copyrights) {
        this.copyrights = copyrights;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public boolean getIsSite() {
        return isSite;
    }

    public void setIsSite(boolean isSite) {
        this.isSite = isSite;
    }

    public String getTranslators() {
        return translators;
    }

    public void setTranslators(String translators) {
        this.translators = translators;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    @Override
    public String toString() {
        return "ContentSorceInfo{" +
                "title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", copyrights=" + Arrays.toString(copyrights) +
                ", linkText='" + linkText + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", links='" + links + '\'' +
                ", isSite=" + isSite +
                ", translators='" + translators + '\'' +
                ", actualDate=" + actualDate +
                '}';
    }
}
