package christmas.menu;

import christmas.menu.model.collection.Orders;
import christmas.menu.model.collection.OrderSheet;

public class MenuService {

    public OrderSheet getOrderSheet(String... inputOrders){
        Orders orders = new Orders(inputOrders);
        return orders.getOrderSheet();
    }
}
