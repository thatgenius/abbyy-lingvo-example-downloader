package abbyylingvoexampledownloader.jsonentity;

import java.util.List;

public class Wrapper {
    private List<Row> rows;
    private boolean hasMoreItems;

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public boolean getHasMoreItems() {
        return hasMoreItems;
    }

    public void setHasMoreItems(boolean hasMoreItems) {
        this.hasMoreItems = hasMoreItems;
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                "rows=" + rows +
                ", hasMoreItems=" + hasMoreItems +
                '}';
    }
}
