package org.acme.quarkus.sample.utils;

import java.util.stream.Stream;

public enum QueryOperator {
	EQUAL("un", " = '%s'");
	
	private final String sqlOperator;
	private final String queryOperator;
	
	public String getQueryOperator() {
		return queryOperator;
	}
	
	QueryOperator(String queryOperator, String sqlOperator) {
		
		this.sqlOperator = sqlOperator;
		this.queryOperator = queryOperator;
		
	}
	
	public static String getSqlOperator(String queryOperator) {
		return Stream.of(values())
				.filter(operator -> operator.getQueryOperator().equalsIgnoreCase(queryOperator))
				.findFirst()
				.orElse(EQUAL)
				.sqlOperator;
	}

}
