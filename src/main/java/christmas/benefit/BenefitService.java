package christmas.benefit;

import christmas.benefit.model.discount.Discount;
import christmas.benefit.model.discount.DiscountFactories;
import christmas.date.model.PromotionDay;
import christmas.menu.model.collection.OrderSheet;
import java.util.List;

public class BenefitService {
    public List<Discount> getDiscounts(PromotionDay promotionDay, OrderSheet orderSheet) {
        return DiscountFactories.of(promotionDay, orderSheet);
    }
}
