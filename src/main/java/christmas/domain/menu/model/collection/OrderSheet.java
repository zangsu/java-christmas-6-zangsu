package christmas.domain.menu.model.collection;

import christmas.domain.menu.model.MenuAndCount;
import christmas.domain.menu.model.MenuType;
import java.util.List;

/**
 * 주문 확인을 위한 불변 클래스입니다.
 * 해당 클래스는 {@link Orders}를 통해서만 생성할 것을 권장합니다.
 */
public class OrderSheet {
    private final List<MenuAndCount> orderSheet;


    // TODO : 임시로 생성자를 열어뒀으나, 어떻게 관리할지는 더 고민해 보아야 함
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

    public boolean hasMenuOfType(MenuType type){
        return orderSheet.stream()
                .anyMatch(menuAndCount -> menuAndCount.getMenuType().equals(type));
    }

    public int getCountOfMenuType(MenuType type){
        return orderSheet.stream()
                .filter(menuAndCount -> menuAndCount.getMenuType().equals(type))
                .mapToInt(MenuAndCount::getCount)
                .sum();
    }
}
