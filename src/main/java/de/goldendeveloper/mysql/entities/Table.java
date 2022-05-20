package de.goldendeveloper.mysql.entities;

import com.google.inject.Inject;
import de.goldendeveloper.mysql.MYSQL;
import jdk.jfr.DataAmount;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sonatype.guice.bean.reflect.IgnoreSetters;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table {

    private final String name;
    private final Database db;

    public Table(String name, Database database) {
        this.name = name;
        this.db = database;
    }

    public ResultSet describe() {
        try {
            Statement statement = (Statement) MYSQL.connection(this.getName()).get(0);
            return statement.executeQuery("DESCRIBE `" + this.name + "`;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public void drop() {
        try {
            List<Object> conn =  MYSQL.connection(this.getDatabase().getName());
            Statement statement = (Statement) conn.get(0);
            Connection connect = (Connection) conn.get(1);
            statement.execute("DROP TABLE `" + this.name + "`;");
            MYSQL.close(null, connect, statement);
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    public Row getRow(Column column, String item) {
        return new Row(this, column, item);
    }

    public int countRows() {
        try {
            Statement statement = (Statement) MYSQL.connection(this.getDatabase().getName()).get(0);
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM `" + this.name + "`;");
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countColumn() {
        return this.getColumns().size();
    }

    public boolean isEmpty() {
        if (this.countRows() > 0) {
            return false;
        }
        return true;
    }

    public void dropRow(int id) {
        try {
            List<Object> conn =  MYSQL.connection(this.getDatabase().getName());
            Statement statement = (Statement) conn.get(0);
            Connection connect = (Connection) conn.get(1);
            statement.execute("DELETE FROM `" + this.name + "` where id = `" + id + "`;");
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Column> getColumns() {
        List<Column> list = new ArrayList<>();
        try {
            List<Object> conn =  MYSQL.connection(this.getDatabase().getName());
            Statement statement = (Statement) conn.get(0);
            Connection connect = (Connection) conn.get(1);
            ResultSet rs = statement.executeQuery("show columns from `" + this.name + "` ;");
            while (rs.next()) {
                list.add(new Column(rs.getString(1), this));
            }
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Column getColumn(String name) {
        if (existsColumn(name)) {
            return new Column(name, this);
        }
        return null;
    }

    public Boolean existsColumn(String name) {
        try {
            List<Object> conn =  MYSQL.connection(this.getDatabase().getName());
            Statement statement = (Statement) conn.get(0);
            Connection connect = (Connection) conn.get(1);
            statement.executeQuery("select `" + name + "` from `" + this.name + "`;");
            MYSQL.close(null, connect, statement);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Boolean existsRow(Column column, String item) {
        try {
            List<Object> conn =  MYSQL.connection(this.getDatabase().getName());
            Statement statement = (Statement) conn.get(0);
            Connection connect = (Connection) conn.get(1);
            statement.executeQuery("select `" + item + "` from `" + column.getName() + "`;");
            MYSQL.close(null, connect, statement);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void addColumn(String name, Integer MysqlType) {
        try {
            List<Object> conn =  MYSQL.connection(this.getDatabase().getName());
            Statement statement = (Statement) conn.get(0);
            Connection connect = (Connection) conn.get(1);
            statement.execute("ALTER TABLE `" + this.name + "` ADD `" + name + "` " + MysqlTypes.getMysqlTypeName(MysqlType) + ";");
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setUniqueColumn(String name) {
        try {
            List<Object> conn =  MYSQL.connection(this.getDatabase().getName());
            Statement statement = (Statement) conn.get(0);
            Connection connect = (Connection) conn.get(1);
            statement.execute("ALTER IGNORE TABLE `" + this.name + "` ADD UNIQUE (" + name + ");");
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addColumn(String name, Integer MysqlType, int size) {
        try {
            List<Object> conn =  MYSQL.connection(this.getDatabase().getName());
            Statement statement = (Statement) conn.get(0);
            Connection connect = (Connection) conn.get(1);
            statement.execute("ALTER TABLE `" + this.name + "` ADD `" + name + "` " + MysqlTypes.getMysqlTypeName(MysqlType) + " (" + size + ");");
            MYSQL.close(null, connect, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean hasColumns() {
        int a = this.getColumns().size();
        return a > 0;
    }

    public Boolean hasColumn(String name) {
        try {
            List<Object> conn =  MYSQL.connection(this.getDatabase().getName());
            Statement statement = (Statement) conn.get(0);
            Connection connect = (Connection) conn.get(1);
            statement.executeQuery("SELECT " + name + " FROM `" + this.name + "`;");
            MYSQL.close(null, connect, statement);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void insert(HashMap<String, String> rowBuilder) {
        String keys = "";
        String items = "";
        for (String key : rowBuilder.keySet()) {
            if (keys.isEmpty()) {
                keys = "`" + key + "`";
            } else {
                keys = keys + ",`" + key + "`";
            }
        }
        for (String item : rowBuilder.values()) {
            if (items.isEmpty()) {
                items = "'" + item + "'";
            } else {
                items = items + ",'" + item + "'";
            }
        }
        try {
            List<Object> conn =  MYSQL.connection(this.getDatabase().getName());
            Statement statement = (Statement) conn.get(0);
            Connection connect = (Connection) conn.get(1);
            statement.execute("INSERT INTO `" + this.name + "` (" + keys + ")VALUES (" + items + ");");
            MYSQL.close(null, connect, statement);
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    public Database getDatabase() {
        return this.db;
    }
}
