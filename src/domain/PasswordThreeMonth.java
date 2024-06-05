package domain;

import java.util.Date;

public class PasswordThreeMonth {

    private Integer passwdSew;
    private Integer memberSeq;
    private String password;
    private Date modifyDate;

    public PasswordThreeMonth(Integer passwdSew, Integer memberSeq, String password, Date modifyDate) {
        this.passwdSew = passwdSew;
        this.memberSeq = memberSeq;
        this.password = password;
        this.modifyDate = modifyDate;
    }

    public boolean isDuplicate(String password) {

        return this.password.equals(password);
    }
}
