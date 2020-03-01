package com.laptrinhjavaweb.api;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjavaweb.api.output.BuildingTypeOutput;
import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.service.IBuildingService;
import com.laptrinhjavaweb.service.impl.BuildingService;


@RestController("/building")
public class BuildingAPI {
//	private static final long serialVersionUID = 1L;
	private IBuildingService buildingService = new BuildingService();
//       

	@GetMapping(value = "/building")
	public List<BuildingDTO> searchBuilding(@RequestParam Map<String, String> model, @RequestParam("types") String[] types){
		//convert model input to builder
		BuildingSearchBuilder builder = new BuildingSearchBuilder.Builder()
				.setName(model.get("name"))
				.setDistrict(model.get("district"))
				.setFloorArea(StringUtils.isNotBlank(model.get("floorArea")) ? Integer.parseInt(model.get("floorArea")) : null)
				.setNumberOfBasement(StringUtils.isNotBlank(model.get("numberOfBasement")) ? Integer.parseInt(model.get("numberOfBasement")) : null)
				.setRentAreaFrom(model.get("rentAreaFrom"))
				.setRentAreaTo(model.get("rentAreaTo"))
				.setRentCostFrom(model.get("rentCostFrom"))
				.setRentCostTo(model.get("rentCostTo"))
				.setStaffId(Long.parseLong(model.get("staffId")))
				.setTypes(types)
				.build();
		
		return buildingService.findAll(builder);
	}
	
	@GetMapping(value = "/building/types")
	public List<BuildingTypeOutput> getBuildingTypes(){
		return null;
	}
}
