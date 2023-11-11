package christmas.menu.model.collection;

import christmas.menu.model.MenuAndCount;
import java.util.List;

/**
 * 주문 확인을 위한 불변 클래스입니다.
 * 해당 클래스는 {@link Orders}를 통해서만 생성할 것을 권장합니다.
 */
public class OrderSheet {
    private final List<MenuAndCount> orderSheet;

    OrderSheet(List<MenuAndCount> orderSheet) {
        this.orderSheet = orderSheet;
    }

    public List<MenuAndCount> getOrderSheet() {
        return orderSheet;
    }

    public int getTotalPrice(){
        return orderSheet.stream()
                .mapToInt(MenuAndCount::getTotalPrice)
                .sum();
    }
}
