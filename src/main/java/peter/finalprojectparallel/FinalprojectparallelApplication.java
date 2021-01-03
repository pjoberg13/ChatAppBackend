package peter.finalprojectparallel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FinalprojectparallelApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalprojectparallelApplication.class, args);
	}

}
