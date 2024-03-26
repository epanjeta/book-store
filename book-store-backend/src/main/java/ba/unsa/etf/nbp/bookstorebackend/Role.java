package ba.unsa.etf.nbp.bookstorebackend;

/** User role */
public enum Role {
    /** The administrator role for users */
    ADMINISTRATOR(2),
    /** The buyer role for users */
    BOOK_BUYER(8);

    private int databaseId;

    Role(int databaseId) {
        this.databaseId = databaseId;
    }

    public int getDatabaseId() {
        return databaseId;
    }
}
