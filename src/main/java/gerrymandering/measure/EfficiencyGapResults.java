package gerrymandering.measure;

import gerrymandering.common.CommonConstants;
import gerrymandering.common.TestType;

/**
 * Created by yisuo on 11/7/17.
 */
public class EfficiencyGapResults extends MeasureResults {
    private Double efficiencyGap;
    private Double legislativeThreshold = CommonConstants.EFFICIENCYGAP_THRESHOLD;

    public EfficiencyGapResults()
    {
        super();
        testPerformed = TestType.EfficiencyGap;
    }
    public Double getEfficiencyGap() {
        return efficiencyGap;
    }

    public void setEfficiencyGap(Double efficiencyGap) {
        this.efficiencyGap = efficiencyGap;
    }

    public Double getLegislativeThreshold() {
        return legislativeThreshold;
    }

    public void setLegislativeThreshold(Double legislativeThreshold) {
        this.legislativeThreshold = legislativeThreshold;
    }
}
