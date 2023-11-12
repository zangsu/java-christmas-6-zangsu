package christmas.benefit.model.discount.factory;

import christmas.benefit.model.discount.Discount;
import christmas.date.model.PromotionDay;
import christmas.menu.model.collection.OrderSheet;
import java.util.List;

public class DiscountFactories {
    List<DiscountFactory> factories;

    public DiscountFactories(List<DiscountFactory> factories) {
        this.factories = factories;
    }

    public List<Discount> getDiscounts(PromotionDay promotionDay, OrderSheet orderSheet){
        return factories.stream()
                .filter(factory -> factory.isGeneratable(promotionDay, orderSheet))
                .map(factory -> factory.getDiscount(promotionDay, orderSheet))
                .toList();
    }
}
