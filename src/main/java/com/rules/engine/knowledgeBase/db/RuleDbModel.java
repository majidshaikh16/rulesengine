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
    @Column(name = "rule_id")
    private String ruleId;

    @Column(name = "`condition`", columnDefinition = "VARCHAR(2000)")
    private String condition;

    @Column(name = "`action`", columnDefinition = "VARCHAR(2000)")
    private String action;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "description", columnDefinition = "VARCHAR(2000)")
    private String description;

    @Column(name = "include_transformer", columnDefinition = "VARCHAR(2000)")
    private String includeTransformer;

    @Data
    static class IdClass implements Serializable {
        private String ruleNamespace;
        private String ruleId;
    }
}
