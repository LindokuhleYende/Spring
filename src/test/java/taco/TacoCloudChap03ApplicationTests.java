package taco;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TacoCloudChap03ApplicationTests {

    @Test
    void contextLoads() {
    }

}
