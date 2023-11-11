package christmas.constance;

public enum PromotionException {
    INVALID_DATE_NUMBER(String.format("잘못된 날짜입니다. %d ~ %d 사이의 숫자를 입력해 주세요",
            Const.DECEMBER_DATE_START, Const.DECEMBER_DATE_END)),
    NO_SUCH_MENU("존재하지 않는 메뉴가 입력되었습니다. 다시 입력해 주세요"),
    DUPLICATED_MENU("중복되는 메뉴가 존재합니다. 다시 입력해 주세요"),
    EACH_AMOUNT_UNDER_MIN(String.format("각 메뉴의 선택 개수는 %d개 이상이어야 합니다.", Const.EACH_MENU_AMOUNT_MIN)),
    TOTAL_AMOUNT_OVER_MAX(String.format("메뉴의 총 주문 개수는 %d개 이하여야 합니다.", Const.TOTAL_MENU_AMOUNT_MAX)),
    TOTAL_AMOUNT_UNDER_MIN(String.format("메뉴의 총 주문 개수는 %d개 이상이어야 합니다.", Const.TOTAL_MENU_AMOUNT_MIN)),
    ALL_DRINK_ORDER("음료로만 주문을 할 수 없습니다."),
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
