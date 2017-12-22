package gerrymandering.repository;

import gerrymandering.model.District;
import gerrymandering.model.Neighbor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by yisuo on 12/3/17.
 */
public interface NeighborRepository extends CrudRepository<Neighbor, Integer>{
    List<Neighbor> findByDistrictA(District district);
}
