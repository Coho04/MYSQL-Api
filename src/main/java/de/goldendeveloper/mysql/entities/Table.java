package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;

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
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
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
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            statement.execute("DROP TABLE `" + this.name + "`;");
            MYSQL.close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Row getRow(Column column, String item) {
        return new Row(this, column, item);
    }

    public Row getRow(Column column, Boolean item) {
        return new Row(this, column, String.valueOf(item));
    }

    public Row getRow(Column column, long item) {
        return new Row(this, column, String.valueOf(item));
    }

    public Row getRow(Column column, int item) {
        return new Row(this, column, String.valueOf(item));
    }

    public List<Row> getRows() {
        List<Row> rows = new ArrayList<>();
        Column column = this.getColumn("id");
        Connection connect = MYSQL.getConnect();
        Statement statement = null;
        try {
            statement = connect.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        for (int i = 1; i <= countRows(); i++) {
            HashMap<String, SearchResult> exportMap = new HashMap<>();
            try {
                String columns = "";
                for (Column clm : this.getColumns()) {
                    columns = columns + "," + clm.getName();
                }
                rs = statement.executeQuery("Select  " + columns.replaceFirst(",", "") + " From (Select *, Row_Number() Over (Order By `id`) As RowNum From `" + this.name + "`) t2 Where RowNum = " + i + ";");
                ResultSetMetaData rsMetaData = rs.getMetaData();
                rs.next();
                for (int b = 1; b <= this.countColumn(); b++) {
                    if (!rsMetaData.getColumnName(b).isEmpty()) {
                        if (rs.getString(rsMetaData.getColumnName(b)) != null && !rs.getString(rsMetaData.getColumnName(b)).isEmpty()) {
                            exportMap.put(rsMetaData.getColumnName(b), new SearchResult(rs.getString(rsMetaData.getColumnName(b))));
                        } else {
                            exportMap.put(rsMetaData.getColumnName(b), null);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Row r = new Row(this, column, String.valueOf(i));
            r.setExportMap(exportMap);
            rows.add(r);
        }
        MYSQL.close(rs, statement);
        return rows;
    }

    // TODO: Check Connection Count
    public HashMap<String, SearchResult> getMap(Statement statement, Column column, int item) {
        HashMap<String, SearchResult> exportMap = new HashMap<>();
        if (exportMap.isEmpty()) {
            try {
                ResultSet rs = statement.executeQuery("SELECT * FROM `" + this.getName() + "` WHERE " + column.getName() + " = '" + item + "';");
                ResultSetMetaData rsMetaData = rs.getMetaData();
                rs.next();
                if (rs != null) {
                    for (int i = 1; i <= this.countColumn(); i++) {
                        if (!rsMetaData.getColumnName(i).isEmpty()) {
                            if (rs.getString(rsMetaData.getColumnName(i)) != null) {
                                exportMap.put(rsMetaData.getColumnName(i), new SearchResult(rs.getString(rsMetaData.getColumnName(i))));
                            } else {
                                exportMap.put(rsMetaData.getColumnName(i), null);
                            }
                        }
                    }
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exportMap;
    }

    public int countRows() {
        try {
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM `" + this.name + "`;");
            rs.next();
            int i = rs.getInt(1);
            MYSQL.close(rs, statement);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countColumn() {
        return this.getColumns().size();
    }

    public boolean isEmpty() {
        return this.countRows() <= 0;
    }

    public void dropRow(int id) {
        try {
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            statement.execute("DELETE FROM `" + this.name + "` where id = " + id + ";");
            MYSQL.close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Column> getColumns() {
        List<Column> list = new ArrayList<>();
        try {
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("show columns from `" + this.name + "` ;");
            while (rs.next()) {
                list.add(new Column(rs.getString(1), this));
            }
            MYSQL.close(rs, statement);
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
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            statement.executeQuery("select `" + name + "` from `" + this.name + "`;");
            MYSQL.close(null, statement);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Boolean existsRow(Column column, String item) {
        try {
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("SELECT EXISTS(SELECT * FROM `" + this.getName() + "` WHERE `" + column.getName() + "` = " + item + ");");
            rs.next();
            Boolean result = rs.getBoolean(1);
            MYSQL.close(rs, statement);
            return result;
        } catch (SQLException e) {
            return false;
        }
    }

    public void setUniqueColumn(String name) {
        try {
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            statement.execute("ALTER IGNORE TABLE `" + this.name + "` ADD UNIQUE (" + name + ");");
            MYSQL.close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addColumn(String name) {
        try {
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            statement.execute("ALTER TABLE `" + this.name + "` ADD `" + name + "` " + MysqlTypes.getMysqlTypeName(MysqlTypes.TEXT) + " (" + 65555 + ");");
            MYSQL.close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean hasColumns() {
        return this.getColumns().size() > 0;
    }

    public Boolean hasColumn(String name) {
        try {
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            statement.executeQuery("SELECT " + name + " FROM `" + this.name + "`;");
            MYSQL.close(null, statement);
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
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            statement.execute("INSERT INTO `" + this.name + "` (" + keys + ")VALUES (" + items + ");");
            MYSQL.close(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Database getDatabase() {
        return this.db;
    }

    public Row getRowById(int id) {
        return new Row(this, this.getColumn("id"), String.valueOf(id));
    }

    public Row getLastestRow() {
        try {
            Column column = this.getColumn("id");
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            ResultSet rs = null;
            HashMap<String, SearchResult> exportMap = new HashMap<>();
            try {
                rs = statement.executeQuery("SELECT * FROM `" + this.getName() + "` WHERE  id = ( SELECT MAX(`id`) FROM `" + this.getName() + "`);");
                ResultSetMetaData rsMetaData = rs.getMetaData();
                rs.next();
                for (int b = 1; b <= this.countColumn(); b++) {
                    if (!rsMetaData.getColumnName(b).isEmpty()) {
                        if (rs.getString(rsMetaData.getColumnName(b)) != null && !rs.getString(rsMetaData.getColumnName(b)).isEmpty()) {
                            exportMap.put(rsMetaData.getColumnName(b), new SearchResult(rs.getString(rsMetaData.getColumnName(b))));
                        } else {
                            exportMap.put(rsMetaData.getColumnName(b), null);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String id = exportMap.get("id").getAsString();
            Row r = new Row(this, column, id);
            r.setExportMap(exportMap);
            MYSQL.close(rs, statement);
            return r;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Row getOldestRow() {
        try {
            Column column = this.getColumn("id");
            Connection connect = MYSQL.getConnect();
            Statement statement = connect.createStatement();
            HashMap<String, SearchResult> exportMap = new HashMap<>();
            ResultSet rs = null;
            try {
                rs = statement.executeQuery("SELECT * FROM `" + this.getName() + "` WHERE  id = ( SELECT MIN(`id`) FROM `" + this.getName() + "`)';");
                ResultSetMetaData rsMetaData = rs.getMetaData();
                rs.next();
                for (int b = 1; b <= this.countColumn(); b++) {
                    if (!rsMetaData.getColumnName(b).isEmpty()) {
                        if (rs.getString(rsMetaData.getColumnName(b)) != null && !rs.getString(rsMetaData.getColumnName(b)).isEmpty()) {
                            exportMap.put(rsMetaData.getColumnName(b), new SearchResult(rs.getString(rsMetaData.getColumnName(b))));
                        } else {
                            exportMap.put(rsMetaData.getColumnName(b), null);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String id = exportMap.get("id").getAsString();
            Row r = new Row(this, column, id);
            r.setExportMap(exportMap);
            MYSQL.close(rs, statement);
            return r;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
