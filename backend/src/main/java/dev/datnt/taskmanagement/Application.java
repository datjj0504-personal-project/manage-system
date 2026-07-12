package dev.datnt.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class Application {

public static void main(String[] args) {
	// PostgreSQL 18 rejects legacy Asia/Saigon timezone id sent by some JVM defaults on Windows.
	if ("Asia/Saigon".equals(TimeZone.getDefault().getID())) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
	}
	SpringApplication.run(Application.class, args);
}
}
