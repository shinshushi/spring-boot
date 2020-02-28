package com.laptrinhjavaweb.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.laptrinhjavaweb.annotation.Column;
import com.laptrinhjavaweb.annotation.Entity;

public class ResultSetMapper<T> {
	public List<T> map(ResultSet resultSet, Class<T> zClass){
		List<T> result = new ArrayList<>();
		try {
			if(zClass.isAnnotationPresent(Entity.class)) {
				ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
				while(resultSet.next()) {
					T object  = zClass.newInstance();
					for(int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
						String columnName = resultSetMetaData.getColumnName(i + 1);
						Object columnValue = resultSet.getObject(i + 1);
						for(Field field: zClass.getDeclaredFields()) {
							if(field.isAnnotationPresent(Column.class)) {
								Column column = field.getAnnotation(Column.class);
								if(column.name().equals(columnName) && columnValue != null) {
									BeanUtils.setProperty(object, field.getName(), columnValue);
									break;
								}
							}
						}
						
						//Check fields in parent class
						Class<?> parentClass = zClass.getSuperclass();
						while(parentClass != null) {
							for(Field field: parentClass.getDeclaredFields()) {
								if(field.isAnnotationPresent(Column.class)) {
									Column column = field.getAnnotation(Column.class);
									if(column.name().equals(columnName) && columnValue != null) {
										BeanUtils.setProperty(object, field.getName(), columnValue);
										break;
									}
								}
							}
							parentClass = parentClass.getSuperclass();
						}
					}
					result.add(object);
				}
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
}
