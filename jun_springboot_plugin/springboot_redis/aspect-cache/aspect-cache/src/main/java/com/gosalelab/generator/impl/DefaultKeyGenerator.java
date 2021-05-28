package com.gosalelab.generator.impl;

import com.gosalelab.generator.KeyGenerator;
import com.gosalelab.helper.CacheHelper;
import com.gosalelab.constants.CacheConstants;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Wujun
 */
@Component("defaultKeyGenerator")
public class DefaultKeyGenerator implements KeyGenerator {

    private static Method isEmpty = null;

        static {
        try {
            isEmpty = CacheHelper.class.getDeclaredMethod("isEmpty", Object.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getKey(String keyExpression, Object[] arguments, Object returnVal) {
        // if there is not a key expression, then return the key expression direct
        if (!keyExpression.contains(CacheConstants.POUND_SIGN) &&
                !keyExpression.contains(CacheConstants.SINGLE_QUOTATION_MARK)) {
            return keyExpression;
        }

        StandardEvaluationContext context = new StandardEvaluationContext();

        // add self define method: isEmpty
        context.registerFunction("isEmpty", isEmpty);

        // add self define arguments
        context.setVariable("args", arguments);

        // if it has return value
        if (keyExpression.contains(CacheConstants.RETURN_VALUE)) {
            context.setVariable("retVal", returnVal);
        }

        ExpressionParser parser = new SpelExpressionParser();

        Expression expression = parser.parseExpression(keyExpression);

        return expression.getValue(context, String.class);
    }

}
