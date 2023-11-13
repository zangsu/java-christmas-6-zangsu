package christmas.domain.benefit;

import christmas.domain.benefit.constance.BenefitConst;
import christmas.domain.benefit.model.Benefit;
import christmas.domain.benefit.model.Benefits;
import christmas.domain.benefit.model.discount.DiscountFactories;
import christmas.domain.benefit.model.gift.GiftFactories;
import christmas.domain.date.model.PromotionDay;
import christmas.domain.menu.model.collection.OrderSheet;
import java.util.ArrayList;
import java.util.List;

//TODO : 혜택이 없을 경우 -0원 으로 나오는 부분
public class BenefitService {

    public Benefits getBenefits(PromotionDay promotionDay, OrderSheet orderSheet) {
        if (orderSheet.getTotalPrice() < BenefitConst.MIN_PRICE_FOR_BENEFIT) {
            return new Benefits(List.of());
        }
        List<Benefit> benefits = new ArrayList<>();
        benefits.addAll(getGifts(promotionDay, orderSheet));
        benefits.addAll(getDiscounts(promotionDay, orderSheet));
        return new Benefits(benefits);
    }

    private List<Benefit> getGifts(PromotionDay promotionDay, OrderSheet orderSheet) {
        return GiftFactories.of(promotionDay, orderSheet);
    }

    private List<Benefit> getDiscounts(PromotionDay promotionDay, OrderSheet orderSheet) {
        return DiscountFactories.of(promotionDay, orderSheet);
    }
}
