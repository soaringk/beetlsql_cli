package io.beetlsql.cli;

import io.beetlsql.cli.component.CommandLineTaskExecutor;
import io.beetlsql.cli.service.VehicleLocation.VehicleLocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ConsoleApplicationTests {
    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        assertNotNull(context.getBean(VehicleLocationService.class));
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> context.getBean(CommandLineTaskExecutor.class),
                "CommandLineRunner should not be loaded during this integration test");
    }

}
