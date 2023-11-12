package christmas.benefit.model.discount.factory;

import christmas.benefit.constance.BenefitConst;
import christmas.benefit.model.discount.Discount;
import christmas.benefit.model.discount.DiscountType;
import christmas.date.model.PromotionDay;
import christmas.menu.model.MenuType;
import christmas.menu.model.collection.OrderSheet;

public class WeekendDiscountFactory extends DiscountFactory{

    @Override
    boolean isGeneratable(PromotionDay promotionDay, OrderSheet orderSheet) {
        return promotionDay.isWeekend() &&
                orderSheet.hasMenuOfType(MenuType.MAIN);
    }

    @Override
    public Discount getDiscount(PromotionDay promotionDay, OrderSheet orderSheet) {
        return new Discount(DiscountType.WEEKEND, getWeekendPrice(orderSheet));
    }

    private static int getWeekendPrice(OrderSheet orderSheet) {
        return orderSheet.getCountOfMenuType(MenuType.MAIN) *
                BenefitConst.WEEKEND_DISCOUNT_PRICE;
    }

}
