package gerrymandering.measure;

import gerrymandering.common.TestType;

/**
 * Created by yisuo on 12/7/17.
 */
public class GeoCompactResults extends MeasureResults {
    private Double threshold;
    private Double compactness;
    public GeoCompactResults(){
        super();
        testPerformed = TestType.GeoCompactness;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public Double getCompactness() {
        return compactness;
    }

    public void setCompactness(Double compactness) {
        this.compactness = compactness;
    }
}
