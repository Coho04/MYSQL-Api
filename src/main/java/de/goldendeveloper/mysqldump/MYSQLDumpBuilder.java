package de.goldendeveloper.mysqldump;


import de.goldendeveloper.mysqldump.entities.MysqlDumpOption;

import java.util.ArrayList;
import java.util.List;

public class MYSQLDumpBuilder {

    private String username;
    private String password;
    private List<MysqlDumpOption> options = new ArrayList<>();

    public MYSQLDumpBuilder(String MysqlUsername, String MysqlPassword) {
        this.username = MysqlUsername;
        this.password = MysqlPassword;
    }

    public MYSQLDumpBuilder setOptions(List<MysqlDumpOption> options) {
        this.options = options;
        return this;
    }

    public MYSQLDumpBuilder setOption(MysqlDumpOption option) {
        this.options.add(option);
        return this;
    }

    public List<MysqlDumpOption> getOptions() {
        return this.options;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getCommand() {
        StringBuilder command = new StringBuilder("mysqldump -u " + this.username + " --password=\"" + this.password + "\" --all-databases");
        for (MysqlDumpOption mysqlDumpOption : options) {
            command.append(" ").append(mysqlDumpOption.getCmd());
        }
        return command.toString();
    }
}