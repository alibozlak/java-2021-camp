package bozlak.java2021.business.abstracts;

import bozlak.java2021.core.dtos.user.CreateUserRequest;
import bozlak.java2021.core.dtos.user.UserResponse;
import bozlak.java2021.core.utilities.results.DataResult;
import bozlak.java2021.core.utilities.results.Result;

public interface UserService {
    Result add(CreateUserRequest createUserRequest);
    DataResult<UserResponse> findByEmail(String email);
}
