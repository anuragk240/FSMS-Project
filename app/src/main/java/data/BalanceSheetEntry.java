package data;

/**
 * Created by Anurag Kumar on 14-Apr-16.
 */
public class BalanceSheetEntry {
    private String title;
    private Entry Asset;
    private Entry Liability_Equity;

    public BalanceSheetEntry() {
        title = null;
        Asset = null;
        Liability_Equity = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Entry getAsset() {
        return Asset;
    }

    public void setAsset(Entry asset) {
        Asset = asset;
    }

    public Entry getLiability_Equity() {
        return Liability_Equity;
    }

    public void setLiability_Equity(Entry liability_Equity) {
        Liability_Equity = liability_Equity;
    }
}
