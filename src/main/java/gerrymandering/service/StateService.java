package gerrymandering.service;


import gerrymandering.model.State;
import gerrymandering.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("StateService")
public class StateService {
    @Autowired
    StateRepository stateRepository;

    public List<State> findPerviousYearState(State state, int year){
        return  stateRepository.findByStateNameAndYear(state.getStateName(), year);
    }
}
