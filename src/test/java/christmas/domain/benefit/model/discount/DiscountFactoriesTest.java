package christmas.domain.benefit.model.discount;

import christmas.domain.benefit.constance.BenefitConst;
import christmas.domain.benefit.model.Benefit;
import christmas.domain.date.model.PromotionDay;
import christmas.domain.menu.model.Menu;
import christmas.domain.menu.model.MenuAndCount;
import christmas.domain.menu.model.collection.OrderSheet;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("할인 적용 테스트")
class DiscountFactoriesTest {
    OrderSheet emptySheet = new OrderSheet(List.of());
    OrderSheet containSheet = new OrderSheet(
            List.of(
                    new MenuAndCount(Menu.CHAMPAGNE, 5),
                    new MenuAndCount(Menu.CHOCOLATE_CAKE, 3)
            ));

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
            //given
            PromotionDay day = PromotionDay.from(date);

            //when & then
            Assertions.assertThat(DiscountFactories.of(day, emptySheet))
                    .contains(new Discount(DiscountType.D_DAY, discountPrice));
        }

        @ParameterizedTest(name = "{0}일에는 D-day 할인이 적용되지 않는다.")
        @ValueSource(ints = {26, 29, 31})
        void 할인_미적용_테스트(int date) {
            //given
            PromotionDay day = PromotionDay.from(date);

            //when
            List<Benefit> discounts = DiscountFactories.of(day, emptySheet);

            //then
            discounts.stream()
                    .map(benefit -> (Discount) benefit)
                    .forEach(
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
            //given
            PromotionDay day = PromotionDay.from(20);
            OrderSheet orderSheet = new OrderSheet(
                    List.of(
                            new MenuAndCount(Menu.CHAMPAGNE, 5),
                            new MenuAndCount(Menu.CHOCOLATE_CAKE, dessertAmount)
                    ));

            //when & then
            Assertions.assertThat(day.isWeekDay()).isTrue();
            Assertions.assertThat(DiscountFactories.of(day, orderSheet))
                    .contains(new Discount(DiscountType.WEEKDAY, discountPrice));
        }

        @Test
        @DisplayName("주말에는 평일 할인이 적용되지 않는다.")
        void 주말엔_평일할인_미적용_테스트() {
            //given
            PromotionDay day = PromotionDay.from(2);
            OrderSheet orderSheet = new OrderSheet(
                    List.of(
                            new MenuAndCount(Menu.CHAMPAGNE, 5),
                            new MenuAndCount(Menu.CHOCOLATE_CAKE, 5)
                    ));

            //when
            List<Benefit> discounts = DiscountFactories.of(day, orderSheet);

            //then
            Assertions.assertThat(day.isWeekDay()).isFalse();
            discounts.stream()
                    .map(benefit -> (Discount) benefit)
                    .forEach(
                            discount -> Assertions.assertThat(discount.getType())
                                    .isNotEqualTo(DiscountType.WEEKDAY)
                    );
        }

        @Test
        @DisplayName("디저트가 없으면 평일 할인이 적용되지 않는다.")
        void 평일할인_미적용_테스트() {
            //given
            PromotionDay day = PromotionDay.from(20);
            OrderSheet orderSheet = new OrderSheet(
                    List.of(
                            new MenuAndCount(Menu.CHAMPAGNE, 5)
                    ));

            //when
            List<Benefit> discounts = DiscountFactories.of(day, orderSheet);

            //then
            Assertions.assertThat(day.isWeekDay()).isTrue();
            discounts.stream()
                    .map(benefit -> (Discount) benefit)
                    .forEach(
                            discount -> Assertions.assertThat(discount.getType())
                                    .isNotEqualTo(DiscountType.WEEKDAY)
                    );
        }
    }

    @Nested
    @DisplayName("주말 할인 테스트")
    class 주말_할인_테스트 {

        @ParameterizedTest(name = "주말에 메인 메뉴를 {0}개 시키면 주말 할인이 {1}원 적용된다.")
        @CsvSource(textBlock = """
                1, 2023
                2, 4046
                3, 6069
                """)
        void 주말할인_적용_테스트(int mainAmount, int discountPrice) {
            //given
            PromotionDay day = PromotionDay.from(2);
            OrderSheet orderSheet = new OrderSheet(
                    List.of(
                            new MenuAndCount(Menu.CHAMPAGNE, 5),
                            new MenuAndCount(Menu.CHOCOLATE_CAKE, 2),
                            new MenuAndCount(Menu.T_BONE_STEAK, mainAmount)
                    ));

            //when&then
            Assertions.assertThat(day.isWeekend()).isTrue();
            Assertions.assertThat(DiscountFactories.of(day, orderSheet))
                    .contains(new Discount(DiscountType.WEEKEND, discountPrice));
        }

        @Test
        @DisplayName("평일에는 주말 할인이 적용되지 않는다.")
        void 평일_주말할인_미적용_테스트() {
            //given
            PromotionDay day = PromotionDay.from(20);
            OrderSheet orderSheet = new OrderSheet(
                    List.of(
                            new MenuAndCount(Menu.CHAMPAGNE, 5),
                            new MenuAndCount(Menu.CHOCOLATE_CAKE, 2),
                            new MenuAndCount(Menu.T_BONE_STEAK, 5)
                    ));

            //when
            List<Benefit> discounts = DiscountFactories.of(day, orderSheet);

            //then
            Assertions.assertThat(day.isWeekend()).isFalse();
            discounts.stream()
                    .map(benefit -> (Discount) benefit)
                    .forEach(
                            discount -> Assertions.assertThat(discount.getType())
                                    .isNotEqualTo(DiscountType.WEEKEND)
                    );
        }

        @Test
        @DisplayName("메인메뉴가 없으면 주말에도 주말 할인이 적용되지 않는다.")
        void 주말할인_미적용_테스트() {
            //given
            PromotionDay day = PromotionDay.from(2);
            OrderSheet orderSheet = new OrderSheet(
                    List.of(
                            new MenuAndCount(Menu.CHAMPAGNE, 5)
                    ));

            //when
            List<Benefit> discounts = DiscountFactories.of(day, orderSheet);

            //then
            Assertions.assertThat(day.isWeekend()).isTrue();
            discounts.stream()
                    .map(benefit -> (Discount) benefit)
                    .forEach(
                            discount -> Assertions.assertThat(discount.getType())
                                    .isNotEqualTo(DiscountType.WEEKEND)
                    );
        }
    }

    @Nested
    @DisplayName("특별 할인 테스트")
    class 특별_할인_테스트 {

        @ParameterizedTest(name = "{0}일에는 특별 할인이 적용된다.")
        @ValueSource(ints = {3, 10, 17, 24, 25, 31})
        void 특별할인_적용_테스트(int date) {
            //given
            PromotionDay day = PromotionDay.from(date);

            //when&then
            Assertions.assertThat(day.isSpecialDay()).isTrue();
            Assertions.assertThat(DiscountFactories.of(day, containSheet))
                    .contains(new Discount(DiscountType.SPECIAL, BenefitConst.SPECIAL_DISCOUNT_PRICE));
        }

        @ParameterizedTest(name = "{0}일에는 특별 할인이 적용되지 않는다.")
        @ValueSource(ints = {1, 2, 4, 9, 11, 26, 30})
        void 특별할인_미적용_테스트(int date) {
            //given
            PromotionDay day = PromotionDay.from(date);

            //when
            List<Benefit> discounts = DiscountFactories.of(day, containSheet);

            //then
            Assertions.assertThat(day.isSpecialDay()).isFalse();
            discounts.stream()
                    .map(benefit -> (Discount) benefit)
                    .forEach(
                            discount -> Assertions.assertThat(discount.getType())
                                    .isNotEqualTo(DiscountType.SPECIAL)
                    );
        }
    }

    @Nested
    @DisplayName("전체 할인 테스트")
    class 전체_할인_테스트 {

        static Stream<Arguments> DateOrdersAndDiscounts() {
            return Stream.of(
                    //평일, 스페셜, D-day
                    Arguments.of(
                            3,
                            List.of(
                                    new MenuAndCount(Menu.ICE_CREAM, 3)
                            ),
                            List.of(
                                    new Discount(DiscountType.WEEKDAY, 6069),
                                    new Discount(DiscountType.SPECIAL, 1000),
                                    new Discount(DiscountType.D_DAY, 1200)
                            )
                    ),
                    //주말, D-day
                    Arguments.of(
                            2,
                            List.of(
                                    new MenuAndCount(Menu.T_BONE_STEAK, 2)
                            ),
                            List.of(
                                    new Discount(DiscountType.WEEKEND, 4046),
                                    new Discount(DiscountType.D_DAY, 1100)
                            )
                    ),
                    //평일, 스페셜
                    Arguments.of(
                            31,
                            List.of(
                                    new MenuAndCount(Menu.CHOCOLATE_CAKE, 2)
                            ),
                            List.of(
                                    new Discount(DiscountType.WEEKDAY, 4046),
                                    new Discount(DiscountType.SPECIAL, 1000)
                            )
                    ),
                    //D-day, 평일, 스페셜
                    Arguments.of(
                            25,
                            List.of(
                                    new MenuAndCount(Menu.ICE_CREAM, 2)
                            ),
                            List.of(
                                    new Discount(DiscountType.WEEKDAY, 4046),
                                    new Discount(DiscountType.D_DAY, 3400),
                                    new Discount(DiscountType.SPECIAL, 1000)
                            )
                    )
            );
        }

        @ParameterizedTest
        @MethodSource("DateOrdersAndDiscounts")
        void 전체_할인_테스트(int date, List<MenuAndCount> orders, List<Discount> expectedDiscounts) {
            PromotionDay day = PromotionDay.from(date);
            OrderSheet orderSheet = new OrderSheet(orders);

            List<Benefit> discounts = DiscountFactories.of(day, orderSheet);

            Assertions.assertThat(discounts.size())
                    .isEqualTo(expectedDiscounts.size());
            discounts.stream()
                    .map(benefit -> (Discount) benefit)
                    .forEach(discount ->
                            Assertions.assertThat(expectedDiscounts).contains(discount)
                    );
        }
    }
}