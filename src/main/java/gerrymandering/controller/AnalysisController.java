package gerrymandering.controller;

import gerrymandering.api.ApiResponse;
import gerrymandering.measure.MeasureResults;
import gerrymandering.model.GeoJson;
import gerrymandering.service.ConfigurationService;
import gerrymandering.service.GerrymanderMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.Year;
import java.util.List;

@RestController
public class AnalysisController {
	@Autowired
    private GerrymanderMeasureService gerrymanderMeasureService;
	@Autowired
	private ConfigurationService configurationService;

	@RequestMapping(value = "/loadMap", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse loadMap(
            @RequestParam(value="year") Integer electionYear){
		GeoJson result = configurationService.generateUSGeoJson(electionYear);
		if(result == null)
			return new ApiResponse(false);
		else
			return new ApiResponse(true, result);
	}

	@RequestMapping(value = "/loadState", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse loadState(
			@RequestParam(value="stateName") String stateName,
			@RequestParam(value="year") Integer electionYear){
	    GeoJson response = gerrymanderMeasureService.selectState(stateName, Year.of(electionYear));
	    response.addAllMeasureResults(gerrymanderMeasureService.runStateWideMeasures(stateName, electionYear));
	    if(response == null)
	    	return new ApiResponse(false);
	    else
	    	return new ApiResponse(true, response);
	}

	@RequestMapping(value = "/loadDistrict", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse loadDistrict(
			@RequestParam(value="stateName") String stateName,
			@RequestParam(value="districtNo") Integer districtNo,
			@RequestParam(value="year") Integer electionYear){
		GeoJson response = gerrymanderMeasureService
            .selectDistrict(stateName, districtNo, Year.of(electionYear));
		if(response == null)
			return new ApiResponse(false);
		else
			return new ApiResponse(true, response);
	}

	@RequestMapping(value = "/allYears", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse getAllYears(){
		List<Integer> result = configurationService.getAllYears();
		if(result == null)
			return new ApiResponse(false);
		else
			return new ApiResponse(true, result);
	}

	@RequestMapping(value = "/allStates", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse getAllStates(){
		List<String> result = configurationService.getAllStateNames();
		if(result == null)
			return new ApiResponse(false);
		else
			return new ApiResponse(true, result);
	}

	@RequestMapping(value = "/runMeasures", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse runMeasures(
			@RequestParam(value="stateName") String stateName,
			@RequestParam(value="year") Integer electionYear) {
		List<MeasureResults> result = gerrymanderMeasureService.runStateWideMeasures(stateName, electionYear);
		if(result == null)
			return new ApiResponse(false);
		else
			return new ApiResponse(true, result);
	}
}
