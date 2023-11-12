package christmas.benefit.model.discount;

import christmas.benefit.constance.BenefitConst;
import christmas.date.model.PromotionDay;
import christmas.menu.model.MenuType;
import christmas.menu.model.collection.OrderSheet;
import java.util.Arrays;
import java.util.List;

/**
 * Discount 를 생성하는 역할들을 `enum` 상수로 관리
 */
public enum DiscountFactories {

    D_DAY_FACTORY(DiscountType.D_DAY) {
        @Override
        boolean canDiscount(PromotionDay promotionDay, OrderSheet orderSheet) {
            return promotionDay.getDDayFromXMax() >= 0;
        }

        @Override
        int getDiscountPrice(PromotionDay promotionDay, OrderSheet orderSheet){
            return BenefitConst.D_DAY_INITIAL_PRICE +
                    promotionDay.getDayFromStart() * BenefitConst.D_DAY_INCREASE_PRICE;
        }
    },
    WEEKDAY_FACTORY(DiscountType.WEEKDAY) {
        @Override
        boolean canDiscount(PromotionDay promotionDay, OrderSheet orderSheet) {
            return promotionDay.isWeekDay() &&
                    orderSheet.hasMenuOfType(MenuType.DESSERT);
        }

        @Override
        int getDiscountPrice(PromotionDay promotionDay, OrderSheet orderSheet){
            return orderSheet.getCountOfMenuType(MenuType.DESSERT) *
                    BenefitConst.WEEKDAY_DISCOUNT_PRICE;
        }
    },
    WEEKEND_FACTORY(DiscountType.WEEKEND) {
        @Override
        boolean canDiscount(PromotionDay promotionDay, OrderSheet orderSheet) {
            return promotionDay.isWeekend() &&
                    orderSheet.hasMenuOfType(MenuType.MAIN);
        }

        @Override
        int getDiscountPrice(PromotionDay promotionDay, OrderSheet orderSheet){
            return orderSheet.getCountOfMenuType(MenuType.MAIN) *
                    BenefitConst.WEEKEND_DISCOUNT_PRICE;
        }
    },
    SPECIAL_FACTORY(DiscountType.SPECIAL) {
        @Override
        boolean canDiscount(PromotionDay promotionDay, OrderSheet orderSheet) {
            return promotionDay.isSpecialDay();
        }

        @Override
        int getDiscountPrice(PromotionDay promotionDay, OrderSheet orderSheet) {
            return BenefitConst.SPECIAL_DISCOUNT_PRICE;
        }
    };

    /** 팩토리가 생성할 Discount 의 타입 */
    private final DiscountType type;

    /**
     * 할인 적용이 가능한지를 판단한다.
     * @param promotionDay 할인을 받을 예약 날짜
     * @param orderSheet 할인을 적용시킬 주문서
     * @return 할인 가능 여부
     */
    abstract boolean canDiscount(PromotionDay promotionDay, OrderSheet orderSheet);

    /**
     * 할인 받을 금액을 계산한다.
     * @param promotionDay 할인을 받을 예약 날짜
     * @param orderSheet 할인을 적용시킬 주문서
     * @return 할인 정책에 따라 계산된 할인 금액
     */
    abstract int getDiscountPrice(PromotionDay promotionDay, OrderSheet orderSheet);


    public Discount generate(PromotionDay promotionDay, OrderSheet orderSheet){
        return new Discount(getType(), getDiscountPrice(promotionDay, orderSheet));
    }

    DiscountFactories(DiscountType type) {
        this.type = type;
    }

    /**
     * 팩토리들을 순회하며 적용 가능한 모든 Discount 를 생성하여 List로 반환하는 메서드
     * @param promotionDay 할인을 적용할 날짜
     * @param orderSheet 할인을 적용받을 주문서
     * @return 적용된 할인들의 리스트
     */
    public static List<Discount> of(PromotionDay promotionDay, OrderSheet orderSheet){
        return Arrays.stream(DiscountFactories.values())
                .filter(factory -> factory.canDiscount(promotionDay, orderSheet))
                .map(factory -> factory.generate(promotionDay, orderSheet))
                .toList();
    }

    public DiscountType getType() {
        return type;
    }
}
