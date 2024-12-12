package it.college.AnnouncementBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ## API для Системы объявлений
 *
 * @author Горелов Дмитрий
 */

@SpringBootApplication
@EnableScheduling
public class AnnouncementBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnnouncementBackendApplication.class, args);
	}

}
