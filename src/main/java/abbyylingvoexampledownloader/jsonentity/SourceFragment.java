package abbyylingvoexampledownloader.jsonentity;

public class SourceFragment {
    private String text;
    private int languageId;
    private Boolean isOriginal;
    private ContentSorceInfo contentSorceInfo;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public Boolean getIsOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(Boolean original) {
        isOriginal = original;
    }

    public ContentSorceInfo getContentSorceInfo() {
        return contentSorceInfo;
    }

    public void setContentSorceInfo(ContentSorceInfo contentSorceInfo) {
        this.contentSorceInfo = contentSorceInfo;
    }

    @Override
    public String toString() {
        return "SourceFragment{" +
                "text='" + text + '\'' +
                ", languageId=" + languageId +
                ", isOriginal=" + isOriginal +
                ", contentSorceInfo=" + contentSorceInfo +
                '}';
    }
}
