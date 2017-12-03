package gerrymandering.measure;

import gerrymandering.common.Party;
import gerrymandering.model.MultiDistrictRegion;
import gerrymandering.model.State;

import java.util.List;
import java.util.stream.Collectors;

public class ConsistentAdvantageTest implements Measure {
    public List<Double> winnerPercentages(State state){
        return state
				.getDistricts()
				.stream()
				.map(district -> {
					return district.getPartyPercent(state.getElectedParty());
				})
				.collect(Collectors.toList());
	}

	public Double meanMedianDifference(State state){
        List<Double> winnerPercents = winnerPercentages(state);
        Double mean = winnerPercents.stream().mapToDouble(p -> p).average().getAsDouble();
        winnerPercents.sort((a, b) -> a.compareTo(b));
        Double median = null;
        Integer n = winnerPercents.size();
        if(winnerPercents.size() % 2 == 0){
        	median = (winnerPercents.get(n/2 - 1) + winnerPercents.get(n/2)) / 2;
		}
		else{
        	median = winnerPercents.get(n/2);
		}
		return mean - median;
	}

	@Override
	public MeasureResults runMeasure(MultiDistrictRegion region) {
	    State state = (State) region;
	    ConsistentAdvantageResults results = new ConsistentAdvantageResults();
	    Double meanMedianDifference = meanMedianDifference(state);
	    Boolean testResult = true;
	    if(state.getElectedParty() == Party.Democrat && meanMedianDifference > 0)
	        testResult = false;
	    else if(state.getElectedParty() == Party.Republican && meanMedianDifference < 0)
	    	testResult = false;
	    else
	    	testResult = true;
	    results.addTestResult(testResult);
	    results.setMeanMedianDifference(meanMedianDifference);
	    return results;
	}
}
