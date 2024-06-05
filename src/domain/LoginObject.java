package domain;

public class LoginObject {
    private Integer seq;
    private String id;
    private String name;
    private String password;

    public LoginObject(Integer seq, String id, String name, String password) {
        this.seq = seq;
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public boolean checkPassword(String empPassword) {
        if(this.password.equals(empPassword)) {
            return  true;
        }

        return false;
    }

    public Integer getSeq() {
        return seq;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
