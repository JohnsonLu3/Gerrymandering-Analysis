package gerrymandering.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gerrymandering.api.ApiResponse;
import gerrymandering.model.District;
import gerrymandering.model.GeoJson;
import gerrymandering.model.State;
import gerrymandering.model.SuperDistrict;
import gerrymandering.service.GeoRenderingService;
import gerrymandering.service.WhatIfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSONFactory;
import org.wololo.jts2geojson.GeoJSONReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by yisuo on 11/12/17.
 */
@Controller
public class WhatIfController {
    @Autowired
    WhatIfService whatIfService;
    @Autowired
    GeoRenderingService geoRenderingService;

    GeoJSONReader reader = new GeoJSONReader();

    @RequestMapping(value = "/whatif", method = RequestMethod.GET)
    public String whatif(){
        return "whatif";
    }

    @RequestMapping(value = "/superdistrict/validate", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ApiResponse validateSuperdistrict(
            @RequestParam("state") String state,
            @RequestParam("year") Integer year,
            @RequestBody Feature superdistrict)
    {
        SuperDistrict sd = geoRenderingService.buildSuperdistrict(superdistrict);
        List<District> districts = whatIfService.selectDistricts(superdistrict, state, year);
        sd.setDistricts(districts);
        return new ApiResponse(true, whatIfService.runHR3057Measures(sd, year));
    }

    @RequestMapping(value = "/combineDistrictsAuto", method = RequestMethod.POST)
    @ResponseBody
    public GeoJson combineDistrictsAuto(@RequestParam String stateName){
        return null;
    }

    @RequestMapping(value = "/combineDistrictsManual", method = RequestMethod.POST)
    @ResponseBody
    public GeoJson combineDistrictsManual(@RequestParam String stateName,
                                          @RequestParam List<String> districts){
        return null;
    }

    @RequestMapping(value = "/saveCompletedWork", method = RequestMethod.POST)
    @ResponseBody
    public GeoJson saveCompletedWork(@RequestParam String stateName,
                                     @RequestParam Integer year,
                                     @RequestBody FeatureCollection features){
        return null;
    }

    @RequestMapping(value = "/loadCompletedWorks", method = RequestMethod.GET)
    @ResponseBody
    public List<GeoJson> loadCompletedWorks(@RequestParam Integer numItems){
        return null;
    }

    @RequestMapping(value = "/downloadWork", method = RequestMethod.GET)
    @ResponseBody
    public File downloadWork(@RequestParam State state){
        return null;
    }
}
