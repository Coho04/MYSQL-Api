package de.goldendeveloper.mysql.entities.enums;

/**
 * The MysqlTypes enum represents the different MySQL data types.
 */
@SuppressWarnings("unused")
public enum MysqlTypes {

    /**
     * Enumeration representing the variable "BIT".
     *
     * This enumeration is used to represent a bit variable.
     *
     * The value of this enumeration is a string "bit".
     */
    BIT("bit"),
    /**
     * The TINYINT enum represents the tinyint MySQL data type.
     *
     * <p>
     * The TINYINT data type is used to store a small integer value ranging from -128 to 127.
     * It occupies 1 byte of storage space.
     * </p>
     */
    TINYINT("tinyint"),
    /**
     * The SMALLINT enum represents the smallint MySQL data type.
     *
     * <p>
     * The SMALLINT data type is used to store small integer values ranging from -32768 to 32767.
     * It occupies 2 bytes of storage space.
     * </p>
     */
    SMALLINT("smallint"),
    /**
     * The INT enum represents the int MySQL data type.
     *
     * <p>
     * The INT data type is used to store integer values ranging from -2147483648 to 2147483647.
     * It occupies 4 bytes of storage space.
     * </p>
     *
     * <p>
     * The INT data type is represented as 'int' in MySQL.
     * </p>
     *
     * @see MysqlTypes
     */
    INT("int"),
    /**
     * Represents a variable of type BIGINT.
     *
     * The BIGINT type in SQL is typically used to represent large integer values.
     */
    BIGINT("bigint"),
    /**
     * The DECIMAL enum represents the decimal MySQL data type.
     *
     * <p>
     * The DECIMAL data type is used to store fixed-point decimal numbers.
     * It provides exact precision but can store a variable number of digits before and after the decimal point.
     * </p>
     *
     * <p>
     * The DECIMAL data type is represented as 'decimal' in MySQL and its storage size depends on the specified precision and scale.
     * </p>
     *
     * @see MysqlTypes
     */
    DECIMAL("decimal"),
    /**
     * Represents a numeric value.
     */
    NUMERIC("numeric"),
    /**
     * Represents a variable of data type float.
     */
    FLOAT("float"),
    /**
     * Enum variable representing the type "real".
     */
    REAL("real"),
    /**
     * Represents a variable of type double.
     */
    DOUBLE("double"),
    /**
     * This is a constant variable representing a date.
     *
     * The variable is stored as a string with the value "date".
     *
     * Usage example:
     *     String myDate = DATE.getValue();
     */
    DATE("date"),
    /**
     * Represents a variable called TIME.
     *
     * <p>
     * The variable stores a time value.
     * </p>
     *
     * @since 1.0
     * @see <a href="link_to_related_documentation">Related documentation</a>
     */
    TIME("time"),
    /**
     * Represents a variable indicating the datetime.
     */
    DATETIME("datetime"),
    /**
     * Represents the timestamp of an event or record.
     * <p>
     * The timestamp is a value that represents the date and time when the event or record occurred.
     * It can be used to track the sequence and timing of events or records.
     * </p>
     * @see Record
     */
    TIMESTAMP("timestamp"),
    /**
     * This enumeration represents the available values for the "year" variable.
     */
    YEAR("year"),
    /**
     * The CHAR variable represents a character type in Java.
     * It is used to store single characters and occupies 2 bytes in memory.
     * The value of a CHAR variable can be any valid Unicode character.
     *
     * Usage:
     * CHAR("char")
     *
     * Example:
     * char myChar = CHAR.getValue();
     */
    CHAR("char"),
    /**
     * Represents the VARCHAR data type in a database.
     */
    VARCHAR("varchar"),
    /**
     * Represents a variable for storing text.
     */
    TEXT("text"),
    /**
     * Represents a constant for the "nchar" variable.
     */
    NCHAR("nchar"),
    /**
     * Represents a variable of NVARCHAR data type.
     */
    NVARCHAR("nvarchar"),
    /**
     * Represents a binary value.
     *
     * Usage:
     * BINARY binary = BINARY("binary");
     *
     * This class can be used to store binary values and perform operations on them.
     *
     */
    BINARY("binary"),
    /**
     * Represents a VARBINARY type in a database.
     *
     * <p>
     * VARBINARY is a data type that stores binary data (byte arrays) of variable length.
     * It is often used to store images, documents, or any other binary data.
     * </p>
     *
     * @since 1.0.0
     */
    VARBINARY("varbinary"),
    /**
     * Represents a BLOB (Binary Large OBject).
     *
     * <p>
     * The BLOB type is used to store large binary data, such as images or documents, in a database.
     * </p>
     *
     * <p>
     * This variable represents a BLOB value named "blob".
     * </p>
     *
     * @since 1.0
     */
    BLOB("blob"),
    /**
     * The JSON enum represents the JSON MySQL data type.
     *
     * <p>
     * The JSON data type is used to store JSON (JavaScript Object Notation) data in MySQL.
     * It can store JSON objects or arrays and supports various JSON functions and operations.
     * </p>
     *
     * <p>
     * The JSON data type is represented as 'json' in MySQL and does not have a fixed storage size.
     * </p>
     *
     * @see MysqlTypes
     */
    JSON("json"),
    /**
     * The BOOLEAN enum represents the boolean MySQL data type.
     *
     * <p>
     * The BOOLEAN data type is used to store boolean values (true or false).
     * It is represented as 'bit(1)' in MySQL and occupies 1 byte of storage space.
     * </p>
     */
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
