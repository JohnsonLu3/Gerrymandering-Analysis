package gerrymandering.repository;

import gerrymandering.model.MonteCarloSeats;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yisuo on 12/6/17.
 */
@Repository
public interface MonteCarloSeatsRepository extends CrudRepository<MonteCarloSeats, Integer> {
//    @Query(value = "SELECT * FROM Simulations WHERE StateId = ?1", nativeQuery = true)
    List<MonteCarloSeats> findFirstByStateId(Integer stateId);
}
