package christmas.menu.model.collection;

import christmas.menu.constance.MenuConst;
import christmas.constance.PromotionException;
import christmas.menu.model.Menu;
import christmas.menu.model.MenuAndCount;
import christmas.menu.model.MenuType;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * 고객의 주문을 처리하기 위한 일급 컬렉션의 변형입니다. <br>
 * 주문 데이터를 반환할 때는 {@link OrderSheet} 객체를 사용합니다.
 */
public class Orders {
    private final EnumMap<Menu, Integer> orders = new EnumMap<>(Menu.class);

    /**
     * 총 주문 메뉴의 개수를 저장하며, 해당 개수는 20개를 넘어가서는 안됩니다.
     */
    private int totalOrderAmount = 0;

    //TODO : 입력받는 order들은 항상 [$메뉴 이름]-[$숫자] 형식이어야 한다.

    /**
     * "[$메뉴 이름]-[$숫자]" 형식의 주문들을 전달받아 해당 주문들을 저장한 {@link Orders}를 생성합니다.
     * @param orders 1 개 이상의 "[$메뉴 이름]-[$숫자]" 형식의 주문
     * @throws IllegalArgumentException <br>
     * 존재하지 않는 메뉴를 주문한 경우 <br>
     * 주문 메뉴의 개수가 최소 주문 개수 이하인 경우 <br>
     * 총 주문 개수가 최소 총 주문 개수 미만인 경우 <br>
     * 총 주문 개수가 최대 총 주문 개수를 초과한 경우 <br>
     * 주문이 음료로만 구성된 경우 <br>
     * 동일한 메뉴의 주문이 2개 이상 존재하는 경우 <br>
     */
    public Orders(String... orders){
        Arrays.stream(orders)
                .forEach(this::addMenu);
        validateOrders();
    }

    private void addMenu(String order){
        String[] splitOrder = order.split(MenuConst.MENU_AND_COUNT_DELIMITER);
        Menu menu = Menu.from(splitOrder[0]);
        int count = Integer.parseInt(splitOrder[1]);
        addMenu(menu, count);
    }

    private void addMenu(Menu menu, Integer count){
        validateEachOrder(menu, count);
        totalOrderAmount += count;
        orders.put(menu, count);
    }

    /**
     * 해당 주석 아래에는 전체 메뉴의 추가가 끝난 이후 수행되는 검등들이 위치합니다.
     */
    private void validateOrders() {
        validateOrderSize();
        validateContainsNonDrink();
    }

    private void validateOrderSize() {
        int orderCount = orders.keySet().size();
        if(orderCount < MenuConst.TOTAL_MENU_AMOUNT_MIN){
            throw PromotionException.INVALID_ORDER.makeException();
        }
    }

    private void validateContainsNonDrink() {
        int nonDrinkCount = getNonDrinkCount();
        if(nonDrinkCount == 0){
            throw PromotionException.INVALID_ORDER.makeException();
        }
    }
    private int getNonDrinkCount() {
        return (int) orders.keySet().stream()
                .filter(menu -> menu.getType() != MenuType.DRINK)
                .count();
    }

    /**
     * 해당 주석 하위에는 각 주문이 추가될 때 마다 수행되는 검증 메서드가 위치합니다.
     */

    private void validateEachOrder(Menu menu, Integer count) {
        validateDuplication(menu);
        validateAmount(count);
    }

    private void validateDuplication(Menu menu) {
        if(orders.containsKey(menu)){
            throw PromotionException.INVALID_ORDER.makeException();
        }
    }

    private void validateAmount(Integer count) {
        if(count < MenuConst.EACH_MENU_AMOUNT_MIN){
            throw PromotionException.INVALID_ORDER.makeException();
        }
        if(totalOrderAmount + count > MenuConst.TOTAL_MENU_AMOUNT_MAX){
            throw PromotionException.INVALID_ORDER.makeException();
        }
    }

    /**
     * 주문을 확인하기 위한 불변 클래스 {@link OrderSheet}를 반환합니다.
     */
    public OrderSheet getOrderSheet(){
        return new OrderSheet(this.getOrders());
    }

    private List<MenuAndCount> getOrders() {
        return orders.entrySet().stream()
                .map(this::getMenuAndCount)
                .toList();
    }

    private MenuAndCount getMenuAndCount(Entry<Menu, Integer> entry){
        return new MenuAndCount(entry.getKey(), entry.getValue());
    }
}
