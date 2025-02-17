package cab.aggregator.app.authservice.service;

import cab.aggregator.app.authservice.dto.request.RefreshTokenDto;
import cab.aggregator.app.authservice.dto.request.SignInAdminDto;
import cab.aggregator.app.authservice.dto.request.SignInDto;
import cab.aggregator.app.authservice.dto.request.SignUpDto;
import cab.aggregator.app.authservice.dto.response.AdminResponseTokenDto;
import cab.aggregator.app.authservice.dto.response.UserResponseTokenDto;

public interface UserService {

    void signUp(SignUpDto signUpDto);

    UserResponseTokenDto signIn(SignInDto signInDto);

    AdminResponseTokenDto signInAsAdmin(SignInAdminDto signInAdminDto);

    UserResponseTokenDto refreshToken(RefreshTokenDto refreshTokenDto);
}
