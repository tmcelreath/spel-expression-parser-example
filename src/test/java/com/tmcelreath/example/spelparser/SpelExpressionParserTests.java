package com.tmcelreath.example.spelparser;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class SpelExpressionParserTests {

	@Autowired
	private ExpressionLanguageParser elParser;

	Log logger = LogFactory.getLog(SpelExpressionParserTests.class);
	
	@Resource
	ExpressionLanguageParser parser;
	
	@Test
	public void testSingleExpression() {
		String expressionString = "#{@testBean.value}";
		Map<String, Object> context = new HashMap<String, Object>();
		TestObject testObject = new TestObject("TEST_VALUE");
		context.put("testBean", testObject);
		Object val = parser.getValue(expressionString, context);
		Assert.assertNotNull(val);
		Assert.assertEquals(val.toString(), testObject.getValue());
		logger.info("testSingleExpression RESULTS: " + val.toString());
	}

	@Test
	public void testSingleEmbeddedExpression() {
		String expressionString = "value=#{@testBean.value}";
		Map<String, Object> context = new HashMap<String, Object>();
		TestObject testObject = new TestObject("TEST_VALUE");
		context.put("testBean", testObject);
		Object val = parser.getValue(expressionString, context);
		Assert.assertNotNull(val);
		Assert.assertEquals(val.toString(), "value="+testObject.getValue());
		logger.info("testSingleEmbeddedExpression RESULTS: " + val.toString());
	}
	
	@Test
	public void testMultipleEmbeddedExpressions() {
		String expressionString = "RESULTS: value1=#{@testBean1.value} and value2=#{@testBean2.value}.";
		Map<String, Object> context = new HashMap<String, Object>();
		TestObject testObject1 = new TestObject("TEST_VALUE1");
		TestObject testObject2 = new TestObject("TEST_VALUE2");
		context.put("testBean1", testObject1);
		context.put("testBean2", testObject2);
		Object val = parser.getValue(expressionString, context);
		Assert.assertNotNull(val);
		Assert.assertEquals(val.toString(), "RESULTS: value1="+testObject1.getValue()+" and value2="+testObject2.getValue()+".");
		logger.info("testMultipleEmbeddedExpressions RESULTS: " + val.toString());
	}
	
	public static class TestObject {
		public TestObject(String value) {this.value = value;}
		public TestObject() {}
		private String value;
		public String getValue() { return this.value; }
		public void setValue(String value) { this.value=value; }
	}

	
}
