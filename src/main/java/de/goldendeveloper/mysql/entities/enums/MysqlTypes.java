package de.goldendeveloper.mysql.entities.enums;

/**
 * The MysqlTypes enum represents the different MySQL data types.
 */
@SuppressWarnings("unused")
public enum MysqlTypes {
    BIT("bit"),
    TINYINT("tinyint"),
    SMALLINT("smallint"),
    INT("int"),
    BIGINT("bigint"),
    DECIMAL("decimal"),
    NUMERIC("numeric"),
    FLOAT("float"),
    REAL("real"),
    DOUBLE("double"),
    DATE("date"),
    TIME("time"),
    DATETIME("datetime"),
    TIMESTAMP("timestamp"),
    YEAR("year"),
    CHAR("char"),
    VARCHAR("varchar"),
    TEXT("text"),
    NCHAR("nchar"),
    NVARCHAR("nvarchar"),
    BINARY("binary"),
    VARBINARY("varbinary"),
    BLOB("blob"),
    JSON("json"),
    BOOLEAN("boolean");

    private final String mysqlTypeName;

    /**
     * Constructs a new MysqlTypes object with the specified MySQL type name.
     *
     * @param mysqlTypeName the MySQL type name of the MysqlTypes object
     */
    MysqlTypes(String mysqlTypeName) {
        this.mysqlTypeName = mysqlTypeName;
    }

    /**
     * Retrieves the MySQL type name associated with this MysqlTypes object.
     *
     * @return the MySQL type name
     */
    public String getMysqlTypeName() {
        return mysqlTypeName;
    }
}
