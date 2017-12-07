package gerrymandering.measure;

import gerrymandering.model.*;
import gerrymandering.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Year;
import java.util.*;

/**
 * Created by yisuo on 11/7/17.
 */

/**
 1. The server retrieves voting data as well as district numbers for the last 3 elections for the selected districts.
 2. If the district numbers vary due to a different Congress in historical elections, then only the latest 2 elections will be stored. Otherwise, all 3 elections will be stored. (Need to verify from data sets)
 3. For each election year stored, the server calculates the number of representatives in this super-district by summing the number of districts, since each district elects one representative.
 4. For each election year stored, the server calculates the % votes for Democratic presidential nominee and the % votes for Republican presendential nominee by dividing the number of votes per nominee by the total votes.
 5. The server determines if the super-district complies with HR3057 by the following criteria if 3 elections are stored:
    a) The super-district elects exactly 3 representatives and Presidential nominee received at least 75% of votes in 2 of the 3 most recent elections
    b) The super-district elects exactly 4 representatives and Presidential nominee received at least 80% of votes in 2 of the 3 most recent elections
    c) The super-district elects exactly 5 representatives and Presidential nominee received at least 83% of votes in 2 of the 3 most recent elections
 6. The server determines if the super-district complies with HR3057 by the following criteria if only 2 latest elections are stored:
    a) The super-district elects exactly 3 representatives and Presidential nominee received at least 75% of votes in the 2 most recent elections
    b) The super-district elects exactly 4 representatives and Presidential nominee received at least 80% of votes in the 2 most recent elections
    c) The super-district elects exactly 5 representatives and Presidential nominee received at least 83% of votes in the 2 most recent elections
 7. If the criteria is met, the server returns a test result that contains the name of the test as "ValidateRepsVsVotes" and status of the test as "success". If the criteria is not met, the server returns a test result that contains the name of the test as ""ValidateRepsVsVotes"" and status of the test as ""failed"".
 8. The UI receives the test result and renders the bulleted line corresponding to this test as green if the test passed, or red if the test failed.
 */
public class RepsVsVotesMeasure implements Measure {

    @Autowired
    StateService stateService;

    private Map<Year, SuperDistrict> getPastElectionData(SuperDistrict s, Year electionYear){
        return null;
    }

    private Map<Year, Map<Year, SuperDistrict>> redistrictingHappenedAt(Set<Year> electionYearss,
        Set<Year> redistrictingYears){
        return null;
    }

    private Boolean isRepsVsVotesFair(Map<Year, SuperDistrict> threeMostRecentElections){
        return false;
    }

    @Override
    public MeasureResults runMeasure(MultiDistrictRegion region) {
        State state = region.getDistricts().get(0).getState();
        List<District> districts = region.getDistricts();
        List<Votes> votes = new ArrayList<Votes>();

        boolean redistricted;
        int numDistrictsCurrentYear = state.getDistricts().size();
        // district count for last year, boy is that a lot of work to get a district count
        int numDistrictsPerivousYear = stateService.findPerviousYearState(state, state.getYear().getValue()-2).get(0).getDistricts().size();
        int numDistrictsThirdYear = stateService.findPerviousYearState(state, state.getYear().getValue()-4).get(0).getDistricts().size();

        for(District district : districts){
            // for each district get voting data from the past 3 years
            district.getVotes();
        }

        return null;
    }
}
