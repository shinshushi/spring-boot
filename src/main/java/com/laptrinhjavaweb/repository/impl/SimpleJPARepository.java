package com.laptrinhjavaweb.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.laptrinhjavaweb.annotation.Column;
import com.laptrinhjavaweb.annotation.Table;
import com.laptrinhjavaweb.mapper.ResultSetMapper;
import com.laptrinhjavaweb.repository.EntityManagerFactory;
import com.laptrinhjavaweb.repository.IJPARepository;

public class SimpleJPARepository<T> implements IJPARepository<T> {

	private Class<T> zClass;
	
	@SuppressWarnings("unchecked")
	public SimpleJPARepository() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		this.zClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}
	
	@Override
	public List<T> findAll(Map<String, Object> params, Object...where) {
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		Connection connection = EntityManagerFactory.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String tableName = "";
		if(connection != null) {
			try {
				if(zClass.isAnnotationPresent(Table.class)) {
					Table table = zClass.getAnnotation(Table.class);
					tableName = table.name();
				}
				
				StringBuilder sql = new StringBuilder("select * from " + tableName + " A where 1=1");
				sql = createSQLfindAllCommon(sql, params);
				if(where != null && where.length == 1) {
					 sql.append(where[0]);
				}
				statement = connection.prepareStatement(sql.toString() );
				resultSet = statement.executeQuery();
				return resultSetMapper.map(resultSet, this.zClass);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return null;
			} finally {
				try {
					if(connection != null) {
						connection.close();
					}
					if(statement != null) {
						statement.close();
					}
					if(resultSet != null) {
						resultSet.close();
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					return new ArrayList<>();
				}
			}
		}
		return new ArrayList<>();
	}

	protected StringBuilder createSQLfindAllCommon(StringBuilder sql, Map<String, Object> params) {
		if(params != null && params.size() > 0) {
			for(Map.Entry<String, Object> item : params.entrySet()) {
				if(item.getValue() != null && item.getValue() instanceof String) {
					if(StringUtils.isNotBlank((String) item.getValue())) {
						sql.append(" AND A." + item.getKey() + " like '%" + item.getValue() + "%'");
					}
				}
				else if(item.getValue() != null) {
					sql.append(" AND A." + item.getKey() + " = " + item.getValue());
				}
			}
		}
		return sql;
	}

	@Override
	public void insert(T entity) {
		String sql = createSQLInsert();
		Connection connection = null;
		PreparedStatement statement = null;	
		try {
			connection = EntityManagerFactory.getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			//convert object -> object class
			//Class<?> aClass = object.getClass();
			int index = 1;
			for(Field aField : zClass.getDeclaredFields()) {
				aField.setAccessible(true);
				statement.setObject(index, aField.get(entity));
				index++;
			}
			
			//check parent class
			Class<?> parentClass = zClass.getSuperclass();
			while(parentClass != null) {
				for(Field aField : parentClass.getDeclaredFields()) {
					aField.setAccessible(true);
					statement.setObject(index, aField.get(entity));
					index++;
				}
				parentClass = parentClass.getSuperclass();
			}
			
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException | IllegalArgumentException | IllegalAccessException e) {
			if(connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}
			}
			System.out.println(e.getMessage());
		} finally {
			try {
				if(connection != null) {
					connection.close();
				}
				if(statement != null) {
					statement.close();
				}
			} catch(SQLException e2) {
				System.out.println(e2.getMessage());
			}
		}
		
	}

	private String createSQLInsert() {
		String tableName = "";
		if(zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName = table.name();
		}
		StringBuilder fields = new StringBuilder("");
		StringBuilder params = new StringBuilder("");
		for(Field field : zClass.getDeclaredFields()) {
			if(!field.isAnnotationPresent(Column.class)) {
				continue;
			}
			if(fields.length() > 0) {
				fields.append(",");
				params.append(",");
			}
			Column column = field.getAnnotation(Column.class);
			fields.append(column.name());
			params.append("?");
		}
		
		//check parent class
		Class<?> parentClass = zClass.getSuperclass();
		while(parentClass != null) {
			for(Field field : parentClass.getDeclaredFields()) {
				if(!field.isAnnotationPresent(Column.class)) {
					continue;
				}
				if(fields.length() > 0) {
					fields.append(",");
					params.append(",");
				}
				Column column = field.getAnnotation(Column.class);
				fields.append(column.name());
				params.append("?");
			}
			parentClass = parentClass.getSuperclass();
		}
		
		String sql = "INSERT INTO " + tableName + "(" + fields.toString() + ") VALUES(" + params.toString() +")";
		return sql;
	}

	@Override
	public List<T> findAll(String sql, Object... where) {
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		Connection connection = EntityManagerFactory.getConnection();
		//PreparedStatement statement = null;
		Statement statement = null;
		ResultSet resultSet = null;

		if(connection != null) {
			try {
				StringBuilder sqlBuilder = new StringBuilder(sql);
				if(where != null && where.length == 1) {
					 sqlBuilder.append(where[0]);
				}
				//statement = connection.prepareStatement(sqlBuilder.toString() );
				statement = connection.createStatement();
				//resultSet = statement.executeQuery();
				resultSet = statement.executeQuery(sqlBuilder.toString());
				return resultSetMapper.map(resultSet, this.zClass);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return null;
			} finally {
				try {
					if(connection != null) {
						connection.close();
					}
					if(statement != null) {
						statement.close();
					}
					if(resultSet != null) {
						resultSet.close();
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					return new ArrayList<>();
				}
			}
		}
		return new ArrayList<>();
	}
}
