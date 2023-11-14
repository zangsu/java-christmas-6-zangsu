package christmas.domain.benefit.constance;

public interface BenefitConst {
    String GIFT_DESCRIPTION = "증정 이벤트";

    int D_DAY_INITIAL_PRICE = 1_000;
    int D_DAY_INCREASE_PRICE = 100;
    int WEEKDAY_DISCOUNT_PRICE = 2_023;
    int WEEKEND_DISCOUNT_PRICE = 2_023;
    int SPECIAL_DISCOUNT_PRICE = 1_000;

    int MIN_PRICE_FOR_BENEFIT = 10_000;

    int MIN_PRICE_FOR_CHAMPAGNE = 120_000;
}
