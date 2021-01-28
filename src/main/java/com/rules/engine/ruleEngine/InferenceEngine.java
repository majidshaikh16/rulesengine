package com.rules.engine.ruleEngine;

import com.rules.engine.knowledgeBase.models.Rule;
import com.rules.engine.langParser.RuleParser;
import com.rules.engine.restAPI.RuleNamespace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public abstract class InferenceEngine<INPUT_DATA, OUTPUT_RESULT> {

    @Autowired
    private RuleParser<INPUT_DATA, OUTPUT_RESULT> ruleParser;

    /**
     * Run inference engine on set of rules for given data.
     *
     * @param listOfRules
     * @param inputData
     * @return
     */
    public OUTPUT_RESULT run(List<Rule> listOfRules, INPUT_DATA inputData) {
        if (null == listOfRules || listOfRules.isEmpty()) {
            return null;
        }

        //STEP 1 (MATCH) : Match the facts and data against the set of rules.
        List<Rule> conflictSet = match(listOfRules, inputData);

        //STEP 2 (RESOLVE) : Resolve the conflict and give the selected one rule.
        Rule resolvedRule = resolve(conflictSet);
        if (null == resolvedRule) {
            return null;
        }

        //STEP 3 (EXECUTE) : Run the action of the selected rule on given data and return the output.
        OUTPUT_RESULT outputResult = executeRule(resolvedRule, inputData);

        return outputResult;
    }

    /**
     * We can use here any pattern matching algo:
     * 1. Rete
     * 2. Linear
     * 3. Treat
     * 4. Leaps
     * <p>
     * Here we are using Linear matching algorithm for pattern matching.
     *
     * @param listOfRules
     * @param inputData
     * @return
     */
    protected List<Rule> match(List<Rule> listOfRules, INPUT_DATA inputData) {
        return listOfRules.stream()
                .filter(
                        rule -> ruleParser.parseCondition(getConditionAndSetAction(rule), inputData)
                )
                .collect(Collectors.toList());
    }

    /**
     * We can use here any resolving techniques:
     * 1. Lex
     * 2. Recency
     * 3. MEA
     * 4. Refactor
     * 5. Priority wise
     * <p>
     * Here we are using find first rule logic.
     *
     * @param conflictSet
     * @return
     */
    protected Rule resolve(List<Rule> conflictSet) {
        Optional<Rule> rule = conflictSet.stream()
                .sorted((o1, o2) -> o2.getPriority().compareTo(o1.getPriority()))
                .findFirst();
        if (rule.isPresent()) {
            return rule.get();
        }
        return null;
    }

    /**
     * Execute selected rule on input data.
     *
     * @param rule
     * @param inputData
     * @return
     */
    protected OUTPUT_RESULT executeRule(Rule rule, INPUT_DATA inputData) {
        OUTPUT_RESULT outputResult = initializeOutputResult();
        return ruleParser.parseAction(rule.getAction(), inputData, outputResult, rule);
    }

    protected abstract OUTPUT_RESULT initializeOutputResult();

    protected abstract RuleNamespace getRuleNamespace();

    private String getConditionAndSetAction(Rule refRule) {
        Rule rule = refRule;
        String condition = rule.getCondition();
        String action = rule.getAction();
        while (Objects.nonNull(rule.getRefRule())){
            if (StringUtils.hasText(condition)) {
                condition = condition + " && (" + rule.getRefRule().getCondition() + ")";

            } else
                condition = rule.getRefRule().getCondition();

            action += rule.getRefRule().getAction();
            refRule.setAction(action);
            rule = rule.getRefRule();
        }
        return condition;
    }
}
