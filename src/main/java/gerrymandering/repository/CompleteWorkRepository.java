package gerrymandering.repository;

import gerrymandering.model.CompleteWork;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by yisuo on 12/7/17.
 */
public interface CompleteWorkRepository extends CrudRepository<CompleteWork, Integer> {
    //List<CompleteWork> findByCreatorId(Integer creatorId);
}
