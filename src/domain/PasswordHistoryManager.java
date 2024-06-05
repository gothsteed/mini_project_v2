package domain;

import java.util.List;

public class PasswordHistoryManager {

    private List<PasswordThreeMonth> history;

    public PasswordHistoryManager(List<PasswordThreeMonth> history) {
        this.history = history;
    }

    public boolean isUsedRecently(String password){
        for(PasswordThreeMonth pastHistory : history) {

            if(pastHistory.isDuplicate(password)) {
                return true;
            }

        }

        return false;

    }
}
