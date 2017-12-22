package gerrymandering.measure;

import gerrymandering.common.TestType;

/**
 * Created by yisuo on 11/7/17.
 */
public class ConsistentAdvantageResults extends MeasureResults {
    private Double meanMedianDifference;
    public ConsistentAdvantageResults(){
        super();
        testPerformed = TestType.ConsistentAdvantage;
    }

    public Double getMeanMedianDifference() {
        return meanMedianDifference;
    }

    public void setMeanMedianDifference(Double meanMedianDifference) {
        this.meanMedianDifference = meanMedianDifference;
    }
}
