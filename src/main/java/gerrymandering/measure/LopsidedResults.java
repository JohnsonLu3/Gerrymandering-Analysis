package gerrymandering.measure;

import gerrymandering.common.TestType;

/**
 * Created by yisuo on 11/7/17.
 */
public class LopsidedResults extends MeasureResults {
    private Double pvalue;
    private Double threshold;

    public LopsidedResults() {
        super();
        testPerformed = TestType.Lopsided;
    }

    public Double getPvalue() {
        return pvalue;
    }

    public void setPvalue(Double pvalue) {
        this.pvalue = pvalue;
    }

    public Double getThreshold(){
        return threshold;
    }

    public void setThreshold(Double threshold){
        this.threshold = threshold;
    }
}
