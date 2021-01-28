package com.rules.engine.dslResolver.resolverImpl;

import com.rules.engine.dslResolver.DSLResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InsuranceResolver implements DSLResolver {
    private static final String RESOLVER_KEYWORD = "insurance";
    private static final String PREMIUM_MIN_AMT = "premium_min_amt";
    private static final String PREMIUM_MIN_YEAR = "premium_min_year";

    @Value("${insurance.min.premiumAmount}")
    private long minPremiumAmt;

    @Value("${insurance.min.premiumYear}")
    private long minPremiumYear;

    @Override
    public String getResolverKeyword() {
        return RESOLVER_KEYWORD;
    }

    @Override
    public Object resolveValue(String keyword) {
        if (keyword.equalsIgnoreCase(PREMIUM_MIN_AMT)){
            //Code to calculate the max premium amount.Totally configurable.
            return minPremiumAmt;
        }

        if (keyword.equalsIgnoreCase(PREMIUM_MIN_YEAR)){
            //Code to calculate the max premium amount years.
            return minPremiumYear;
        }

        return null;
    }
}