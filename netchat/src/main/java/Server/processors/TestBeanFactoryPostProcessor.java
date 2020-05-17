package Server.processors;

import Server.ServerChat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] strings = beanFactory.getBeanDefinitionNames();
        Arrays.stream(strings).forEach(System.out::println);
        BeanDefinition chatserver = beanFactory.getBeanDefinition("ServerChat");
        System.out.println(chatserver);

    }
}
