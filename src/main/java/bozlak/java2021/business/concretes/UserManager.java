package bozlak.java2021.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import bozlak.java2021.business.abstracts.UserService;
import bozlak.java2021.core.dataAccess.UserRepository;
import bozlak.java2021.core.dtos.user.CreateUserRequest;
import bozlak.java2021.core.dtos.user.UserResponse;
import bozlak.java2021.core.entities.User;
import bozlak.java2021.core.utilities.results.ErrorResult;
import bozlak.java2021.core.utilities.results.Result;
import bozlak.java2021.core.utilities.results.SuccessDataResult;
import bozlak.java2021.core.utilities.results.SuccessResult;

@Service
public class UserManager implements UserService {

    private UserRepository userRepository;

    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Result add(CreateUserRequest createUserRequest) {
        String email = createUserRequest.getEmail();

        User user = new User();
        user.setEmail(email);
        user.setPassword(createUserRequest.getPassword());

        this.userRepository.save(user);
        String message = email + " emailli kullanıcı (user) eklendi.";
        return new SuccessResult(message);
    }

    @Override
    public Result findByEmail(String email) {
        if (!existsUserByEmail(email)) {
            return new ErrorResult("Veritabanında " + email + " emailine sahip bir kullanıcı yok!");
        }

        User user = this.userRepository.findByEmail(email);
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setEmail(email);
        userResponse.setPassword(user.getPassword());

        String message = email + " emailli kullanıcı getirildi.";
        return new SuccessDataResult<UserResponse>(message, userResponse);
    }

    private boolean existsUserByEmail(String email) {
        List<User> users = this.userRepository.findAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    
}
