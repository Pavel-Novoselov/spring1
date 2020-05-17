package Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan(basePackages = "Server.*")
public class SpringContext {

    @Bean
    public ServerChat serverChat() {
        return new ServerChat();
    }

    @Bean
    public ClientsHandler clientsHandler(){
        return new ClientsHandler();
    }

}
