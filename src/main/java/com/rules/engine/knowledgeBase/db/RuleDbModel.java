package com.rules.engine.knowledgeBase.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rules")
@IdClass(RuleDbModel.IdClass.class)
public class RuleDbModel {
    @Id
    @Column(name = "rule_namespace")
    private String ruleNamespace;

    @Id
    @Column(name = "rule")
    private String rule;

    @Column(name = "`condition`", columnDefinition = "VARCHAR(2000)")
    private String condition;

    @Column(name = "`action`", columnDefinition = "VARCHAR(2000)")
    private String action;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "description", columnDefinition = "VARCHAR(2000)")
    private String description;

    @Transient
    private String enrich;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "enrich_namespace_ref", referencedColumnName = "rule_namespace"),
            @JoinColumn(name = "enrich_rule_ref", referencedColumnName = "rule")
    })
    private RuleDbModel ruleReference;

    public String getEnrich() {
        return ruleReference == null ? enrich : ruleReference.getRule();
    }

    @Data
    static class IdClass implements Serializable {
        private String ruleNamespace;
        private String rule;
    }
}
