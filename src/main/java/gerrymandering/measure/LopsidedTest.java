package gerrymandering.measure;

import gerrymandering.common.CommonConstants;
import gerrymandering.common.Party;
import gerrymandering.model.MultiDistrictRegion;
import gerrymandering.model.State;
import gerrymandering.model.User;
import gerrymandering.service.UserService;
import org.apache.commons.math3.stat.inference.TTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

public class LopsidedTest implements Measure {

	double threshold = CommonConstants.TTEST_THRESHOLD;

	public LopsidedTest(UserService userService){
		double userThreshold = getUserPValueThreshold(userService);
		if(userThreshold != -1.0){
			threshold = userThreshold;
		}
	}

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
	    return result < threshold ? true : false;
	}

	@Override
	public MeasureResults runMeasure(MultiDistrictRegion region) {
	    State state = (State) region;
		LopsidedResults results = new LopsidedResults();

		Map<Party, List<Double>> percents = votesPercentages(state);
		if(percents.get(Party.Democrat).size() < 2 || percents.get(Party.Republican).size() < 2)
			return results;
		Boolean passOrFail = null;
		Double pvalue = null;
		if(state.getElectedParty() == Party.Democrat){
		    pvalue = tTest(percents.get(Party.Democrat), percents.get(Party.Republican));
		    passOrFail = !exceedsThreshold(pvalue, threshold);
		}
		else if(state.getElectedParty() == Party.Republican){
			pvalue = tTest(percents.get(Party.Republican), percents.get(Party.Democrat));
			passOrFail = !exceedsThreshold(pvalue, threshold);
		}

		results.addTestResult(passOrFail);
		results.setPvalue(pvalue);
		results.setThreshold(threshold);
		return results;
	}

	private double getUserPValueThreshold(UserService userService){
		double threshold = -1.0;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findByUsername(auth.getName());
		if(user == null)
			return threshold;
		else{
			if(user.getPValue() != null){
				threshold = user.getPValue();
			}
		}
		return threshold;
	}
}
