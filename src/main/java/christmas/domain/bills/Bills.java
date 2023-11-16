package christmas.domain.bills;

import christmas.domain.badge.model.Badge;
import christmas.domain.benefit.model.Benefits;
import christmas.domain.menu.model.collection.OrderSheet;

public class Bills {
    private final int date;
    private final OrderSheet orderSheet;
    private final Benefits benefits;
    private final Badge badge;

    private Bills(int date, OrderSheet orderSheet, Benefits benefits, Badge badge) {
        this.date = date;
        this.orderSheet = orderSheet;
        this.benefits = benefits;
        this.badge = badge;
    }

    public static BillsBuilder builder() {
        return new BillsBuilder();
    }

    public static class BillsBuilder {

        private int date;
        private OrderSheet orderSheet;
        private Benefits benefits;
        private Badge badge;

        public BillsBuilder date(int date) {
            this.date = date;
            return this;
        }

        public BillsBuilder orderSheet(OrderSheet orderSheet) {
            this.orderSheet = orderSheet;
            return this;
        }

        public BillsBuilder benefits(Benefits benefits) {
            this.benefits = benefits;
            return this;
        }

        public BillsBuilder Badge(Badge badge) {
            this.badge = badge;
            return this;
        }

        public Bills build() {
            return new Bills(this.date, this.orderSheet, this.benefits, this.badge);
        }
    }

    public int getDate() {
        return date;
    }

    public OrderSheet getOrderSheet() {
        return orderSheet;
    }

    public Benefits getBenefits() {
        return benefits;
    }

    public Badge getBadge() {
        return badge;
    }

    public int getDiscountedPrice() {
        return orderSheet.getTotalPrice() + benefits.getDiscountPrice();
    }

}
