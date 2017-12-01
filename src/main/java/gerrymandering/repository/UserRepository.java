package gerrymandering.repository;

import gerrymandering.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
        User findById(long id);
        User findByUsername(String username);
        User findByActivationKey(String activationKey);
    }

