package christmas.constance;

public enum PromotionException {
    INVALID_DATE_NUMBER(String.format("잘못된 날짜입니다. %d ~ %d 사이의 숫자를 입력해 주세요",
            Const.DECEMBER_DATE_START, Const.DECEMBER_DATE_END)),
    NO_SUCH_MENU("존재하지 않는 메뉴가 입력되었습니다. 다시 입력해 주세요");
    ;

    public static final String PREFIX = "[ERROR] ";
    private final String message;

    PromotionException(String message){
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }

    //abstract IllegalArgumentException illegalArgumentException();
    public IllegalArgumentException makeException(){
        return new IllegalArgumentException(this.getMessage());
    }
}
