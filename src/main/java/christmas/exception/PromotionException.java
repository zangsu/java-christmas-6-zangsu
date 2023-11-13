package christmas.exception;

public enum PromotionException {
    INVALID_DATE("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    INVALID_ORDER("유효하지 않은 주문입니다. 다시 입력해 주세요."),
    ;

    public static final String PREFIX = "[ERROR] ";
    private final String message;

    PromotionException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }

    //abstract IllegalArgumentException illegalArgumentException();
    public IllegalArgumentException makeException() {
        return new IllegalArgumentException(this.getMessage());
    }
}
