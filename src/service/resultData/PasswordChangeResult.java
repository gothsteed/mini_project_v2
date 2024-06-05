package service.resultData;


//Type Definition Dependency: Enums are indeed dependencies in terms of type definitions. If a method returns an enum or accepts an enum as a parameter, any class that interacts with this method will depend on that enum. Thus, the enum becomes part of the method's public interface.
//Loose Coupling: However, enums are generally seen as a lightweight and justified dependency. They do not represent a heavy coupling like dependencies on complex classes or services because enums are immutable and their values are fixed. Enums merely encode constant data and do not encapsulate behavior that could change independently.
//Compile-Time Dependency: Enums introduce a compile-time dependency, not a runtime dependency. This means your code must be aware of and agree upon the enum at compile time, but this doesn't affect the runtime dynamics in terms of behavior dependency (like service dependencies might).
//Maintainability and Clarity: Enums increase the clarity and maintainability of your code by making explicit the valid values that variables can hold. This can prevent errors related to using invalid or unrecognized raw values (like strings or integers), and it leverages the compile-time checking that something like a raw type does not offer.
public enum PasswordChangeResult {

    SUCCESS,
    FAILURE,
    PASSWORD_RECENTLY_USED,
    MEMBER_NOT_FOUND
}
