package Server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class ServerStart {
    public static final Logger LOGGER  = LogManager.getLogger(ServerStart.class);
    public static void main(String[] args) {

            new ServerChat();

     }
}
