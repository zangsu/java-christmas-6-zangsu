package christmas.domain.menu;

import christmas.domain.menu.model.collection.OrderSheet;
import christmas.domain.menu.model.collection.Orders;

public class MenuService {

    public OrderSheet getOrderSheet(String... inputOrders) {
        Orders orders = new Orders(inputOrders);
        return orders.getOrderSheet();
    }
}
