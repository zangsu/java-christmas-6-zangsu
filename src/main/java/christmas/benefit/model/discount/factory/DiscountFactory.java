package christmas.benefit.model.discount.factory;

import christmas.benefit.model.discount.Discount;
import christmas.date.model.PromotionDay;
import christmas.menu.model.collection.OrderSheet;

public abstract class DiscountFactory {
    abstract boolean isGeneratable(PromotionDay promotionDay, OrderSheet orderSheet);
    abstract public Discount getDiscount(PromotionDay promotionDay, OrderSheet orderSheet);
}
