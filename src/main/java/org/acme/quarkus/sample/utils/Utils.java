package org.acme.quarkus.sample.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import io.quarkus.panache.common.Sort;

public final class Utils {
	
	private Utils() {
		
	}
	
	public static Map<String, Object> createMapId(Object...obj){
		
		Map<String, Object> mapId = new HashMap<>();
		
		for (int index = 0; index < obj.length; index +=2) {
			mapId.put(obj[index].toString(), obj[index + 1]);
		}
		
		return mapId;
	}
	
	public static Sort getSort(Sort.Direction orderBy, String columnSort) {
		Sort sort = null;
		
		if(Objects.nonNull(orderBy) && !(columnSort.isEmpty() || null == columnSort)) {
			if(Sort.Direction.Ascending.name().equals(orderBy.name())) {
				sort = Sort.ascending(columnSort.split(","));
				
			} else {
				sort = Sort.descending(columnSort.split(","));
			}
		}
		
		return sort;
	}

	
	/*
	
	public static String getClassParameters(Object instance) {
		Class<?> type;
	
	    log.info("Classe " +  instance.getClass().getName());
	
	    Type[] types = instance.getClass().getGenericInterfaces();
	    
	    if (types.length == 0 || !(types[0] instanceof ParameterizedType)) {
	        throw new IllegalArgumentException("A interface não é parametrizada");
	    }
	    type = (Class<?>) ((ParameterizedType) types[0]).getActualTypeArguments()[0];
	    return type.getName();
	}
	*/
	
	
}
