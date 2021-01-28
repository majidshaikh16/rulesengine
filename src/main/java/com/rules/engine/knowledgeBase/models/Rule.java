package com.rules.engine.knowledgeBase.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rules.engine.restAPI.RuleNamespace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Rule {
    RuleNamespace ruleNamespace;
    String rule;
    String condition;
    String action;
    Integer priority;
    String description;
    String refRuleId;
    @JsonIgnore
    Rule refRule;
}
