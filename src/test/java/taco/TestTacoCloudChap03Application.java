package taco;

import org.springframework.boot.SpringApplication;

public class TestTacoCloudChap03Application {

    public static void main(String[] args) {
        SpringApplication.from(TacoCloudChap03Application::main).with(TestcontainersConfiguration.class).run(args);
    }

}
