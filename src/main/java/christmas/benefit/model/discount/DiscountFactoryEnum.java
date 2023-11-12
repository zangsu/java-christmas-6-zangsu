package christmas.benefit.model.discount;

import christmas.benefit.constance.BenefitConst;
import christmas.date.model.PromotionDay;
import christmas.menu.model.MenuType;
import christmas.menu.model.collection.OrderSheet;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * Discount 를 생성하는 역할들을 `enum` 상수로 관리
 */
public enum DiscountFactoryEnum {

    D_DAY_FACTORY(DiscountType.D_DAY,
            (promotionDay, orderSheet) ->
                    promotionDay.getDDayFromXMax() >= 0,
            (promotionDay, orderSheet) ->
                    BenefitConst.D_DAY_INITIAL_PRICE +
                            promotionDay.getDayFromStart() * BenefitConst.D_DAY_INCREASE_PRICE
    ), //12월 25일을 지나지 않은 경우 //날짜에 따라 100원씩 증가
    WEEKDAY_FACTORY(DiscountType.WEEKDAY,
            (promotionDay, orderSheet) -> promotionDay.isWeekDay()
                    && orderSheet.hasMenuOfType(MenuType.DESSERT),
            (promotionDay, orderSheet) ->
                    orderSheet.getCountOfMenuType(MenuType.DESSERT) * BenefitConst.WEEKDAY_DISCOUNT_PRICE
    ), //평일인 경우 // 디저트 메뉴 * 금액
    WEEKEND_FACTORY
            (DiscountType.WEEKEND,
            (promotionDay, orderSheet) -> promotionDay.isWeekend()
                    && orderSheet.hasMenuOfType(MenuType.MAIN),
            (promotionDay, orderSheet) ->
                    orderSheet.getCountOfMenuType(MenuType.MAIN) * BenefitConst.WEEKEND_DISCOUNT_PRICE
    ), //주말인 경우 // 메인 메뉴 * 금액
    SPECIAL_FACTORY(DiscountType.SPECIAL,
            (promotionDay, orderSheet) -> promotionDay.isSpecialDay(),
            (promotionDay, orderSheet) -> BenefitConst.SPECIAL_DISCOUNT_PRICE) //스페셜 데이인 경우 // 무조건 1000원
    ;

    /** 팩토리가 생성할 Discount 의 타입 */
    private final DiscountType type;
    /** Discount 가 적용되는지를 파악하는 Predicate */
    private final BiPredicate<PromotionDay, OrderSheet> predicate;
    /** Discount 의 가격을 결정하는 Function */
    private final BiFunction<PromotionDay, OrderSheet, Integer> priceCalculator;

    DiscountFactoryEnum(DiscountType type, BiPredicate<PromotionDay, OrderSheet> predicate, BiFunction<PromotionDay, OrderSheet, Integer> priceCalculator) {
        this.type = type;
        this.predicate = predicate;
        this.priceCalculator = priceCalculator;
    }

    /**
     * 팩토리들을 순회하며 적용 가능한 모든 Discount 를 생성하여 List로 반환하는 메서드
     * @param promotionDay 할인을 적용할 날짜
     * @param orderSheet 할인을 적용받을 주문서
     * @return 적용된 할인들의 리스트
     */
    public static List<Discount> of(PromotionDay promotionDay, OrderSheet orderSheet){
        return Arrays.stream(DiscountFactoryEnum.values())
                .filter(factory -> factory.canApplyDiscount(promotionDay, orderSheet))
                .map(factory -> factory.makeDiscount(promotionDay, orderSheet))
                .toList();
    }

    private boolean canApplyDiscount(PromotionDay promotionDay, OrderSheet orderSheet){
        return this.predicate.test(promotionDay, orderSheet);
    }

    private Discount makeDiscount(PromotionDay promotionDay, OrderSheet orderSheet){
        Integer price = this.priceCalculator.apply(promotionDay, orderSheet);
        return new Discount(this.type, price);
    }
}
