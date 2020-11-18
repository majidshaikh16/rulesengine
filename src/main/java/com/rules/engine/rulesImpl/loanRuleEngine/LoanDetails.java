package com.rules.engine.rulesImpl.loanRuleEngine;

import com.rules.engine.knowledgeBase.db.LoanEligibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDetails {
    Long accountNumber;
    Boolean approvalStatus;
    Float interestRate;
    Float sanctionedPercentage;
    Double processingFees;
    String message = "no message".toUpperCase();
    Optional<LoanEligibility> eligibility;
}
