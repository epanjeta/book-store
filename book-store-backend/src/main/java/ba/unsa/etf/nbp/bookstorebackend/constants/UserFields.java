package ba.unsa.etf.nbp.bookstorebackend.constants;

/**
 * Contains the table and column names for table user. Used for easier query creations.
 */
public class UserFields {
    public static final String ALIAS = "usr";
    public static final String ALIAS_DOT = ALIAS + ".";
    public static final String TABLE_NAME = CommonEntityConstants.NBP_SCHEMA + "NBP_USER" + " " + ALIAS;
    public static final String ID = "USER_ID";
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String USERNAME = "USERNAME";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String BIRTH_DATE = "BIRTH_DATE";
    public static final String ADDRESS_ID = "ADDRESS_ID";
    public static final String ROLE_ID = "ROLE_ID";
    public static final String NEXT_VAL = "NBP.NBP_USER_ID_SEQ.nextval";
    public static final String CURR_VAL = "NBP.NBP_USER_ID_SEQ.currval";

}
