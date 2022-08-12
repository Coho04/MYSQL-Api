package de.goldendeveloper.mysqldump;

import de.goldendeveloper.mysqldump.entities.MYSQLDumpResult;
import de.goldendeveloper.mysqldump.entities.MysqlDumpOption;

import java.io.File;
import java.util.List;

public class MYSQLDump {

    private final String SSHHostname;
    private final String SSHUsername;
    private final String SSHPassword;
    private final int SSHPort;

    private MYSQLDumpBuilder mysqlDumpBuilder = null;

    public MYSQLDump(String SSHHostname, String SSHUsername, String SSHPassword, int SSHPort) {
        this.SSHHostname = SSHHostname;
        this.SSHUsername = SSHUsername;
        this.SSHPassword = SSHPassword;
        this.SSHPort = SSHPort;
    }

    public MYSQLDumpResult execute(MYSQLDumpBuilder mysqlDumpBuilder) {
        System.out.println("[Golden-Developer][MYSQL-API][MYSQL-Dump] SQL File Created: " + mysqlDumpBuilder.getCommand());
        this.mysqlDumpBuilder = mysqlDumpBuilder;
        return new MYSQLDumpResult(SSHHostname, SSHUsername, SSHPassword, SSHPort, mysqlDumpBuilder.getCommand());
    }

    public void ImportSQL(File SQLFile, String MysqlUsername, String MysqlPassword) {
        new MYSQLImporter(SQLFile, this, MysqlUsername, MysqlPassword);
    }

    public void ImportSQL(String SQLContent, String MysqlUsername, String MysqlPassword) {
        new MYSQLImporter(SQLContent, this, MysqlUsername, MysqlPassword);
    }

    public List<MysqlDumpOption> getOptions() {
        if (mysqlDumpBuilder != null) {
            return mysqlDumpBuilder.getOptions();
        } else {
            return null;
        }
    }

    public int getSSHPort() {
        return SSHPort;
    }

    public MYSQLDumpBuilder getMysqlDumpBuilder() {
        return mysqlDumpBuilder;
    }

    public String getSSHHostname() {
        return SSHHostname;
    }

    public String getSSHPassword() {
        return SSHPassword;
    }

    public String getSSHUsername() {
        return SSHUsername;
    }
}
