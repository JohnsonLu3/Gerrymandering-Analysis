package gerrymandering.service;

import gerrymandering.model.GeoJson;
import gerrymandering.model.State;
import gerrymandering.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yisuo on 11/15/17.
 */
@Service("configurationService")
@Transactional
public class ConfigurationServiceImpl implements ConfigurationService {
    @Autowired
    private StateRepository states;
    @Autowired
    private GeoRenderingService geoJson;

    @Override
    public GeoJson generateUSGeoJson() {
        List<State> allUSStates = states.findByYear(2000);
        if(allUSStates.size() == 0)
            return null;
        else
            return geoJson.buildGeoJson(allUSStates);
    }

    @Override
    public State getStateByZipcode(Integer zipcode) {
        return null;
    }
}
