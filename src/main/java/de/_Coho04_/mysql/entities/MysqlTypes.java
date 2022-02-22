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
    public static final int VARCHAR = 16; // (Mit größen angabe) zbs. (20)
    public static final int TEXT = 17;
    public static final int NCHAR = 18;
    public static final int NVARCHAR = 19; // (Mit größen angabe) zbs. (20)
    public static final int BINARY = 20;
    public static final int VARBINARY = 21; // (Mit größen angabe) zbs. (20)
    public static final int BLOB = 22;
    public static final int JSON = 23;
    public static final int BOOLEAN = 24;

    public static String getPermissionName(Integer permission) {
        String type = "";
        switch (permission) {
            case BIT:
                type = "bit";
                break;
            case TINYINT:
                type = "tinyint";
                break;
            case SMALLINT:
                type = "smallint";
                break;
            case INT:
                type = "int";
                break;
            case BIGINT:
                type = "bigint";
                break;
            case DECIMAL:
                type = "decimal";
                break;
            case NUMERIC:
                type = "numeric";
                break;
            case FLOAT:
                type = "float";
                break;
            case REAL:
                type = "real";
                break;
            case DATE:
                type = "DATE";
                break;
            case TIME:
                type = "TIME";
                break;
            case DATETIME:
                type = "DATETIME";
                break;
            case TIMESTAMP:
                type = "TIMESTAMP";
                break;
            case YEAR:
                type = "YEAR";
                break;
            case CHAR:
                type = "CHAR";
                break;
            case VARCHAR:
                type = "VARCHAR";
                break;
            case TEXT:
                type = "TEXT";
                break;
            case NCHAR:
                type = "NCHAR";
                break;
            case NVARCHAR:
                type = "NVARCHAR";
                break;
            case BINARY:
                type = "BINARY";
                break;
            case VARBINARY:
                type = "VARBINARY";
                break;
            case BLOB:
                type = "BLOB";
                break;
            case JSON:
                type = "JSON";
                break;
            case BOOLEAN:
                type = "BOOLEAN";
                break;
            case DOUBLE:
                type = "DOUBLE";
                break;
        }
        return type;
    }
}
