package gerrymandering.service;

import gerrymandering.measure.MeasureResults;
import gerrymandering.model.CompleteWork;
import gerrymandering.model.District;
import gerrymandering.model.State;
import gerrymandering.model.SuperDistrict;
import org.wololo.geojson.Feature;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Created by yisuo on 11/12/17.
 */
public interface WhatIfService {
    public List<MeasureResults> runHR3057Measures(SuperDistrict superDistricts, Integer year);

    public List<MeasureResults> runStatewideMeasures(CompleteWork completeWork);

    public State combineDistrictsAuto(Collection<District> districts);

    public SuperDistrict combineDistrictsManual(Collection<District> districts);

    public State saveCompletedWork(State completedWork);

    public List<State> loadCompletedWorks(Integer numItems);

    public File downloadWork(State completedWork);

    public List<District> selectDistricts(Feature features, String stateName, Integer year);

    public CompleteWork exportCurrentWork(List<Feature> features, String stateName, Integer year);
}
