package com.haumea.gitanalyzer;

import com.haumea.gitanalyzer.student.StudentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class GitanalyzerApplication {

	public static void main(String[] args) {

		SpringApplication.run(GitanalyzerApplication.class, args);
	}

}
