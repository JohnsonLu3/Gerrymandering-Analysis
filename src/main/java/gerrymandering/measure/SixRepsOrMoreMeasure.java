package gerrymandering.measure;

import gerrymandering.model.*;

/**
 * Created by yisuo on 11/7/17.
 */
public class SixRepsOrMoreMeasure implements StateMeasure {

    private int checkNumDistricts(State s){
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

    private int checkNumOfSuperDistricts(CompleteWork completeWork){
        if(completeWork != null){

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
    public MeasureResults runStateMeasure(CompleteWork completeWork) {

        SixRepsOrMoreResults results = new SixRepsOrMoreResults();

        if(checkNumDistricts(completeWork.getState()) == -1){
            // SKIP TEST
            return null;
        }else{
            // PERFORM TEST
            if(checkNumOfSuperDistricts(completeWork) == 0){
                //checkNumOfRepsPerSuperDistrict();
            }else{
                results.addTestResult(false);
            }
        }


        return results;
    }
}
