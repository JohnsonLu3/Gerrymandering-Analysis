package gerrymandering.measure;

import gerrymandering.common.CommonConstants;
import gerrymandering.common.Party;
import gerrymandering.model.District;
import gerrymandering.model.MultiDistrictRegion;
import gerrymandering.model.State;
import gerrymandering.model.User;
import gerrymandering.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EfficiencyGapTest implements Measure {

    double threshold = CommonConstants.EFFICIENCYGAP_THRESHOLD;

    public EfficiencyGapTest(UserService userService){
        double userThreshold = getUserEffciencyGapThreshold(userService);
        if(userThreshold != -1.0){
            threshold = userThreshold;
        }
    }

    private Map<Party, Long> totalWastedVotes(State state) {
        Map<Party, Long> result = new HashMap<>();
        List<Party> parties = Arrays.asList(Party.values());

        parties.forEach(party -> {
            result.put(party, 0L);
        });
        state.getDistricts().forEach(district -> {
            Map<Party, Long> wastedVotes = wastedVotesPerDistrict(district);
            wastedVotes.entrySet().forEach(entry -> {
                Long wastedInParty = result.get(entry.getKey());
                result.put(entry.getKey(), wastedInParty + entry.getValue());
            });
        });
        return result;
    }

    private Map<Party, Long> wastedVotesPerDistrict(District district) {
        return district
                .getVotes()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> calculateWastedVotes(e, district)
                ));
    }

    private Long calculateWastedVotes(Map.Entry<Party, Long> entry, District district) {
        if (district.getPartyVotes(entry.getKey()) == CommonConstants.UNCONTESTED)
            return 0L;
        if (entry.getKey() != district.getElectedParty()) {
            return entry.getValue();
        } else {
            Long neededVotes = district.getTotalVotes() / 2 + 1;
            return entry.getValue() - neededVotes;
        }
    }

    @Override
    public MeasureResults runMeasure(MultiDistrictRegion region) {
        State state = (State) region;
        Map<Party, Long> wastedVotes = totalWastedVotes(state);
        Double efficiencyGap = (wastedVotes.get(Party.Democrat) - wastedVotes.get(Party.Republican))
                / new Double(state.getTotalVotes());
        Boolean passOrFail = efficiencyGap < threshold;
        EfficiencyGapResults results = new EfficiencyGapResults();
        results.setEfficiencyGap(efficiencyGap);
        results.setLegislativeThreshold(threshold);
        results.addTestResult(passOrFail);

        return results;
    }

    private double getUserEffciencyGapThreshold(UserService userService){

        double threshold = -1.0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        if(user.getEfficienctGap() != null){
            threshold = user.getEfficienctGap();
        }

        return threshold;
    }


}


