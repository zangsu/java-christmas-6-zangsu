package christmas.domain.benefit.model;

import christmas.domain.date.model.PromotionDay;
import christmas.domain.menu.model.collection.OrderSheet;

public interface BenefitFactory {
    /**
     * 할인 적용이 가능한지를 판단한다.
     *
     * @param promotionDay 할인을 받을 예약 날짜
     * @param orderSheet   할인을 적용시킬 주문서
     * @return 할인 가능 여부
     */
    boolean canApply(PromotionDay promotionDay, OrderSheet orderSheet);

    Benefit generate(PromotionDay promotionDay, OrderSheet orderSheet);
}
