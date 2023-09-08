package cn.tuise233;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan("cn.tuise233.mapper")
public class CardApplication {
    public static void main(String[] args) {
        SpringApplication.run(CardApplication.class, args);
    }
}
