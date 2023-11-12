package christmas.benefit.model.discount.factory;

import christmas.benefit.constance.BenefitConst;
import christmas.benefit.model.discount.Discount;
import christmas.benefit.model.discount.DiscountType;
import christmas.date.model.PromotionDay;
import christmas.menu.model.MenuType;
import christmas.menu.model.collection.OrderSheet;

public class WeekdayDiscountFactory extends DiscountFactory{

    @Override
    boolean isGeneratable(PromotionDay promotionDay, OrderSheet orderSheet) {
        return promotionDay.isWeekDay() &&
                orderSheet.hasMenuOfType(MenuType.DESSERT);
    }

    @Override
    public Discount getDiscount(PromotionDay promotionDay, OrderSheet orderSheet) {
        return new Discount(DiscountType.WEEKDAY, getWeekdayPrice(orderSheet));
    }

    private static int getWeekdayPrice(OrderSheet orderSheet) {
        return orderSheet.getCountOfMenuType(MenuType.DESSERT) *
                BenefitConst.WEEKDAY_DISCOUNT_PRICE;
    }

}
