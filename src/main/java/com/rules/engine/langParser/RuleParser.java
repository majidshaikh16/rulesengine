package com.rules.engine.langParser;

import com.rules.engine.knowledgeBase.db.LoanEligibilityRepository;
import com.rules.engine.knowledgeBase.models.Rule;
import com.rules.engine.utils.RefdataCallUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class RuleParser<INPUT_DATA, OUTPUT_RESULT> {

    @Autowired
    protected DSLParser dslParser;
    @Autowired
    protected MVELParser mvelParser;
    @Autowired
    private LoanEligibilityRepository loanEligibilityRepository;

    @Autowired
    private RefdataCallUtils refdataCallUtils;

    private final String INPUT_KEYWORD = "input";
    private final String OUTPUT_KEYWORD = "output";
    private final String ENRICH_KEYWORD = "refdata";
    private final String REFDATA_UTILS = "refdataLib";

    /**
     * Parsing in given priority/steps.
     * <p>
     * Step 1. Resolve domain specific keywords first: $(rulenamespace.keyword)
     * Step 2. Resolve MVEL expression.
     *
     * @param expression
     * @param inputData
     */
    public boolean parseCondition(String expression, INPUT_DATA inputData) {
        String resolvedDslExpression = dslParser.resolveDomainSpecificKeywords(expression);
        Map<String, Object> input = new HashMap<>();
        input.put(INPUT_KEYWORD, inputData);
        boolean match = mvelParser.parseMvelExpression(resolvedDslExpression, input);
        return match;
    }

    /**
     * Parsing in given priority/steps.
     * <p>
     * Step 1. Resolve domain specific keywords: $(rulenamespace.keyword)
     * Step 2. Resolve MVEL expression.
     *
     * @param expression
     * @param inputData
     * @param outputResult
     * @return
     */
    public OUTPUT_RESULT parseAction(String expression, INPUT_DATA inputData, OUTPUT_RESULT outputResult, Rule ruleMetadata) {
        String resolvedDslExpression = dslParser.resolveDomainSpecificKeywords(expression);
        Map<String, Object> input = new HashMap<>();
        input.put(INPUT_KEYWORD, inputData);
        input.put(OUTPUT_KEYWORD, outputResult);
        input.put(ENRICH_KEYWORD, loanEligibilityRepository);
        input.put(REFDATA_UTILS, refdataCallUtils);
        mvelParser.parseMvelExpression(resolvedDslExpression, input);
        return outputResult;
    }

    private void transformWithMetadata(String expression, Map<String, Object> input) {
        mvelParser.parseMvelExpression(expression, input);
    }

}
