package com.tmcelreath.example.spelparser;

import java.util.Map;

import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Component
public class SpelExpressionLanguageParser implements ExpressionLanguageParser {
	
	private static final ExpressionParser PARSER = new SpelExpressionParser();
	private static final BeanResolver BEAN_RESOLVER = new SimpleBeanResolver();

	public String getValue(String expressionString, Map<String, Object> context) {
		
		StandardEvaluationContext ctx = new StandardEvaluationContext();
		ctx.setBeanResolver(BEAN_RESOLVER);
		ctx.setVariables(context);
		
		Expression exp = PARSER.parseExpression(expressionString, ParserContext.TEMPLATE_EXPRESSION);
		Object retval = exp.getValue(ctx);
		
		return retval==null?"":retval.toString();
	}

	private static class SimpleBeanResolver implements BeanResolver {
	
		public Object resolve(EvaluationContext ctx, String beanName)
				throws AccessException {
			return ctx.lookupVariable(beanName);
		}
		
	}

}

