package gerrymandering.service;

import gerrymandering.measure.MeasureResults;
import gerrymandering.model.GeoJson;
import gerrymandering.model.State;
import gerrymandering.model.SuperDistrict;
import java.time.Year;
import java.util.List;

/**
 * Created by yisuo on 11/9/17.
 */
public interface GerrymanderMeasureService {
    public List<MeasureResults> runStateWideMeasures(String stateName, Integer electionYear);

    public GeoJson selectDistrict(Integer stateId, Integer districtId, Year electionYear);

    public GeoJson selectDistrict(String stateName, Integer districtId, Year electionYear);

    public GeoJson selectState(Integer stateId, Year electionYear);

    public GeoJson selectState(String stateName, Year electionYear);
}
