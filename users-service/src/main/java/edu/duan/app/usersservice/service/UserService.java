package edu.duan.app.usersservice.service;

import edu.duan.app.api.User;
import edu.duan.app.usersservice.data.UserEntity;
import edu.duan.app.usersservice.data.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UsersRepository userRepository;

    public UserService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(long id) {
        UserEntity userEntity = userRepository.getReferenceById(id);
        return new User(userEntity.getId(), userEntity.getLogin());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(userEntity -> new User(userEntity.getId(), userEntity.getLogin())).toList();
    }

    public User createUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(user.getLogin());
        UserEntity createEntity = userRepository.save(userEntity);
        return new User(createEntity.getId(), createEntity.getLogin());
    }
}