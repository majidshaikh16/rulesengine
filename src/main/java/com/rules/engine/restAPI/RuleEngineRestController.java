package com.rules.engine.restAPI;

import com.google.common.base.Enums;
import com.rules.engine.knowledgeBase.KnowledgeBaseService;
import com.rules.engine.knowledgeBase.db.LoanEligibility;
import com.rules.engine.knowledgeBase.db.LoanEligibilityRepository;
import com.rules.engine.knowledgeBase.models.Rule;
import com.rules.engine.ruleEngine.RuleEngine;
import com.rules.engine.rulesImpl.insuranceRuleEngine.InsuranceDetails;
import com.rules.engine.rulesImpl.insuranceRuleEngine.InsuranceInferenceEngine;
import com.rules.engine.rulesImpl.insuranceRuleEngine.PolicyHolderDetails;
import com.rules.engine.rulesImpl.loanRuleEngine.LoanDetails;
import com.rules.engine.rulesImpl.loanRuleEngine.LoanInferenceEngine;
import com.rules.engine.rulesImpl.loanRuleEngine.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
public class RuleEngineRestController {
    @Autowired
    private KnowledgeBaseService knowledgeBaseService;
    @Autowired
    private RuleEngine ruleEngine;
    @Autowired
    private LoanInferenceEngine loanInferenceEngine;
    @Autowired
    private InsuranceInferenceEngine insuranceInferenceEngine;
    @Autowired
    private LoanEligibilityRepository loanEligibilityRepository;

    @GetMapping(value = "/get-all-rules/{ruleNamespace}")
    public ResponseEntity<?> getRulesByNamespace(@PathVariable("ruleNamespace") String ruleNamespace) {
        RuleNamespace namespace = Enums.getIfPresent(RuleNamespace.class, ruleNamespace.toUpperCase()).or(RuleNamespace.DEFAULT);
        List<Rule> allRules = knowledgeBaseService.getAllRuleByNamespace(namespace.toString());
        return ResponseEntity.ok(allRules);
    }

    @GetMapping(value = "/get-all-rules")
    public ResponseEntity<?> getAllRules() {
        List<Rule> allRules = knowledgeBaseService.getAllRules();
        return ResponseEntity.ok(allRules);
    }

    @PostMapping(value = "/loan")
    public ResponseEntity<?> postUserLoanDetails(@RequestBody UserDetails userDetails) {
        LoanDetails result = (LoanDetails) ruleEngine.run(loanInferenceEngine, userDetails);
        return ResponseEntity.ok(Objects.nonNull(result) ? result : "Request is not approved/discarded!!!");
    }

    @PostMapping(value = "/insurance")
    public ResponseEntity<?> postCarLoanDetails(@RequestBody PolicyHolderDetails policyHolderDetails) {
        Map result = (Map) ruleEngine.run(insuranceInferenceEngine, policyHolderDetails);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/get-eligibility/{cibil}/{monthlysalary}")
    public ResponseEntity<?> loanEligibility(@PathVariable Integer cibil, @PathVariable Double monthlysalary){
        Optional<LoanEligibility> firstByCibil = this.loanEligibilityRepository.findFirstByCibil(cibil);
        Optional<LoanEligibility> loanEligibility = this.loanEligibilityRepository.findFirstByCibilLessThanEqualAndMonthlySalaryLessThanEqualOrderByEligibleLoanAmountDesc(cibil,monthlysalary);
        return ResponseEntity.ok(loanEligibility);
    }
}
