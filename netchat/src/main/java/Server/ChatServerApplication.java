package Server;

import javafx.application.Application;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.print.attribute.standard.Severity;
import java.sql.SQLException;


public class ChatServerApplication {
    public static final Logger LOGGER = LogManager.getLogger(ServerStart.class);

    public static void main(String[] args) {

        //   ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);

        ServerChat serverChat = context.getBean("serverChat", ServerChat.class);

        try {
            serverChat.start();
            LOGGER.info("Starting ServerChat");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
