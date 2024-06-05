package auth;

import domain.LoginObject;
import dto.LoginObjectDto;

import java.util.Map;

public class AuthenticationManager {

    private Map<String,AuthService> serviceMap;
    private LoginObject loginObject;


    public AuthenticationManager(Map<String,AuthService> serviceMap, LoginObject loginObject) {
        this.serviceMap = serviceMap;
        this.loginObject = loginObject;
    }


    public boolean login(String type, String id, String password){
        AuthService service = serviceMap.get(type);
        loginObject = service.login(id, password);

        if(loginObject != null) {
            return true;
        }

        return false;
    }
    public void logout() {

        loginObject = null;

    }

    public boolean isLoggedIn() {
        if(loginObject != null) {
            return true;
        }

        return false;
    }


    public LoginObjectDto getLoginDto() {
        return LoginObjectDto.of(loginObject);
    }


}
