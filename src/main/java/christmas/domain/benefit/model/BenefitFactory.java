package christmas.domain.benefit.model;

import christmas.domain.date.model.PromotionDay;
import christmas.domain.menu.model.collection.OrderSheet;

public interface BenefitFactory {

    boolean canApply(PromotionDay promotionDay, OrderSheet orderSheet);

    Benefit generate(PromotionDay promotionDay, OrderSheet orderSheet);
}
