package gerrymandering.measure;

import gerrymandering.common.CommonConstants;
import gerrymandering.common.Party;
import gerrymandering.model.MultiDistrictRegion;
import gerrymandering.model.State;
import org.apache.commons.math3.stat.inference.TTest;

import java.util.*;

public class LopsidedTest implements Measure {
	private Map<Party, List<Double>> votesPercentages(State state){
		Map<Party, List<Double>> result = new HashMap<>();
		Arrays.stream(Party.values()).forEach(party -> {
			result.put(party, new ArrayList<>());
		});
		state.getDistricts().forEach(district -> {
		    Party elected = district.getElectedParty();
		    result.get(elected).add(district.getPartyPercent(elected));
		});
		return result;
	}

	private Double tTest(List<Double> a, List<Double> b){
		TTest tTest = new TTest();
		double[] arrayA = a.stream().mapToDouble(Double::doubleValue).toArray();
		double[] arrayB = b.stream().mapToDouble(Double::doubleValue).toArray();
		Double oneTailedPvalue = tTest.tTest(arrayA, arrayB) / 2.0;
		return oneTailedPvalue;
	}

	private Boolean exceedsThreshold(Double result, Double threshold) {
	    return result > threshold ? true : false;
	}

	@Override
	public MeasureResults runMeasure(MultiDistrictRegion region) {
	    State state = (State) region;
		LopsidedResults results = new LopsidedResults();

		Map<Party, List<Double>> percents = votesPercentages(state);
		Boolean passOrFail = null;
		Double pvalue = null;
		if(state.getElectedParty() == Party.Democrat){
		    pvalue = tTest(percents.get(Party.Democrat), percents.get(Party.Republican));
		    passOrFail = exceedsThreshold(pvalue, CommonConstants.TTEST_THRESHOLD);
		}

		results.addTestResult(passOrFail);
		results.setPvalue(pvalue);
		return results;
	}
}
