package com.rules.engine.utils;

import com.rules.engine.knowledgeBase.db.LoanEligibilityRepository;
import com.rules.engine.langParser.MVELParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

@Log4j2
@Component
public class RefdataCallUtils {
    @Autowired
    protected MVELParser mvelParser;

    @Autowired
    private LoanEligibilityRepository loanEligibilityRepository;

    private static final String REFDATA_KEYWORD = "refdata";

    public Object invoke(String expression, Map<String, Object> input) {
        try {
            input.put(REFDATA_KEYWORD, loanEligibilityRepository);
            mvelParser.parseMvelExpression(expression, input);
        } catch (Exception exception) {
            log.error("some error", expression);
            return false;
        }
        return true;
    }

    
    /**
     * Random random = new Random();
     *             int nextInt = random.nextInt(10);
     *             log.info("number {}", nextInt);
     *             if (nextInt % 2 == 0) {
     *                 mvelParser.parseMvelExpression(expression, input);
     *             } else {
     *                 throw new RuntimeException("Refdata service not available");
     *             }
     */
}
