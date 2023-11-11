package christmas.menu.model.collection;

import christmas.constance.Const;
import christmas.constance.PromotionException;
import christmas.menu.model.Menu;
import christmas.menu.model.MenuAndCount;
import christmas.menu.model.MenuType;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

public class Orders {
    private final EnumMap<Menu, Integer> orders = new EnumMap<>(Menu.class);
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
        validateOrderSize();
        validateContainsNonDrink();
    }

    private void validateOrderSize() {
        int orderCount = orders.keySet().size();
        if(orderCount < Const.TOTAL_MENU_AMOUNT_MIN){
            throw PromotionException.TOTAL_AMOUNT_UNDER_MIN.makeException();
        }
    }

    private void addMenu(String order){
        String[] splitOrder = order.split(Const.MENU_AND_COUNT_DELIMITER);
        Menu menu = Menu.from(splitOrder[0]);
        int count = Integer.parseInt(splitOrder[1]);
        addMenu(menu, count);
    }
    private void addMenu(Menu menu, Integer count){
        validate(menu, count);
        totalOrderAmount += count;
        orders.put(menu, count);
    }

    private void validate(Menu menu, Integer count) {
        validateDuplication(menu);
        validateAmount(count);
    }

    private void validateDuplication(Menu menu) {
        if(orders.containsKey(menu)){
            throw PromotionException.DUPLICATED_MENU.makeException();
        }
    }

    private void validateAmount(Integer count) {
        if(count < Const.EACH_MENU_AMOUNT_MIN){
            throw PromotionException.EACH_AMOUNT_UNDER_MIN.makeException();
        }
        if(totalOrderAmount + count > Const.TOTAL_MENU_AMOUNT_MAX){
            throw PromotionException.TOTAL_AMOUNT_OVER_MAX.makeException();
        }
    }

    private void validateContainsNonDrink() {
        int nonDrinkCount = getNonDrinkCount();
        if(nonDrinkCount == 0){
            throw PromotionException.ALL_DRINK_ORDER.makeException();
        }
    }

    private int getNonDrinkCount() {
        return (int) orders.keySet().stream()
                .filter(menu -> menu.getType() != MenuType.DRINK)
                .count();
    }

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
