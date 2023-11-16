package christmas.domain.benefit.model.discount;

import christmas.domain.benefit.constance.BenefitConst;
import christmas.domain.benefit.model.Benefit;
import christmas.domain.benefit.model.BenefitFactory;
import christmas.domain.date.model.PromotionDay;
import christmas.domain.menu.model.MenuType;
import christmas.domain.menu.model.collection.OrderSheet;
import java.util.Arrays;
import java.util.List;

/**
 * Discount 를 생성하는 역할들을 `enum` 상수로 관리
 */
public enum DiscountFactories implements BenefitFactory {

    D_DAY_FACTORY(DiscountType.D_DAY) {
        @Override
        public boolean canApply(PromotionDay promotionDay, OrderSheet orderSheet) {
            return promotionDay.getDDayFromXMax() >= 0;
        }

        @Override
        int getDiscountPrice(PromotionDay promotionDay, OrderSheet orderSheet) {
            return BenefitConst.D_DAY_INITIAL_PRICE +
                    promotionDay.getDayFromStart() * BenefitConst.D_DAY_INCREASE_PRICE;
        }
    },
    WEEKDAY_FACTORY(DiscountType.WEEKDAY) {
        @Override
        public boolean canApply(PromotionDay promotionDay, OrderSheet orderSheet) {
            return promotionDay.isWeekDay() &&
                    orderSheet.hasMenuOfType(MenuType.DESSERT);
        }

        @Override
        int getDiscountPrice(PromotionDay promotionDay, OrderSheet orderSheet) {
            return orderSheet.getCountOfMenuType(MenuType.DESSERT) *
                    BenefitConst.WEEKDAY_DISCOUNT_PRICE;
        }
    },
    WEEKEND_FACTORY(DiscountType.WEEKEND) {
        @Override
        public boolean canApply(PromotionDay promotionDay, OrderSheet orderSheet) {
            return promotionDay.isWeekend() &&
                    orderSheet.hasMenuOfType(MenuType.MAIN);
        }

        @Override
        int getDiscountPrice(PromotionDay promotionDay, OrderSheet orderSheet) {
            return orderSheet.getCountOfMenuType(MenuType.MAIN) *
                    BenefitConst.WEEKEND_DISCOUNT_PRICE;
        }
    },
    SPECIAL_FACTORY(DiscountType.SPECIAL) {
        @Override
        public boolean canApply(PromotionDay promotionDay, OrderSheet orderSheet) {
            return promotionDay.isSpecialDay();
        }

        @Override
        int getDiscountPrice(PromotionDay promotionDay, OrderSheet orderSheet) {
            return BenefitConst.SPECIAL_DISCOUNT_PRICE;
        }
    };

    private static final DiscountFactories[] discountFactories = DiscountFactories.values();

    private final DiscountType type;

    DiscountFactories(DiscountType type) {
        this.type = type;
    }

    public static List<Benefit> of(PromotionDay promotionDay, OrderSheet orderSheet) {
        return Arrays.stream(discountFactories)
                .filter(factory -> factory.canApply(promotionDay, orderSheet))
                .map(factory -> factory.generate(promotionDay, orderSheet))
                .toList();
    }

    abstract int getDiscountPrice(PromotionDay promotionDay, OrderSheet orderSheet);

    @Override
    public Benefit generate(PromotionDay promotionDay, OrderSheet orderSheet) {
        return new Discount(this.getType(), getDiscountPrice(promotionDay, orderSheet));
    }

    private DiscountType getType() {
        return type;
    }
}
