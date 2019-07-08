package abbyylingvoexampledownloader.jsonentity;

import java.util.Arrays;


public class Row {
    private boolean hasCorrectFoundWordTranslations;
    private SourceFragment sourceFragment;
    private TargetFragment targetFragment;
    private SourceFoundWord[] sourceFoundWords;
    private String[] targetFoundWords;
    private String topics;
    private boolean isLingvoContent;
    private String lingvoDictionaryName;
    private String authorNickName;

    public boolean isHasCorrectFoundWordTranslations() {
        return hasCorrectFoundWordTranslations;
    }

    public void setHasCorrectFoundWordTranslations(boolean hasCorrectFoundWordTranslations) {
        this.hasCorrectFoundWordTranslations = hasCorrectFoundWordTranslations;
    }

    public SourceFragment getSourceFragment() {
        return sourceFragment;
    }

    public void setSourceFragment(SourceFragment sourceFragment) {
        this.sourceFragment = sourceFragment;
    }

    public TargetFragment getTargetFragment() {
        return targetFragment;
    }

    public void setTargetFragment(TargetFragment targetFragment) {
        this.targetFragment = targetFragment;
    }

    public SourceFoundWord[] getSourceFoundWords() {
        return sourceFoundWords;
    }

    public void setSourceFoundWords(SourceFoundWord[] sourceFoundWords) {
        this.sourceFoundWords = sourceFoundWords;
    }

    public String[] getTargetFoundWords() {
        return targetFoundWords;
    }

    public void setTargetFoundWords(String[] targetFoundWords) {
        this.targetFoundWords = targetFoundWords;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public boolean getIsLingvoContent() {
        return isLingvoContent;
    }

    public void setIsLingvoContent(boolean isLingvoContent) {
        this.isLingvoContent = isLingvoContent;
    }

    public String getLingvoDictionaryName() {
        return lingvoDictionaryName;
    }

    public void setLingvoDictionaryName(String lingvoDictionaryName) {
        this.lingvoDictionaryName = lingvoDictionaryName;
    }

    public String getAuthorNickName() {
        return authorNickName;
    }

    public void setAuthorNickName(String authorNickName) {
        this.authorNickName = authorNickName;
    }

    @Override
    public String toString() {
        return "Row{" +
                "\nhasCorrectFoundWordTranslations=" + hasCorrectFoundWordTranslations +
                ",\n sourceFragment=" + sourceFragment +
                ",\n targetFragment=" + targetFragment +
                ",\n sourceFoundWords=" + Arrays.toString(sourceFoundWords) +
                ",\n targetFoundWords=" + Arrays.toString(targetFoundWords) +
                ",\n topics='" + topics + '\'' +
                ",\n isLingvoContent=" + isLingvoContent +
                ",\n lingvoDictionaryName='" + lingvoDictionaryName + '\'' +
                ",\n authorNickName='" + authorNickName + '\'' +
                '}';
    }
}
