package org.spes.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 公式解析器
 * 
 * @author HeGang
 * 
 */
public class FormulaParser {
	// 懒汉式
	private static FormulaParser instance = null;
	
	private static String [] operators = {"+","-","*","/","(",")"};
	

	
	private FormulaParser() {
		//Nothing to Do
	}

	/**
	 * 获取此类的单例
	 * 
	 * @return 单例
	 */
	public static FormulaParser getInstance() {
		if (null == instance) {
			return new FormulaParser();
		}
		return instance;
	}

	/**
	 * 判断是否为操作符号
	 * 
	 * @param operator
	 *            操作符
	 * @return
	 */
	public static boolean isOperator(String operator) {
		if (operator.equals("+") || operator.equals("-")
				|| operator.equals("*") || operator.equals("/")
				|| operator.equals("(") || operator.equals(")")) {
			return true;
		}
		return false;
	}

	/**
	 * 设置操作符的优先级
	 * 
	 * @param operator
	 * @return
	 */
	public static int prority(String operator) {
		if (operator.equals("*") || operator.equals("/")) {
			return 2;
		} else if (operator.equals("+") || operator.equals("-")
				|| operator.equals("(")) {
			return 1;
		} else
			return 0;
	}

	
	public static List<String> parseExpression(String expression) {
		List<String> list = new ArrayList<String>();
		List<String> opList = Arrays.asList(operators);
		StringTokenizer token = new StringTokenizer(expression, "+-*/()", true);
		while (token.hasMoreTokens()) {
			String toke = token.nextToken();
			if(opList.contains(toke)){
				continue;
			} else {
				list.add(toke);
			}
		}
		return list;
	}

	
	public static void inorderToRpn(String expression){
		Stack stack = new Stack();
		int index = 0;
		List<String> list = parseExpression(expression);
		while(true){
			if(isOperator(list.get(index))){
				
			}
		}
		
	}

	//test
	public static void main(String[] args) {
		String expression = "2+4*4";
		System.out.println(parseExpression(expression));
	}
}
