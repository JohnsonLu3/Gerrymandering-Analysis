package gerrymandering.measure;

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
	    Boolean testResult = meanMedianDifference >= 0 ? true : false;
	    results.addTestResult(testResult);
	    results.setMeanMedianDifference(meanMedianDifference);
	    return results;
	}
}
