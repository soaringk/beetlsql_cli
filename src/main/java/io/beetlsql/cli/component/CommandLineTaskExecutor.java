package io.beetlsql.cli.component;

import io.beetlsql.cli.service.Task.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class CommandLineTaskExecutor implements CommandLineRunner {
    private TaskService taskService;

    public CommandLineTaskExecutor(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) {
        taskService.execute();
    }
}
