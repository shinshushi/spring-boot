package com.laptrinhjavaweb.repository.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.IBuildingRepository;

public class BuildingRepository extends SimpleJPARepository<BuildingEntity> implements IBuildingRepository {

	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, BuildingSearchBuilder builder) {
		StringBuilder sql = new StringBuilder("select * from building A");
		if(builder.getStaffId() != null && builder.getStaffId() != -1) {
			sql.append(" INNER JOIN assignmentbuilding AB ON A.id = AB.buildingid");
		}
		sql.append(" where 1=1");
		sql = this.createSQLfindAllCommon(sql, params);
		sql = this.createSQLspecial(sql, builder);
		return this.findAll(sql.toString());
	}

	private StringBuilder createSQLspecial(StringBuilder sql, BuildingSearchBuilder builder) {
		//rentarea
		if(StringUtils.isNotBlank(builder.getRentAreaFrom()) || StringUtils.isNotBlank(builder.getRentAreaTo())) {
			sql.append(" AND EXISTS (SELECT * FROM rentarea ra WHERE (A.id = ra.buildingid");
			if(StringUtils.isNotBlank(builder.getRentAreaFrom())) {
				sql.append(" AND ra.value >= " + builder.getRentAreaFrom());
			}
			if(StringUtils.isNotBlank(builder.getRentAreaTo())) {
				sql.append(" AND ra.value <= " + builder.getRentAreaTo());
			}
			sql.append("))");
		}
		
		//type
		if(builder.getTypes().length > 0) {
			sql.append(" AND (");
			//Java 7
//			for(int i = 0; i < builder.getTypes().length; i++) {
//				String type = builder.getTypes()[i];
//				if(i == 0) {
//					sql.append("A.type like '%" + type + "%'");
//				}
//				else {
//					sql.append(" OR A.type like '%" + type + "%'");
//				}
//			}
			
			//Java 8
			String sqlType = Arrays.stream(builder.getTypes()).map(item -> "(A.type like '%" + item +"%')").collect(Collectors.joining(" OR "));
			sql.append(sqlType);
			sql.append(")");
		}
		
		//rentcost
		if(StringUtils.isNotBlank(builder.getRentCostFrom())) {
			sql.append(" AND A.rentcost >= " + builder.getRentCostFrom());
		}
		if(StringUtils.isNotBlank(builder.getRentCostTo())) {
			sql.append(" AND A.rentcost <= " + builder.getRentCostFrom());
		}
		
		//staffid
		if(builder.getStaffId() != null && builder.getStaffId() != -1) {
			sql.append(" AND AB.staffid = " + builder.getStaffId());
		}
		return sql;
	}
}
