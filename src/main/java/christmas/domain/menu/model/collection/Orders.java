package christmas.domain.menu.model.collection;

import christmas.domain.menu.constance.MenuConst;
import christmas.domain.menu.model.Menu;
import christmas.domain.menu.model.MenuAndCount;
import christmas.domain.menu.model.MenuType;
import christmas.exception.PromotionException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * 고객의 주문을 처리하기 위한 일급 컬렉션의 변형입니다. <br> 주문 데이터를 반환할 때는 {@link OrderSheet} 객체를 사용합니다.
 */
public class Orders {
    private final EnumMap<Menu, Integer> orders = new EnumMap<>(Menu.class);

    private int totalOrderAmount = 0;

    public Orders(String... orders) {
        Arrays.stream(orders)
                .forEach(this::addMenu);
        validateOrders();
    }

    private void addMenu(String order) {
        String[] splitOrder = order.split(MenuConst.MENU_AND_COUNT_DELIMITER);
        Menu menu = Menu.from(splitOrder[0]);
        int count = Integer.parseInt(splitOrder[1]);
        addMenu(menu, count);
    }

    private void addMenu(Menu menu, Integer count) {
        validateEachOrder(menu, count);
        totalOrderAmount += count;
        orders.put(menu, count);
    }

    /** 해당 주석 아래에는 전체 메뉴의 추가가 끝난 이후 수행되는 검등들이 위치합니다. */
    private void validateOrders() {
        validateOrderSize();
        validateContainsNonDrink();
    }

    private void validateOrderSize() {
        int orderCount = orders.keySet().size();
        if (orderCount < MenuConst.TOTAL_MENU_AMOUNT_MIN) {
            throw PromotionException.INVALID_ORDER.makeException();
        }
    }

    private void validateContainsNonDrink() {
        if (hasOnlyDrinkMenu()) {
            throw PromotionException.INVALID_ORDER.makeException();
        }
    }

    private boolean hasOnlyDrinkMenu() {
        return orders.keySet().stream()
                .dropWhile(menu -> menu.isTypeOf(MenuType.DRINK))
                .findAny()
                .isEmpty();
    }

    /**  해당 주석 하위에는 각 주문이 추가될 때 마다 수행되는 검증 메서드가 위치합니다. */

    private void validateEachOrder(Menu menu, Integer count) {
        validateDuplication(menu);
        validateAmount(count);
    }

    private void validateDuplication(Menu menu) {
        if (orders.containsKey(menu)) {
            throw PromotionException.INVALID_ORDER.makeException();
        }
    }

    private void validateAmount(Integer count) {
        validAmountGreaterThanMinimum(count);
        validAmountLessThanMaximum(count);
    }

    private void validAmountLessThanMaximum(Integer count) {
        if (totalOrderAmount + count > MenuConst.TOTAL_MENU_AMOUNT_MAX) {
            throw PromotionException.INVALID_ORDER.makeException();
        }
    }

    private void validAmountGreaterThanMinimum(Integer count) {
        if (count < MenuConst.EACH_MENU_AMOUNT_MIN) {
            throw PromotionException.INVALID_ORDER.makeException();
        }
    }

    /**
     * 주문을 확인하기 위한 불변 클래스 {@link OrderSheet}를 반환합니다.
     */
    public OrderSheet getOrderSheet() {
        return new OrderSheet(this.getOrders());
    }

    private List<MenuAndCount> getOrders() {
        return orders.entrySet().stream()
                .map(this::getMenuAndCount)
                .toList();
    }

    private MenuAndCount getMenuAndCount(Entry<Menu, Integer> entry) {
        return new MenuAndCount(entry.getKey(), entry.getValue());
    }
}
