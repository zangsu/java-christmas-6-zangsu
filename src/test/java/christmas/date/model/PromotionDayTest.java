package christmas.date.model;

import christmas.constance.ExceptionMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PromotionDayTest {

    @Nested
    @DisplayName("PromotionDay 객체 생성 테스트")
    class 객체_생성_테스트 {
        @ParameterizedTest(name = "12월 {0}일의 객체는 정상적으로 생성 된다.")
        @ValueSource(ints = {1, 10, 20, 31})
        public void 객체_정상_생성_테스트(int date) {

            Assertions.assertThatNoException()
                    .isThrownBy(() -> PromotionDay.from(date));
        }

        @ParameterizedTest(name = "12월 {0}일의 객체는 생성을 시도하면 예외가 발생한다.")
        @ValueSource(ints = {0, 32, -1})
        public void 객체_생성_예외_테스트(int date) {

            Assertions.assertThatIllegalArgumentException()
                    .isThrownBy(() -> PromotionDay.from(date))
                    .withMessage(ExceptionMessage.INVALID_DATE_NUMBER.getMessage());
        }
    }

    @Nested
    @DisplayName("D-day 기능 테스트")
    class D_day_기능_테스트 {
        @ParameterizedTest(name = "12월 {0}일의 D-day는 {1}일 입니다.")
        @CsvSource({
                "1, 24",
                "5, 20",
                "10, 15",
                "20, 5",
                "25, 0",
                "31, -6"
        })
        void D_day_기능_테스트(int date, int d_day) {
            PromotionDay promotionDay = PromotionDay.from(date);
            Assertions.assertThat(promotionDay.getDDayFromXMax())
                    .isEqualTo(d_day);
        }

    }

    @Nested
    @DisplayName("평일/주말 기능 테스트")
    class 평일_주말_테스트 {
        @ParameterizedTest(name = "12월 {0}은 평일 (일 ~ 목) 입니다.")
        @ValueSource(ints = {3, 4, 5, 6, 7, 26, 27, 28, 31})
        void 평일_테스트(int date) {
            PromotionDay promotionDay = PromotionDay.from(date);
            Assertions.assertThat(promotionDay.isWeekDay())
                    .isTrue();
            Assertions.assertThat(promotionDay.isWeekend())
                    .isFalse();
        }

        @ParameterizedTest(name = "12월 {0}일은 주말 (금, 토) 입니다.")
        @ValueSource(ints = {1, 2, 8, 9, 15, 16, 22, 23, 29, 30})
        void 주말_테스트(int date) {
            PromotionDay promotionDay = PromotionDay.from(date);
            Assertions.assertThat(promotionDay.isWeekDay())
                    .isFalse();
            Assertions.assertThat(promotionDay.isWeekend())
                    .isTrue();
        }
    }

    @Nested
    @DisplayName("스페셜 데이 테스트")
    class 스페셜_데이_테스트{

        @ParameterizedTest(name = "12월 {0}일은 스페셜데이 입니다.")
        @ValueSource(ints = {3, 10, 17, 24, 25, 31})
        void specialDayTest(int date){
            PromotionDay specialDay = PromotionDay.from(date);
            Assertions.assertThat(specialDay.isSpecialDay())
                    .isTrue();
        }

        @ParameterizedTest(name = "12월 {0}일은 스페셜데이가 아닙니다..")
        @ValueSource(ints = {1, 2, 4, 5, 9, 23, 26, 30})
        void noSpecialDayTest(int date){
            PromotionDay noSpecialDay = PromotionDay.from(date);
            Assertions.assertThat(noSpecialDay.isSpecialDay())
                    .isFalse();
        }
    }
}