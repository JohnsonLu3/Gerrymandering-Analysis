package gerrymandering.service;

import gerrymandering.common.CommonConstants;
import gerrymandering.measure.GeoCompactMeasure;
import gerrymandering.measure.Measure;
import gerrymandering.measure.MeasureResults;
import gerrymandering.model.*;
import gerrymandering.repository.CompleteWorkRepository;
import gerrymandering.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yisuo on 12/2/17.
 */
@Service("whatIfService")
public class WhatIfServiceImpl implements WhatIfService {
    @Autowired
    DistrictRepository districts;
    @Autowired
    CompleteWorkRepository completeWorks;
    @Autowired
    UserService userService;

    @Override
    public List<MeasureResults> runHR3057Measures(SuperDistrict superDistrict, Integer year) {
        List<Measure> measures = new ArrayList<>();
        measures.add(new GeoCompactMeasure(userService));

        return measures
                .stream()
                .map(m -> m.runMeasure(superDistrict))
                .collect(Collectors.toList());
    }

    @Override
    public List<MeasureResults> runStatewideMeasures(CompleteWork completeWork) {
        return null;
    }

    @Override
    public State combineDistrictsAuto(Collection<District> districts) {
        return null;
    }

    @Override
    public SuperDistrict combineDistrictsManual(Collection<District> districts) {
        SuperDistrict superDistrict = new SuperDistrict();
        superDistrict.getDistricts().addAll(districts);

        List<Boundary> boundaries = new ArrayList<>();
        districts.forEach(district -> boundaries.addAll(district.getBoundaries()));
        return superDistrict;
    }

    @Override
    public State saveCompletedWork(State completedWork) {
        return null;
    }

    @Override
    public List<State> loadCompletedWorks(Integer numItems) {
        return null;
    }

    @Override
    public File downloadWork(State completedWork) {
        return null;
    }

    @Override
    @Transactional
    public List<District> selectDistricts(List<Feature>features, String stateName, Integer year) {
        Map<String, Object> properties = features.get(CommonConstants.FIRST_ELEMENT).getProperties();
        List<Integer> districtNos = (List<Integer>) properties.get("Districts");
        List<District> result =
                districts.findByStateNameAndYearAndDistrictNoIn(stateName, year, districtNos);
        if(result == null)
            return null;
        if(result.size() == 0)
            return null;
        return result;
    }

    @Override
    @Transactional
    public CompleteWork exportCurrentWork(List<Feature> features, String stateName, Integer year) {
        return null;
    }
}
