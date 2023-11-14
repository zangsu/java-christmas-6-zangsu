package christmas.view.io;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;

public class Reader {

    public void closeConsole(){
        Console.close();
    }
    public int getInteger() {
        String input = Console.readLine();
        validNotEmptyInput(input);
        return Integer.parseInt(input);
    }

    public List<String> getStringsByDelimiter(String delimiter) {
        String input = Console.readLine();
        validNotEmptyInput(input);
        validNotEndWithDelimiter(delimiter, input);
        return Arrays.stream(input.split(delimiter))
                .toList();
    }

    private void validNotEmptyInput(String input){
        if(input == null || input.isBlank()){
            throw new IllegalArgumentException("빈 입력입니다.");
        }
    }

    private void validNotEndWithDelimiter(String delimiter, String input) {
        if (getLastString(input).equals(delimiter)) {
            throw new IllegalArgumentException("입력이 구분자로 끝났습니다.");
        }
    }

    private String getLastString(String input) {
        return input.substring(input.length() - 1);
    }
}
