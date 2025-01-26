package mcshop.jjonge_shop;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.nio.charset.Charset;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class JjongeShopApplication {

	@PostConstruct
	public void checkEncoding() {
		System.out.println("JVM Default Encoding: " + Charset.defaultCharset());
		System.out.println("file.encoding: " + System.getProperty("file.encoding"));
	}

	public static void main(String[] args) {
		SpringApplication.run(JjongeShopApplication.class, args);
	}

}
