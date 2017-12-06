package gerrymandering.measure;

import gerrymandering.model.MultiDistrictRegion;
import gerrymandering.model.State;
import gerrymandering.model.SuperDistrict;

/**
 * Created by yisuo on 11/7/17.
 */
public class SixRepsOrMoreMeasure implements Measure {
    private int checkNumDistricts(SuperDistrict s){
        int districtCount = s.getDistricts().size();

        if(districtCount >= 6){
            return -1;              // skipped
        }

        return 0;                   // continue
    }

    private int checkNumOfSuperDistricts(State state){

        // Check the number of superdistricts plus the number of normal districts
        // this should be less than the number of total representatives.
        if((state.getSuperDistricts().size() + state.getDistricts().size()) < state.getDistricts().size()){
            return -1;               // failed
        }
        // Check the number of non-super districts. If it is 0 then return a resultobject
        // with the status as "failed" else continue
        if(state.getDistricts().size() == 0){
            return  -1;             // failed
        }

        return 0;                   // continue
    }

    private int checkNumOfRepsPerSuperDistrict(SuperDistrict s){
        // Check the number of representatives for each super distrist if it is greater
        // than 3 and less than 5. If the check fails then return a resultobject with the
        // status "failed" else "success".
        return 0;                   // pass
    }

    @Override
    public MeasureResults runMeasure(MultiDistrictRegion region) {
        return null;
    }
}
