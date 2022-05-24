package de.goldendeveloper.mysqldump;

import de.goldendeveloper.mysqldump.entities.MysqlDumpOption;

import java.util.ArrayList;
import java.util.List;

public class MYSQLDumpBuilder {

    private String hostname;
    private String username;
    private String password;
    private int port;
    private List<MysqlDumpOption> options = new ArrayList<>();

    public MYSQLDumpBuilder(String hostname, String username, String password, int port) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;
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

    public MYSQLDump build() {
        return new MYSQLDump(this.hostname, this.username, this.password, this.port, options);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }
}