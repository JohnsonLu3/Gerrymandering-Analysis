package gerrymandering.service;

import gerrymandering.model.User;
import gerrymandering.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("UserService")
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String email) {
        return userRepository.findByUsername(email);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.findById(id);
    }

    public User findByActivationKey(String activationKey){
        return userRepository.findByActivationKey(activationKey);
    }


    public void deleteUserById(long id){
        userRepository.delete(id);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

}