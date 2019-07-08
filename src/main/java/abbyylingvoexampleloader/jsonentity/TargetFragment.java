package abbyylingvoexampleloader.jsonentity;

public class TargetFragment {
    private String text;
    private int languageId;
    private boolean isOriginal;
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

    public boolean getIsOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(boolean isOriginal) {
        this.isOriginal = isOriginal;
    }

    public ContentSorceInfo getContentSorceInfo() {
        return contentSorceInfo;
    }

    public void setContentSorceInfo(ContentSorceInfo contentSorceInfo) {
        this.contentSorceInfo = contentSorceInfo;
    }

    @Override
    public String toString() {
        return "TargetFragment{" +
                "text='" + text + '\'' +
                ", languageId=" + languageId +
                ", isOriginal=" + isOriginal +
                ", contentSorceInfo=" + contentSorceInfo +
                '}';
    }
}
