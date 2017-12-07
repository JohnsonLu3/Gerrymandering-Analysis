package gerrymandering.service;

import gerrymandering.common.CommonConstants;
import gerrymandering.measure.*;
import gerrymandering.model.*;
import gerrymandering.repository.DistrictRepository;
import gerrymandering.repository.MonteCarloSeatsRepository;
import gerrymandering.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yisuo on 11/14/17.
 */
@Service("gerrymanderMeasureService")
@Transactional
public class GerrymanderMeasureServiceImpl implements GerrymanderMeasureService {
    @Autowired
    private StateRepository states;
    @Autowired
    private DistrictRepository districts;
    @Autowired
    private GeoRenderingService geoJson;
    @Autowired
    private MonteCarloSeatsRepository monteCarloSeatsRepository;

    @Override
    public List<MeasureResults> runStateWideMeasures(String stateName, Integer electionYear) {
        List<State> found = states.findByStateNameAndYear(stateName, electionYear);
        if(found.size() == 0)
            return null;
        else{
            List<Measure> stateWideMeasures = new ArrayList<>();
            State state = found.get(CommonConstants.FIRST_ELEMENT);
            MonteCarloSeats simulatedSeats =
                    monteCarloSeatsRepository.findFirstByStateId(state.getId())
                        .get(CommonConstants.FIRST_ELEMENT);
            stateWideMeasures.add(new LopsidedTest());
            stateWideMeasures.add(new EfficiencyGapTest());
            stateWideMeasures.add(new ConsistentAdvantageTest());
            stateWideMeasures.add(new ExcessiveSeatsTest(simulatedSeats));

            return stateWideMeasures
                    .stream()
                    .map(m -> m.runMeasure(found.get(CommonConstants.FIRST_ELEMENT)))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public GeoJson selectDistrict(Integer stateId, Integer districtId, Year electionYear) {
        List<District> found =
            districts.findByDistrictNoAndStateIdAndYear(districtId, stateId, electionYear.getValue());
        if(found.isEmpty())
            return null;
        else
            return geoJson.buildGeoJson(found.get(CommonConstants.FIRST_ELEMENT));
    }

    @Override
    public GeoJson selectDistrict(String stateName, Integer districtId, Year electionYear){
        List<District> found =
            districts.findByDistrictNoAndStateNameAndYear(districtId, stateName, electionYear.getValue());
        if(found.isEmpty())
            return null;
        else
            return geoJson.buildGeoJson(found.get(CommonConstants.FIRST_ELEMENT));
    }

    @Override
    public GeoJson selectState(Integer stateId, Year electionYear) {
        List<State> found =
            states.findByStateIdAndYear(stateId, electionYear.getValue());
        if(found.isEmpty())
            return null;
        else
            return geoJson.buildGeoJson(found.get(CommonConstants.FIRST_ELEMENT));
    }

    @Override
    public GeoJson selectState(String stateName, Year electionYear){
        List<State> found =
            states.findByStateNameAndYear(stateName, electionYear.getValue());
        if(found.isEmpty())
            return null;
        else
            return geoJson.buildGeoJson(found.get(CommonConstants.FIRST_ELEMENT));
    }
}
