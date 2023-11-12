package christmas.view.io;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;

public class Reader {
    public int getInteger(){
        return parseInt(Console.readLine());
    }

    public List<String> getStringsByDelimiter(String delimiter){
        String input = Console.readLine().trim();
        validNotEndWithDelimiter(delimiter, input);
        return Arrays.stream(input.split(delimiter))
                .toList();
    }

    private void validNotEndWithDelimiter(String delimiter, String input) {
        if(getLastString(input).equals(delimiter)){
            throw new IllegalArgumentException();
        }
    }

    private String getLastString(String input) {
        return input.substring(input.length() - 1);
    }

    private int parseInt(String input){
        try{
            return Integer.parseInt(input);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException();
        }
    }
}
