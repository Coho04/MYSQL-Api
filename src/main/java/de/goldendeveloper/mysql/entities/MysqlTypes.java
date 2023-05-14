package de.goldendeveloper.mysql.entities;

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

    MysqlTypes(String mysqlTypeName) {
        this.mysqlTypeName = mysqlTypeName;
    }

    public String getMysqlTypeName() {
        return mysqlTypeName;
    }
}
