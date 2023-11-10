package org.mxmn.litmus.util.usercreator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.mxmn.litmus.util.usercreator")
public class LitmusUserCreatorApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(LitmusUserCreatorApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}
}
