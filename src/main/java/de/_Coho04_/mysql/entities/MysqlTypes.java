package de._Coho04_.mysql.entities;

public class MysqlTypes {

    public static final int BIT = 0;
    public static final int TINYINT = 1;
    public static final int SMALLINT = 2;
    public static final int INT = 3;
    public static final int BIGINT = 4;
    public static final int DECIMAL = 5;
    public static final int NUMERIC = 6;
    public static final int FLOAT = 7;
    public static final int REAL = 8;
    public static final int DOUBLE = 9;
    public static final int DATE = 10;
    public static final int TIME = 11;
    public static final int DATETIME = 12;
    public static final int TIMESTAMP = 13;
    public static final int YEAR = 14;
    public static final int CHAR = 15;
    public static final int VARCHAR = 16;
    public static final int TEXT = 17;
    public static final int NCHAR = 18;
    public static final int NVARCHAR = 19;
    public static final int BINARY = 20;
    public static final int VARBINARY = 21;
    public static final int BLOB = 22;
    public static final int JSON = 23;
    public static final int BOOLEAN = 24;

    public static String getMysqlTypeName(Integer permission) {
        String type = null;
        switch (permission) {
            case BIT -> type = "bit";
            case TINYINT -> type = "tinyint";
            case SMALLINT -> type = "smallint";
            case INT -> type = "int";
            case BIGINT -> type = "bigint";
            case DECIMAL -> type = "decimal";
            case NUMERIC -> type = "numeric";
            case FLOAT -> type = "float";
            case REAL -> type = "real";
            case DATE -> type = "DATE";
            case TIME -> type = "TIME";
            case DATETIME -> type = "DATETIME";
            case TIMESTAMP -> type = "TIMESTAMP";
            case YEAR -> type = "YEAR";
            case CHAR -> type = "CHAR";
            case VARCHAR -> type = "VARCHAR";
            case TEXT -> type = "TEXT";
            case NCHAR -> type = "NCHAR";
            case NVARCHAR -> type = "NVARCHAR";
            case BINARY -> type = "BINARY";
            case VARBINARY -> type = "VARBINARY";
            case BLOB -> type = "BLOB";
            case JSON -> type = "JSON";
            case BOOLEAN -> type = "BOOLEAN";
            case DOUBLE -> type = "DOUBLE";
        }
        return type;
    }
}
