package com.rules.engine.knowledgeBase;

import com.google.common.base.Enums;
import com.rules.engine.knowledgeBase.db.RuleDbModel;
import com.rules.engine.knowledgeBase.db.RulesRepository;
import com.rules.engine.knowledgeBase.models.Rule;
import com.rules.engine.restAPI.RuleNamespace;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class KnowledgeBaseService {
    @Autowired
    private RulesRepository rulesRepository;

    public List<Rule> getAllRules() {
        return rulesRepository.findAll().stream()
                .map(
                        ruleDbModel -> mapFromDbModel(ruleDbModel)
                )
                .collect(Collectors.toList());
    }

    public List<Rule> getAllRuleByNamespace(String ruleNamespace) {
        return rulesRepository.findByRuleNamespace(ruleNamespace).stream()
                .map(
                        ruleDbModel -> mapFromDbModel(ruleDbModel)
                )
                .collect(Collectors.toList());
    }

    private Rule mapFromDbModel(RuleDbModel ruleDbModel) {
        RuleNamespace namespace = Enums.getIfPresent(RuleNamespace.class, ruleDbModel.getRuleNamespace().toUpperCase())
                .or(RuleNamespace.DEFAULT);
        Rule rule = Rule.builder()
                .ruleNamespace(namespace)
                .rule(ruleDbModel.getRule())
                .condition(ruleDbModel.getCondition())
                .action(ruleDbModel.getAction())
                .description(ruleDbModel.getDescription())
                .priority(ruleDbModel.getPriority())
//                .refRuleId(ruleDbModel.getEnrich())
//                .refRule(Objects.nonNull(ruleDbModel.getRuleReference()) ? mapReference(ruleDbModel.getRuleReference()) : null)
                .build();

        RuleDbModel nextRef = ruleDbModel.getRuleReference();
        if (Objects.nonNull(nextRef)) {
            Rule nextRuleRef = mapReference(nextRef);
            rule.setRefRule(nextRuleRef);
            rule.setRefRuleId(ruleDbModel.getEnrich());
            while (Objects.nonNull(nextRef.getRuleReference())) {
                String nextRuleRefId = nextRef.getEnrich();
                nextRef = nextRef.getRuleReference();
                Rule mapReference = mapReference(nextRef);
                nextRuleRef.setRefRule(mapReference);
                mapReference.setRefRuleId(nextRuleRefId);
                nextRuleRef = mapReference;

            }
        }

        return rule;
    }

    private Rule mapReference(RuleDbModel ruleDbModel) {
        RuleNamespace namespace = Enums.getIfPresent(RuleNamespace.class, ruleDbModel.getRuleNamespace().toUpperCase())
                .or(RuleNamespace.DEFAULT);
        return Rule.builder()
                .ruleNamespace(namespace)
                .rule(ruleDbModel.getRule())
                .condition(ruleDbModel.getCondition())
                .action(ruleDbModel.getAction())
                .description(ruleDbModel.getDescription())
                .priority(ruleDbModel.getPriority())
                .build();
    }
}

