package dto;

import domain.LoginObject;

public class LoginObjectDto {

    private int seq;             // 회원번호 PK
    private String id;           // 회원아이디 NN
    private String name;         // 회원이름 NN

    public LoginObjectDto(int seq, String id, String name) {
        this.seq = seq;
        this.id = id;
        this.name = name;
    }

    static public  LoginObjectDto of(LoginObject loginObject) {
        return  new LoginObjectDto(loginObject.getSeq(), loginObject.getId(), loginObject.getName());
    }


    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
