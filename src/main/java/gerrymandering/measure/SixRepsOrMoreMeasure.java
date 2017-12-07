package gerrymandering.measure;

import com.sun.net.httpserver.Authenticator;
import gerrymandering.model.District;
import gerrymandering.model.MultiDistrictRegion;
import gerrymandering.model.State;
import gerrymandering.model.SuperDistrict;

import java.sql.ResultSet;

/**
 * Created by yisuo on 11/7/17.
 */
public class SixRepsOrMoreMeasure implements Measure {

    private int checkNumDistricts(SuperDistrict s){
        //Check if the number of representatives in the state is 6 or more. If it is less than 6
        //then return a resultobject with status as "skipped" else continue

        District district = s.getDistricts().get(0);
        State state= district.getState();
        int districtCount = state.getDistricts().size();

        if(districtCount >= 6){
            return -1;              // skipped
        }

        return 0;                   // continue
    }

    private int checkNumOfSuperDistricts(MultiDistrictRegion region){
        if(region != null){

        }else{
            return -1;              // failed
        }

        return 0;                   // continue
    }

    private int checkNumOfRepsPerSuperDistrict(SuperDistrict s){
        // Check the number of representatives for each super distrist if it is greater
        // than 3 and less than 5. If the check fails then return a resultobject with the
        // status "failed" else "success".
        if(s.getDistricts().size() < 3 || s.getDistricts().size() > 5){
            return -1;              // failed
        }
        return 0;                   // pass
    }

    @Override
    public MeasureResults runMeasure(MultiDistrictRegion region) {

        SixRepsOrMoreResults result = new SixRepsOrMoreResults();
//
//        if(checkNumDistricts(region) == 0){
//
//        }else{
//            // return skipped, state is not big enough
//            return null;
//        }
        return result;
    }
}
