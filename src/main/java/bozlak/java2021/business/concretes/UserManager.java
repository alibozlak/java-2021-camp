package bozlak.java2021.business.concretes;

import org.springframework.stereotype.Service;

import bozlak.java2021.business.abstracts.UserService;
import bozlak.java2021.core.dataAccess.UserRepository;
import bozlak.java2021.core.dtos.user.CreateUserRequest;
import bozlak.java2021.core.dtos.user.UserResponse;
import bozlak.java2021.core.entities.User;
import bozlak.java2021.core.utilities.results.DataResult;
import bozlak.java2021.core.utilities.results.Result;
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
    public DataResult<UserResponse> findByEmail(String email) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
