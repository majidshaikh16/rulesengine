package com.rules.engine.langParser;

import lombok.extern.slf4j.Slf4j;
import org.mvel2.MVEL;
import org.mvel2.ast.ASTNode;
import org.mvel2.integration.Interceptor;
import org.mvel2.integration.VariableResolverFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MVELParser {

    public boolean parseMvelExpression(String expression, Map<String, Object> inputObjects) {
        try {
            inputObjects.putAll(getLogInterceptor());
            Boolean evalToBoolean = MVEL.evalToBoolean(expression, inputObjects);
            return evalToBoolean == null ? false : evalToBoolean;
        } catch (Exception e) {
            log.error("Can not parse Mvel Expression : {} Error: {}", expression, e.getMessage());
            return false;
        }

    }
    private void testInterceptors(){
        Map interceptors = new HashMap();
        String expr = "list = [1,2,3,4,5]; @log for (item : list) { System.out.println(item); };";
        Interceptor logInterceptor = new Interceptor() {
            @Override
            public int doBefore(ASTNode node, VariableResolverFactory factory) {
                log.info("BEFORE!!");
                return 0;
            }

            @Override
            public int doAfter(Object exitStackValue, ASTNode node, VariableResolverFactory factory) {
                log.info("AFTER!!");
                return 0;
            }
        };
        interceptors.put("log", logInterceptor);
        Serializable compiled = MVEL.compileExpression(expr, null, interceptors);
        MVEL.executeExpression(compiled, new HashMap());
    }

    private Map getLogInterceptor(){
        Map interceptor = new HashMap();
        Interceptor logInterceptor = new Interceptor() {
            @Override
            public int doBefore(ASTNode node, VariableResolverFactory factory) {
                log.info("BEFORE!!");
                return 0;
            }

            @Override
            public int doAfter(Object exitStackValue, ASTNode node, VariableResolverFactory factory) {
                log.info("AFTER!!");
                return 0;
            }
        };
        interceptor.put("log", logInterceptor);
        return interceptor;
    }
}
