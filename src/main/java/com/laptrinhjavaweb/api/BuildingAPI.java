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
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		ObjectMapper mapper = new ObjectMapper();
//		//convert params in request to model input
//		BuildingInput buildingInput = FormUtil.toModel(BuildingInput.class, request);
//		//convert model input to builder
//		BuildingSearchBuilder builder = new BuildingSearchBuilder.Builder()
//				.setName(buildingInput.getName())
//				.setDistrict(buildingInput.getDistrict())
//				.setFloorArea(StringUtils.isNotBlank(buildingInput.getFloorArea()) ? Integer.parseInt(buildingInput.getFloorArea()) : null)
//				.setNumberOfBasement(StringUtils.isNotBlank(buildingInput.getNumberOfBasement()) ? Integer.parseInt(buildingInput.getNumberOfBasement()) : null)
//				.setRentAreaFrom(buildingInput.getRentAreaFrom())
//				.setRentAreaTo(buildingInput.getRentAreaTo())
//				.setRentCostFrom(buildingInput.getRentCostFrom())
//				.setRentCostTo(buildingInput.getRentCostTo())
//				.setStaffId(buildingInput.getStaffId())
//				.setTypes(buildingInput.getTypes())
//				.build();
//		
//		//get data from database and save in dto
//		List<BuildingDTO> result = buildingService.findAll(builder);
//		//convert dto to json to send to client through response
//		mapper.writeValue(response.getOutputStream(), result);
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		ObjectMapper mapper = new ObjectMapper();
//		BuildingDTO buildingDTO = HttpUtil.of(request.getReader()).toModel(BuildingDTO.class);
//		buildingService.insert(buildingDTO);
//		mapper.writeValue(response.getOutputStream(), buildingDTO);
//	}
//	
//	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//	}
//	
//	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//	}

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
		
		return null;
	}
	
	@GetMapping(value = "/building/types")
	public List<BuildingTypeOutput> getBuildingTypes(){
		return null;
	}
}
