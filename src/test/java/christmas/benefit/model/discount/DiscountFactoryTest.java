package christmas.benefit.model.discount;

import christmas.benefit.constance.BenefitConst;
import christmas.benefit.model.Benefit;
import christmas.date.model.PromotionDay;
import christmas.menu.model.Menu;
import christmas.menu.model.MenuAndCount;
import christmas.menu.model.collection.OrderSheet;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountFactoryEnumTest {
    OrderSheet EmptySheet = new OrderSheet(List.of());

    @Nested
    @DisplayName("D-day 할인 테스트")
    class D_Day_테스트 {
        @ParameterizedTest(name = "{0}일의 D-day 할인 금액은 {1}원 이어야 한다.")
        @CsvSource(textBlock = """
                1, 1000
                6, 1500
                11, 2000
                25, 3400
                """)
        void 할인_적용_테스트(int date, int discountPrice) {
            PromotionDay day = PromotionDay.from(date);

            Assertions.assertThat(DiscountFactoryEnum.of(day, EmptySheet))
                    .contains(new Discount(DiscountType.D_DAY, discountPrice));
        }

        @ParameterizedTest(name = "{0}일에는 D-day 할인이 적용되지 않는다.")
        @ValueSource(ints = {26, 29, 31})
        void 할인_미적용_테스트(int date) {
            PromotionDay day = PromotionDay.from(date);

            List<Discount> discounts = DiscountFactoryEnum.of(day, EmptySheet);
            discounts.forEach(
                    discount -> Assertions.assertThat(discount.getType())
                            .isNotEqualTo(DiscountType.D_DAY)
            );

        }

    }

    @Nested
    @DisplayName("평일 할인 테스트")
    class 평일_할인_테스트 {

        @ParameterizedTest(name = "평일에 디저트를 {0}개 시키면 평일 할인이 {1}원 적용된다.")
        @CsvSource(textBlock = """
                1, 2023
                2, 4046
                3, 6069
                """)
        void 평일할인_적용_테스트(int dessertAmount, int discountPrice) {
            PromotionDay day = PromotionDay.from(20);
            OrderSheet orderSheet = new OrderSheet(
                    List.of(
                            new MenuAndCount(Menu.CHAMPAGNE, 5),
                            new MenuAndCount(Menu.CHOCOLATE_CAKE, dessertAmount)
                    ));

            Assertions.assertThat(DiscountFactoryEnum.of(day, orderSheet))
                    .contains(new Discount(DiscountType.WEEKDAY, discountPrice));
        }

        @Test
        @DisplayName("주말에는 평일 할인이 적용되지 않는다.")
        void 주말_평일할인_미적용_테스트() {
            PromotionDay day = PromotionDay.from(2);
            OrderSheet orderSheet = new OrderSheet(
                    List.of(
                            new MenuAndCount(Menu.CHAMPAGNE, 5),
                            new MenuAndCount(Menu.CHOCOLATE_CAKE, 5)
                    ));

            List<Discount> discounts = DiscountFactoryEnum.of(day, orderSheet);

            discounts.forEach(
                    discount -> Assertions.assertThat(discount.getType())
                            .isNotEqualTo(DiscountType.WEEKDAY)
            );
        }

        @Test
        @DisplayName("디저트가 없으면 평일 할인이 적용되지 않는다.")
        void 평일할인_미적용_테스트() {
            PromotionDay day = PromotionDay.from(20);
            OrderSheet orderSheet = new OrderSheet(
                    List.of(
                            new MenuAndCount(Menu.CHAMPAGNE, 5),
                            new MenuAndCount(Menu.CHOCOLATE_CAKE, 5)
                    ));

            List<Discount> discounts = DiscountFactoryEnum.of(day, orderSheet);

            discounts.forEach(
                    discount -> Assertions.assertThat(discount.getType())
                            .isNotEqualTo(DiscountType.WEEKDAY)
            );
        }
    }
}