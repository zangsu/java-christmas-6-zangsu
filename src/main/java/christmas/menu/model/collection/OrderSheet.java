package christmas.menu.model.collection;

import christmas.menu.model.MenuAndCount;
import java.util.List;

public class OrderSheet {
    private final List<MenuAndCount> orderSheet;

    public OrderSheet(List<MenuAndCount> orderSheet) {
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
