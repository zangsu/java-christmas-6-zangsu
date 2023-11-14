package christmas.domain.benefit.model.discount;

public enum DiscountType {
    D_DAY("크리스마스 디데이 할인"), //12월 25일을 지나지 않은 경우 //날짜에 따라 100원씩 증가
    WEEKDAY("평일 할인"), //평일인 경우 // 디저트 메뉴 * 금액
    WEEKEND("주말 할인"), //주말인 경우 // 메인 메뉴 * 금액
    SPECIAL("특별 할인") //스페셜 데이인 경우 // 무조건 1000원
    ;

    private final String name;

    DiscountType(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
