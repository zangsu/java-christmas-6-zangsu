package christmas.constance;

public enum ExceptionMessage {
    INVALID_DATE_NUMBER(String.format("잘못된 날짜입니다. %d ~ %d 사이의 숫자를 입력해 주세요",
            Const.DECEMBER_DATE_START, Const.DECEMBER_DATE_END));
    private final String message;
    ExceptionMessage(String message){
        this.message = message;
    }
    public static final String PREFIX = "[ERROR] ";

    /*public String getMessage() {
        return PREFIX + message;
    }*/

    //abstract IllegalArgumentException illegalArgumentException();
    public IllegalArgumentException makeException(){
        return new IllegalArgumentException(PREFIX + message);
    }
}
