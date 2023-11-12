package christmas.domain.benefit;

import christmas.domain.benefit.constance.BenefitConst;
import christmas.domain.benefit.model.Benefits;
import christmas.domain.benefit.model.discount.Discount;
import christmas.domain.benefit.model.discount.DiscountFactories;
import christmas.domain.date.model.PromotionDay;
import christmas.domain.menu.model.collection.OrderSheet;
import java.util.List;

public class BenefitService {
    public List<Discount> getDiscounts(PromotionDay promotionDay, OrderSheet orderSheet) {
        return DiscountFactories.of(promotionDay, orderSheet);
    }

    //TODO
    public Benefits getBenefits(PromotionDay promotionDay, OrderSheet orderSheet){
        if(orderSheet.getTotalPrice() < BenefitConst.MIN_PRICE_FOR_BENEFIT){
            return new Benefits(List.of());
        }
        return null;
    }
}
