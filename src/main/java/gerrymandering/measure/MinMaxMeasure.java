package gerrymandering.measure;

import gerrymandering.model.CompleteWork;
import gerrymandering.model.MultiDistrictRegion;
import gerrymandering.model.State;

import java.util.List;
import java.util.Map;

/**
 * Created by yisuo on 11/7/17.
 */
public class MinMaxMeasure implements StateMeasure {
    private Map<Integer, Integer> composition;

    private Map<Integer, Integer> getOptimalComposition(State state){
        return null;
    }

    public void createComposition(List<Integer> ndistrictsInState){

    }

    @Override
    public MeasureResults runStateMeasure(CompleteWork completeWork) {
        return null;
    }
}
