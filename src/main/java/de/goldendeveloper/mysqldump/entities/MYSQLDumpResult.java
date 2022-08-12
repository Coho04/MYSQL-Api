package de.goldendeveloper.mysqldump.entities;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MYSQLDumpResult {

    private final String command;

    private final String SSHHostname;
    private final String SSHUsername;
    private final int SSHPort;

    private String SqlString;

    public MYSQLDumpResult(String SSHHostname, String SSHUsername, String SSHPassword, int SSHPort, String command) {
        this.SSHHostname = SSHHostname;
        this.SSHUsername = SSHUsername;
        this.SSHPort = SSHPort;
        this.command = command;

        Session session = null;
        ChannelExec channel = null;
        try {
            session = new JSch().getSession(this.SSHUsername, this.SSHHostname, this.SSHPort);
            session.setPassword(SSHPassword);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(this.command);

            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();
            while (channel.isConnected()) {
                Thread.sleep(100);
            }
            SqlString = responseStream.toString();
        } catch (JSchException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    public File getFile() {
        File SQLFile;
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd_MM_yyyy-HH_mm_ss");
            SQLFile = new File("src/main/resources/Mysql-Api-MysqlDump-" + dateTimeFormatter.format(LocalDateTime.now()) + ".sql");
            BufferedWriter writer = new BufferedWriter(new FileWriter(SQLFile));
            writer.write(SqlString);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SQLFile;
    }

    public String getSSHUsername() {
        return SSHUsername;
    }

    public String getSSHHostname() {
        return SSHHostname;
    }

    public int getSSHPort() {
        return SSHPort;
    }

    public String getCommand() {
        return command;
    }
}
