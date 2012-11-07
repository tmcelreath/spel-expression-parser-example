package com.tmcelreath.example.spelparser;

import java.util.Map;

public interface ExpressionLanguageParser {

	public String getValue(String expressionString, Map<String, Object> context); 
	
}
