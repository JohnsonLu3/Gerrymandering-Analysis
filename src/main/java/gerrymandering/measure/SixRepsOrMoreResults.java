package gerrymandering.measure;

import gerrymandering.common.TestType;

public class SixRepsOrMoreResults extends MeasureResults {
    private Double threshold;
    private Double compactness;
    public SixRepsOrMoreResults(){
        super();
        testPerformed = TestType.SixRepsOrMore;
    }
}
