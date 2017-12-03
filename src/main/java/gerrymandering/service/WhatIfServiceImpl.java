package gerrymandering.service;

import gerrymandering.model.*;
import gerrymandering.repository.NeighborRepository;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yisuo on 12/2/17.
 */
@Service("whatIfService")
public class WhatIfServiceImpl implements WhatIfService {
    @Autowired
    NeighborRepository neighbors;

    @Override
    public State combineDistrictsAuto(Collection<District> districts) {
        return null;
    }

    @Override
    public SuperDistrict combineDistrictsManual(Collection<District> districts) {
        SuperDistrict superDistrict = new SuperDistrict();
        superDistrict.getDistricts().addAll(districts);

        List<Boundary> boundaries = new ArrayList<>();
        districts.forEach(district -> boundaries.addAll(district.getBoundaries()));
        return superDistrict;
    }

    @Override
    public State saveCompletedWork(State completedWork) {
        return null;
    }

    @Override
    public List<State> loadCompletedWorks(Integer numItems) {
        return null;
    }

    @Override
    public File downloadWork(State completedWork) {
        return null;
    }
}
