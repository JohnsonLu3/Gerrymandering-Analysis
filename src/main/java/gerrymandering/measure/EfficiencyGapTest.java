package gerrymandering.measure;

import gerrymandering.common.CommonConstants;
import gerrymandering.common.Party;
import gerrymandering.model.District;
import gerrymandering.model.MultiDistrictRegion;
import gerrymandering.model.State;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EfficiencyGapTest implements Measure {

	private Map<Party, Long> totalWastedVotes(State state){
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

	private Long calculateWastedVotes(Map.Entry<Party, Long> entry, District district){
	    if(district.getPartyVotes(entry.getKey()) == CommonConstants.UNCONTESTED)
	    	return 0L;
	    if(entry.getKey() != district.getElectedParty()){
	    	return entry.getValue();
		}
		else{
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
	    Boolean passOrFail = efficiencyGap < CommonConstants.EFFICIENCYGAP_THRESHOLD;
	    EfficiencyGapResults results = new EfficiencyGapResults();
	    results.setEfficiencyGap(efficiencyGap);
	    results.setLegislativeThreshold(CommonConstants.EFFICIENCYGAP_THRESHOLD);
	    results.addTestResult(passOrFail);

	    return results;
    }
}
