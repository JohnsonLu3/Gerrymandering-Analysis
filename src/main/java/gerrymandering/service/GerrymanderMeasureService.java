package gerrymandering.service;

import gerrymandering.measure.Measure;
import gerrymandering.measure.MeasureResults;
import gerrymandering.model.District;
import gerrymandering.model.GeoJson;
import gerrymandering.model.State;
import gerrymandering.model.SuperDistrict;

import java.time.Year;
import java.util.List;
import java.util.Map;

/**
 * Created by yisuo on 11/9/17.
 */
public interface GerrymanderMeasureService {
    public List<MeasureResults> runStateWideMeasures(State state);

    public List<MeasureResults> runHR3057Measures(SuperDistrict superDistrict);

    public GeoJson selectDistrict(Integer stateId, Integer districtId, Year electionYear);

    public GeoJson selectState(Integer stateId, Year electionYear);
}
