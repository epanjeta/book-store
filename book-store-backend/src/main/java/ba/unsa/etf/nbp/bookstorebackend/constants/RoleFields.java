package ba.unsa.etf.nbp.bookstorebackend.constants;

/**
 * Contains the table and column names for table role.
 * Used for easier query creations.
 */
public class RoleFields {
    public static final String ALIAS = "rle";
    public static final String ALIAS_DOT = ALIAS + ".";
    public static final String TABLE_NAME = CommonEntityConstants.NBP_SCHEMA + "NBP_ROLE" + " " + ALIAS;
    public static final String ID = ALIAS_DOT + "ID";
    public static final String NAME = ALIAS_DOT + "NAME";
}
