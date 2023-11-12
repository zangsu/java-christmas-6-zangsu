package christmas.domain.benefit;

import christmas.domain.benefit.model.discount.Discount;
import christmas.domain.benefit.model.discount.DiscountFactories;
import christmas.domain.date.model.PromotionDay;
import christmas.domain.menu.model.collection.OrderSheet;
import java.util.List;

public class BenefitService {
    public List<Discount> getDiscounts(PromotionDay promotionDay, OrderSheet orderSheet) {
        return DiscountFactories.of(promotionDay, orderSheet);
    }
}
