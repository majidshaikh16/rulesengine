package com.rules.engine.rulesImpl.insuranceRuleEngine;

import com.rules.engine.restAPI.RuleNamespace;
import com.rules.engine.ruleEngine.InferenceEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class InsuranceInferenceEngine extends InferenceEngine<PolicyHolderDetails, Map<String,Object>> {

    @Override
    protected RuleNamespace getRuleNamespace() {
        return RuleNamespace.INSURANCE;
    }

    @Override
    protected Map<String,Object> initializeOutputResult() {
        return new HashMap<String, Object>();
    }
}
