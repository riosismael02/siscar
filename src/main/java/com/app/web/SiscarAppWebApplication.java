package com.app.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SiscarAppWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiscarAppWebApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void applicationReady() {
		openHomePage();
	}

	private void openHomePage() {
		try {
			String url = "http://localhost:8078/personas/nuevo";
			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				new ProcessBuilder("rundll32", "url.dll,FileProtocolHandler", url).start();
			} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
				new ProcessBuilder("open", url).start();
			} else {
				new ProcessBuilder("xdg-open", url).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
