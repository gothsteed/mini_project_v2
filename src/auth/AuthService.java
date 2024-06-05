package auth;

import domain.LoginObject;

import java.util.Map;

public interface AuthService {
    LoginObject login(String id, String passwod);
}
