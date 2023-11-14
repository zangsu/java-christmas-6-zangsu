package christmas.domain.menu.model.collection;

import christmas.domain.menu.model.collection.Orders;
import christmas.exception.PromotionException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("주문 생성 테스트")
class OrdersTest {

    @Nested
    @DisplayName("Orders 생성 테스트")
    class Orders_생성_테스트 {

        @Test
        @DisplayName("각 메뉴를 한번씩 주문하면 Orders가 정상적으로 생성된다.")
        void 한_번씩_주문() {
            Assertions.assertThatNoException()
                    .isThrownBy(() ->
                            new Orders("티본스테이크-1", "바비큐립-1", "초코케이크-2", "제로콜라-1")
                    );
        }

        @Test
        @DisplayName("아무것도 주문하지 않으면 예외가 발생한다.")
        void 아무것도_주문하지_않음() {
            Assertions.assertThatIllegalArgumentException()
                    .isThrownBy(() ->
                            new Orders()
                    )
                    .withMessage(PromotionException.INVALID_ORDER.getMessage());
        }

        @Test
        @DisplayName("메뉴를 중복 주문하면 예외가 발생한다.")
        void 중복_주문() {
            Assertions.assertThatIllegalArgumentException()
                    .isThrownBy(() ->
                            new Orders("티본스테이크-1", "바비큐립-1", "초코케이크-2", "티본스테이크-1"))
                    .withMessage(PromotionException.INVALID_ORDER.getMessage());
        }

        @Test
        @DisplayName("음료만 주문하면 예외가 발생한다.")
        void 음료만_주문() {
            Assertions.assertThatIllegalArgumentException()
                    .isThrownBy(() ->
                            new Orders("제로콜라-1", "샴페인-2", "레드와인-3"))
                    .withMessage(PromotionException.INVALID_ORDER.getMessage());
        }


    }

    @Nested
    @DisplayName("주문 개수 관련 테스트")
    class 주문_개수_테스트 {
        @Test
        @DisplayName("주문 개수가 0개이면 예외가 발생한다.")
        void 주문_개수가_0개() {
            Assertions.assertThatIllegalArgumentException()
                    .isThrownBy(() ->
                            new Orders("티본스테이크-1", "바비큐립-0", "초코케이크-2"))
                    .withMessage(PromotionException.INVALID_ORDER.getMessage());
        }

        @Test
        @DisplayName("총 주문 개수가 20개 이상이면 예외가 발생한다.")
        void 총_주문_수_초과() {
            Assertions.assertThatIllegalArgumentException()
                    .isThrownBy(() ->
                            new Orders("티본스테이크-10", "바비큐립-8", "초코케이크-3"))
                    .withMessage(PromotionException.INVALID_ORDER.getMessage());
        }

        @DisplayName("총 주문 개수가")
        @ParameterizedTest(name = "{0}개면 정상 동작한다.")
        @ValueSource(ints = {1, 10, 20})
        void 총_주문_개수_테스트(int amount) {
            Assertions.assertThatNoException()
                    .isThrownBy(() ->
                            new Orders("티본스테이크-" + amount));
        }
    }
}