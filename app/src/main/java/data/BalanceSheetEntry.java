package data;

/**
 * Created by Anurag Kumar on 14-Apr-16.
 */
public class BalanceSheetEntry {
    private String title = null;
    private Entry Asset = null;
    private Entry Liability_Equity = null;

    public BalanceSheetEntry() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BalanceSheetEntry(Entry asset, Entry liabilityOrEquity) {
        Asset = asset;
        Liability_Equity = liabilityOrEquity;
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
