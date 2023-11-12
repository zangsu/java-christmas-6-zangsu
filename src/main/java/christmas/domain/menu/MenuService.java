package christmas.domain.menu;

import christmas.domain.menu.model.collection.Orders;
import christmas.domain.menu.model.collection.OrderSheet;

public class MenuService {

    public OrderSheet getOrderSheet(String... inputOrders){
        Orders orders = new Orders(inputOrders);
        return orders.getOrderSheet();
    }
}
