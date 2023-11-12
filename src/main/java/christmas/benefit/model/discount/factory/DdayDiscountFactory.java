package christmas.benefit.model.discount.factory;

import christmas.benefit.constance.BenefitConst;
import christmas.benefit.model.discount.Discount;
import christmas.benefit.model.discount.DiscountType;
import christmas.date.model.PromotionDay;
import christmas.menu.model.collection.OrderSheet;

public class DdayDiscountFactory extends DiscountFactory{

    @Override
    boolean isGeneratable(PromotionDay promotionDay, OrderSheet orderSheet) {
        return promotionDay.getDDayFromXMax() >= 0;
    }

    @Override
    public Discount getDiscount(PromotionDay promotionDay, OrderSheet orderSheet) {
        return new Discount(DiscountType.D_DAY, getDdayPrice(promotionDay));
    }

    private static int getDdayPrice(PromotionDay promotionDay) {
        return BenefitConst.D_DAY_INITIAL_PRICE +
                promotionDay.getDayFromStart() * BenefitConst.D_DAY_INCREASE_PRICE;
    }

}
