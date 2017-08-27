package com.warpaint.challengeservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import com.warpaint.challengeservice.Application;
import com.warpaint.challengeservice.controller.MainController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationUnitTests {

	@Autowired
    private MainController mainController;

	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@Test
	public void testMain() {
		String[] args = {"--spring.main.webEnvironment=false",
						 "--spring.main.registerShutdownHook=false"};
		Application.main(args);
		assertThat(outputCapture.toString()).contains("Started Application");
	}

	@Test
	public void mainControllerLoads() {
		assertThat(mainController).isNotNull();
	}

	@Test(expected=IllegalArgumentException.class)
	public void testMainWithoutArgs() throws Exception {
		String[] args = null;
		Application.main(args);
	}

	@Test
	public void testApplicationConfigure() {
		Application app = new Application();
		SpringApplicationBuilder builder = new SpringApplicationBuilder();
		app.configure(builder);
	}

}
