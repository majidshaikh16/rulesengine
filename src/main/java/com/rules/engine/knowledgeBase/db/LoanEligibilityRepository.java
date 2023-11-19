package com.rules.engine.knowledgeBase.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LoanEligibilityRepository extends CrudRepository<LoanEligibility, Long> {
    Optional<LoanEligibility> findFirstByCibilLessThanEqualAndMonthlySalaryLessThanEqualOrderByEligibleLoanAmountDesc(Integer cbil, Double monthlySalary);
    Optional<LoanEligibility> findFirstByCibil(int cibil);
}
