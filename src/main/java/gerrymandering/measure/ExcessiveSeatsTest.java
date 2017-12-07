package gerrymandering.measure;

import gerrymandering.common.Party;
import gerrymandering.model.MonteCarloSeats;
import gerrymandering.model.MultiDistrictRegion;
import gerrymandering.model.State;
import gerrymandering.model.User;
import gerrymandering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

/**
 * Created by yisuo on 12/6/17.
 */
public class ExcessiveSeatsTest implements Measure{
    private MonteCarloSeats simulated;

    public ExcessiveSeatsTest(MonteCarloSeats simulated){
        this.simulated = simulated;
    }

    @Override
    public MeasureResults runMeasure(MultiDistrictRegion region) {
        State state = (State)region;
        Party elected = state.getElectedParty();

        Long winnerSeats = state
                            .getDistricts()
                            .stream()
                            .filter(district -> district.getElectedParty() == elected)
                            .count();

        MeasureResults results = new ExcessiveSeatsResults(simulated, winnerSeats);

        if(winnerSeats > simulated.getMean() + simulated.getStandardDeviation())
            results.addTestResult(false);
        else
            results.addTestResult(true);
        return results;
    }
}
