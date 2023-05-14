package de.goldendeveloper.mysql.entities;

import de.goldendeveloper.mysql.MYSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table {

    private final String name;
    private final Database db;
    private final MYSQL mysql;

    public Table(String name, Database database, MYSQL mysql) {
        this.name = name;
        this.db = database;
        this.mysql = mysql;
    }

    @SuppressWarnings("unused")
    public ResultSet describe() {
        try {
            Statement statement = mysql.getConnect().createStatement();
            return statement.executeQuery("DESCRIBE `" + this.name + "`;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    @SuppressWarnings("unused")
    public void drop() {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.execute("DROP TABLE `" + this.name + "`;");
            mysql.closeRsAndSt(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Row getRow(Column column, Object item) {
        return new Row(this, column, mysql, String.valueOf(item));
    }

    @SuppressWarnings("unused")
    public List<Row> getRows() {
        List<Row> rows = new ArrayList<>();
        Column column = this.getColumn("id");
        Statement statement = null;
        try {
            statement = mysql.getConnect().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        for (int i = 1; i <= countRows(); i++) {
            HashMap<String, SearchResult> exportMap = new HashMap<>();
            try {
                StringBuilder columns = new StringBuilder();
                this.getColumns().forEach(clm -> columns.append(",").append(clm.getName()));
                rs = statement.executeQuery("Select  " + columns.toString().replaceFirst(",", "") + " From (Select *, Row_Number() Over (Order By `id`) As RowNum From `" + this.name + "`) t2 Where RowNum = " + i + ";");
                exportMap.putAll(fillMap(rs));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Row r = new Row(this, column, mysql, String.valueOf(i));
            r.setExportMap(exportMap);
            rows.add(r);
        }
        mysql.closeRsAndSt(rs, statement);
        return rows;
    }

    @SuppressWarnings("unused")
    public HashMap<String, SearchResult> getMap(Statement statement, Column column, int item) {
        HashMap<String, SearchResult> exportMap = new HashMap<>();
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM `" + this.getName() + "` WHERE " + column.getName() + " = '" + item + "';");
            ResultSetMetaData rsMetaData = rs.getMetaData();
            rs.next();
            for (int i = 1; i <= this.countColumn(); i++) {
                if (!rsMetaData.getColumnName(i).isEmpty()) {
                    if (rs.getString(rsMetaData.getColumnName(i)) != null) {
                        exportMap.put(rsMetaData.getColumnName(i), new SearchResult(rs.getString(rsMetaData.getColumnName(i))));
                    } else {
                        exportMap.put(rsMetaData.getColumnName(i), null);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exportMap;
    }

    public int countRows() {
        try {
            Statement statement = mysql.getConnect().createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM `" + this.name + "`;");
            rs.next();
            int i = rs.getInt(1);
            mysql.closeRsAndSt(rs, statement);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countColumn() {
        return this.getColumns().size();
    }

    @SuppressWarnings("unused")
    public boolean isEmpty() {
        return this.countRows() <= 0;
    }

    @SuppressWarnings("unused")
    public void dropRow(int id) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.execute("DELETE FROM `" + this.name + "` where id = " + id + ";");
            mysql.closeRsAndSt(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Column> getColumns() {
        List<Column> list = new ArrayList<>();
        try {
            Statement statement = mysql.getConnect().createStatement();
            ResultSet rs = statement.executeQuery("show columns from `" + this.name + "` ;");
            while (rs.next()) {
                list.add(new Column(rs.getString(1), this, mysql));
            }
            mysql.closeRsAndSt(rs, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Column getColumn(String name) {
        if (existsColumn(name)) {
            return new Column(name, this, mysql);
        }
        return null;
    }

    public Boolean existsColumn(String name) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.executeQuery("select `" + name + "` from `" + this.name + "`;");
            mysql.closeRsAndSt(null, statement);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @SuppressWarnings("unused")
    public Boolean existsRow(Column column, String item) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            ResultSet rs = statement.executeQuery("SELECT EXISTS(SELECT * FROM `" + this.getName() + "` WHERE `" + column.getName() + "` = " + item + ");");
            rs.next();
            Boolean result = rs.getBoolean(1);
            mysql.closeRsAndSt(rs, statement);
            return result;
        } catch (SQLException e) {
            return false;
        }
    }

    @SuppressWarnings("unused")
    public void setUniqueColumn(String name) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.execute("ALTER IGNORE TABLE `" + this.name + "` ADD UNIQUE (" + name + ");");
            mysql.closeRsAndSt(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addColumn(String name) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.execute("ALTER TABLE `" + this.name + "` ADD `" + name + "` " + MysqlTypes.TEXT.getMysqlTypeName() + " (" + 65555 + ");");
            mysql.closeRsAndSt(null, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public Boolean hasColumns() {
        return this.getColumns().size() > 0;
    }

    @SuppressWarnings("unused")
    public Boolean hasColumn(String name) {
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.executeQuery("SELECT " + name + " FROM `" + this.name + "`;");
            mysql.closeRsAndSt(null, statement);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void insert(HashMap<String, String> rowBuilder) {
        StringBuilder keys = new StringBuilder();
        StringBuilder items = new StringBuilder();
        for (String key : rowBuilder.keySet()) {
            if (keys.length() == 0) {
                keys = new StringBuilder("`" + key + "`");
            } else {
                keys.append(",`").append(key).append("`");
            }
        }
        for (String item : rowBuilder.values()) {
            if (items.length() == 0) {
                items = new StringBuilder("'" + item + "'");
            } else {
                items.append(",'").append(item).append("'");
            }
        }
        try {
            Statement statement = mysql.getConnect().createStatement();
            statement.execute("INSERT INTO `" + this.name + "` (" + keys + ")VALUES (" + items + ");");
            mysql.closeRsAndSt(null, statement);
        } catch (Exception e) {
            mysql.callException(e);
        }
    }

    public Database getDatabase() {
        return this.db;
    }

    @SuppressWarnings("unused")
    public Row getRowById(int id) {
        return new Row(this, this.getColumn("id"), mysql, String.valueOf(id));
    }

    @SuppressWarnings("unused")
    public Row getLastestRow() {
        try {
            Column column = this.getColumn("id");
            Statement statement = mysql.getConnect().createStatement();
            ResultSet rs = null;
            HashMap<String, SearchResult> exportMap = new HashMap<>();
            try {
                rs = statement.executeQuery("SELECT * FROM `" + this.getName() + "` WHERE  id = ( SELECT MAX(`id`) FROM `" + this.getName() + "`);");
                exportMap.putAll(fillMap(rs));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String id = exportMap.get("id").getAsString();
            Row r = new Row(this, column, mysql, id);
            r.setExportMap(exportMap);
            mysql.closeRsAndSt(rs, statement);
            return r;
        } catch (Exception e) {
            mysql.callException(e);
        }
        return null;
    }

    private HashMap<String, SearchResult> fillMap(ResultSet rs) throws SQLException {
        HashMap<String, SearchResult> exportMap = new HashMap<>();
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
        return exportMap;
    }

    @SuppressWarnings("unused")
    public Row getOldestRow() {
        try {
            Column column = this.getColumn("id");
            Statement statement = mysql.getConnect().createStatement();
            HashMap<String, SearchResult> exportMap = new HashMap<>();
            ResultSet rs = null;
            try {
                rs = statement.executeQuery("SELECT * FROM `" + this.getName() + "` WHERE  id = ( SELECT MIN(`id`) FROM `" + this.getName() + "`)';");
                exportMap.putAll(fillMap(rs));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String id = exportMap.get("id").getAsString();
            Row r = new Row(this, column, mysql, id);
            r.setExportMap(exportMap);
            mysql.closeRsAndSt(rs, statement);
            return r;
        } catch (Exception e) {
            mysql.callException(e);
        }
        return null;
    }
}
