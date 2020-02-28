package com.laptrinhjavaweb.repository;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.entity.BuildingEntity;

public interface IBuildingRepository extends IJPARepository<BuildingEntity> {
	List<BuildingEntity> findAll(Map<String, Object> params, BuildingSearchBuilder builder);
}
