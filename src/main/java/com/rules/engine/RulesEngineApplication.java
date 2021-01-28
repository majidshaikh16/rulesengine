package com.rules.engine;

import com.rules.engine.knowledgeBase.db.LoanEligibility;
import com.rules.engine.knowledgeBase.db.LoanEligibilityRepository;
import com.rules.engine.knowledgeBase.db.RuleDbModel;
import com.rules.engine.knowledgeBase.db.RulesRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(value = "com.rules", lazyInit = true)
@EnableJpaRepositories(basePackageClasses = {RulesRepository.class, LoanEligibilityRepository.class})
@EntityScan(basePackageClasses = {RuleDbModel.class, LoanEligibility.class})
public class RulesEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(RulesEngineApplication.class, args);
	}
}
