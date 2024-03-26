package ba.unsa.etf.nbp.bookstorebackend.constants;

/**
 * Contains the table and column names for table user.
 * Used for easier query creations.
 */
public class UserFields {
    public static final String ALIAS = "usr";
    public static final String ALIAS_DOT = ALIAS + ".";
    public static final String TABLE_NAME = CommonEntityConstants.NBP_SCHEMA + "NBP_USER" + " " + ALIAS;
    public static final String ID = ALIAS_DOT + "ID";
    public static final String FIRST_NAME =  ALIAS_DOT + "FIRST_NAME";
    public static final String LAST_NAME =  ALIAS_DOT + "LAST_NAME";
    public static final String EMAIL =  ALIAS_DOT + "EMAIL";
    public static final String PASSWORD =  ALIAS_DOT + "PASSWORD";
    public static final String USERNAME =  ALIAS_DOT + "USERNAME";
    public static final String PHONE_NUMBER =  ALIAS_DOT + "PHONE_NUMBER";
    public static final String BIRTH_DATE =  ALIAS_DOT + "BIRTH_DATE";
    public static final String ADDRESS_ID =  ALIAS_DOT + "ADDRESS_ID";
    public static final String ROLE_ID = ALIAS_DOT + "ROLE_ID";


}
