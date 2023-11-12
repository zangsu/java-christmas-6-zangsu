package christmas.view;

import java.text.DecimalFormat;

public interface ViewConst {

    String ORDERS_DELIMITER = ",";
    String NONE = "없음";
    String HELLO_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";

    String DATE_REQUEST_MESSAGE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    String ORDER_REQUEST_MESSAGE = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";

    String TITLE_ORDER_MENU = "<주문 메뉴>";
    String TITLE_TOTAL_PRICE_NO_DISCOUNT = "<할인 전 총주문 금액>";
    String TITLE_GIFT_LIST = "<증정 메뉴>";
    String TITLE_BENEFIT_LIST = "<혜택 내역>";
    String TITLE_BENEFIT_PRICE = "<총혜택 금액>";
    String TITLE_TOTAL_PRICE_WITH_DISCOUNT = "<할인 후 예상 결제 금액>";
    String TITLE_BADGE = "<12월 이벤트 배지>";

    String FORMAT_BENEFIT_TITLE = "12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    String FORMAT_PRICE = "%s원";
    String FORMAT_BENEFIT_PRICE = "-%s원";
    String FORMAT_ORDER_MENU = "%s - %d개";
    String FORMAT_BENEFIT = "%s: " + FORMAT_BENEFIT_PRICE;

}
