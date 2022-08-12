package de.goldendeveloper.mysqldump;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class MYSQLImporter {

    private File SQLFile;
    private String SQLContent;
    private final MYSQLDump mysqlDump;
    private final String MysqlUsername;
    private final String MysqlPassword;

    public MYSQLImporter(File SQLFile, MYSQLDump mysqlDump, String MysqlUsername, String MysqlPassword) {
        this.SQLFile = SQLFile;
        this.mysqlDump = mysqlDump;
        this.MysqlUsername = MysqlUsername;
        this.MysqlPassword = MysqlPassword;
        Import();
    }

    public MYSQLImporter(String SQLContent, MYSQLDump mysqlDump, String MysqlUsername, String MysqlPassword) {
        this.SQLContent = SQLContent;
        this.mysqlDump = mysqlDump;
        this.MysqlUsername = MysqlUsername;
        this.MysqlPassword = MysqlPassword;
        Import();
    }

    private void Import() {
        String SQLContent;
        if (hasFile()) {
            SQLContent = getContentFromFile(this.SQLFile);
        } else {
            SQLContent = this.SQLContent;
        }

        System.out.println("ERROR: " + 1);
        Session session = null;
        ChannelExec channel = null;
        ByteArrayOutputStream errorResponse = new ByteArrayOutputStream();
        String filename = "MYSQLDumpImporter.sql";
        try {
            System.out.println("ERROR: " + 2);

            System.out.println(SQLContent);

            session = new JSch().getSession(mysqlDump.getSSHUsername(), mysqlDump.getSSHHostname(), mysqlDump.getSSHPort());
            session.setPassword(mysqlDump.getSSHPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            System.out.println("ERROR: " + 3);
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand("echo \"" + SQLContent  + "\" << " + filename + "; mysqldump -u " + this.MysqlUsername + " --password=\"" + this.MysqlPassword + "\"  > " + filename + "; rm " + filename);
            channel.connect();

            System.out.println("ERROR: " + 4);
            channel.setErrStream(errorResponse);
            channel.connect();
            System.out.println("ERROR: " + 5);
            while (channel.isConnected()) {
                Thread.sleep(100);
            }

        } catch (JSchException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (errorResponse.size() >= 1) {
                System.out.println("\u001B[31m[Golden-Developer][MYSQL-API] ERROR: " + errorResponse  + "\u001B[0m");
            } else {
                System.out.println("[Golden-Developer] [MYSQL-API][MYSQL-Import]: The import was performed successfully!");
            }
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    public Boolean hasFile() {
        return this.SQLFile != null;
    }

    private String getContentFromFile(File SQLFile) {
        StringBuilder resultStringBuilder = new StringBuilder();
        try {
            InputStream targetStream = Files.newInputStream(SQLFile.toPath());
            BufferedReader br = new BufferedReader(new InputStreamReader(targetStream));
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultStringBuilder.toString();
    }

    public MYSQLDump getMysqlDump() {
        return mysqlDump;
    }

    public File getFile() {
        return SQLFile;
    }

    public String getSQLContent() {
        return SQLContent;
    }
}
