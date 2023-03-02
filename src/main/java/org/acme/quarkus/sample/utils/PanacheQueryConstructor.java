package org.acme.quarkus.sample.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

public class PanacheQueryConstructor {
	
	@SneakyThrows
	public static String formatQuery(String entityName, String filter) {
		return filter == null || filter.isEmpty() ? null : validate(Class.forName(entityName), filter);
	}

	private static String getOperator(String value) {
		return String.format(QueryOperator.getSqlOperator(value.split(":")[0]), value.split(":")[value.split(":").length-1]);
	}
	
	@SneakyThrows
	public static String validate(Class<?> clazz, String value) {
		
		Map<String, String> mapList = Arrays.stream(value.split(";")).map(s -> s.split("=")).collect(Collectors.toMap(e->e[0],e-> e[1]));
		List<String> error = new ArrayList<>();
		
		for(var map : mapList.entrySet()) {
			if(Arrays.stream(clazz.getDeclaredFields()).anyMatch (x-> x.getName().equals(map.getKey()))){
					Field field = clazz.getDeclaredField(map.getKey());
				if (value.contains(",")) {
					map.setValue("in: " + formatValue(map.getValue(),","," , "));
				} else if (!value.contains("un:") && field.getGenericType() == String.class) {
					map.setValue("lk:" + map.getValue());
				} else if (value.contains("bt")) {
					map.setValue("bt:" + formatValue(map.getValue(),"bt", " and "));
				}
				map.setValue(getOperator(map.getValue()));
			} else {
				error.add(map.getKey());
			}
		}
		
		if(!error.isEmpty()) {
			throw new NoSuchFieldException(String.join(",", error));
		}
		
		return mapList.entrySet().stream().map(x -> x.getKey() + x.getValue()).collect(Collectors.joining(" and "));
		
	}
	
	private static String formatValue(String value, String delimiter, String join) {
		return Arrays.stream(value.split(delimiter)).map(x -> String.format("'%s'", x)).collect(Collectors.joining(join));
	}
	
	
}
