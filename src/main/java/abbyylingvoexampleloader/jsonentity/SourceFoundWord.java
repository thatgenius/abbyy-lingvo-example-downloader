package abbyylingvoexampleloader.jsonentity;

public class SourceFoundWord {
    private int begin;
    private int end;

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "SourceFoundWord{" +
                "begin=" + begin +
                ", end=" + end +
                '}';
    }
}
