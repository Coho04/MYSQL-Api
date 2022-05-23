package de.goldendeveloper.mysqldump;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import de.goldendeveloper.mysqldump.entities.MysqlDumpOption;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;

public class MYSQLDump {

    private final String hostname;
    private final String username;
    private final String password;
    private final int port;
    private final List<MysqlDumpOption> options;

    private String Command;

    public MYSQLDump(String hostname, String username, String password, int port, List<MysqlDumpOption> options) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;
        this.options = options;
    }

    private void setupCommand() {
        for (MysqlDumpOption b : options) {

        }
    }

    public File getFile() {
        return run(this.hostname, this.username, this.password, this.port, this.Command);
    }

    private File run(String hostname, String username, String password, int port, String command) {
        Session session = null;
        ChannelExec channel = null;
        try {
            session = new JSch().getSession(username, hostname, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            String responseString = responseStream.toString();
            System.out.println(responseString);
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
        return null;
    }
}
