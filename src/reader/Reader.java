package reader;

import java.util.Scanner;
import java.util.function.Predicate;

public class Reader {
    private Scanner scanner;

    public Reader(Scanner scanner) {
        this.scanner = scanner;
    }


    public String getInputWithPrompt(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }


    public String getValidatedInput(String prompt, Predicate<String> validator, String errorMessage) {
        String input;
        boolean result= false;
        do {
            input = getInputWithPrompt(prompt);

            result = validator.test(input);
            if(!result) {
                System.out.println(errorMessage);
            }
        }while(!result);


        return input;

    }

    public String getInputDefaultWhenTrue(String prompt, Predicate<String> validator, String defaultString) {
        String input = getInputWithPrompt(prompt);

        if(validator.test(input)) {
            return defaultString;
        }


        return input;

    }
}
