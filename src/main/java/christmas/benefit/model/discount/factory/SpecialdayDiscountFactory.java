package christmas.benefit.model.discount.factory;

import christmas.benefit.constance.BenefitConst;
import christmas.benefit.model.discount.Discount;
import christmas.benefit.model.discount.DiscountType;
import christmas.date.model.PromotionDay;
import christmas.menu.model.MenuType;
import christmas.menu.model.collection.OrderSheet;

public class SpecialdayDiscountFactory extends DiscountFactory{

    @Override
    boolean isGeneratable(PromotionDay promotionDay, OrderSheet orderSheet) {
        if (promotionDay.isSpecialDay()) {
            return true;
        }
        return false;
    }

    @Override
    public Discount getDiscount(PromotionDay promotionDay, OrderSheet orderSheet) {
        return new Discount(DiscountType.WEEKDAY, BenefitConst.SPECIAL_DISCOUNT_PRICE);
    }

}
