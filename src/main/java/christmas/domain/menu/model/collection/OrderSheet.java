package christmas.domain.menu.model.collection;

import christmas.domain.menu.model.MenuAndCount;
import christmas.domain.menu.model.MenuType;
import java.util.Collections;
import java.util.List;

/** 주문 확인을 위한 불변 일급 컬렉션 입니다. */
public class OrderSheet {
    private final List<MenuAndCount> orderSheet;

    public OrderSheet(List<MenuAndCount> orderSheet) {
        this.orderSheet = orderSheet;
    }

    public List<MenuAndCount> getOrderSheet() {
        return Collections.unmodifiableList(orderSheet);
    }

    public int getTotalPrice() {
        return orderSheet.stream()
                .mapToInt(MenuAndCount::getTotalPrice)
                .sum();
    }

    public boolean hasMenuOfType(MenuType type) {
        return orderSheet.stream()
                .anyMatch(menuAndCount -> menuAndCount.isTypeOf(type));
    }

    public int getCountOfMenuType(MenuType type) {
        return orderSheet.stream()
                .filter(menuAndCount -> menuAndCount.isTypeOf(type))
                .mapToInt(MenuAndCount::getCount)
                .sum();
    }
}
