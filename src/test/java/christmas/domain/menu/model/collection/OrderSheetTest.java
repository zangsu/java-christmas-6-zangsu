package christmas.domain.menu.model.collection;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OrderSheetTest {

    @Nested
    @DisplayName("총_금액_테스트")
    class 총_금액_테스트 {

        static Stream<Arguments> ordersAndPrice() {
            return Stream.of(
                    Arguments.of(new String[]{
                                    "티본스테이크-1", "바비큐립-1", "초코케이크-2", "제로콜라-1"
                            }, 142_000
                    ),
                    Arguments.of(new String[]{
                                    "양송이수프-2", "타파스-3", "해산물파스타-1", "샴페인-1"
                            }, 88_500
                    )
            );
        }

        @ParameterizedTest(name = "OrderSheet가 정상적인 총 금액을 계산한다.")
        @MethodSource("ordersAndPrice")
        void priceTest(String[] inputOrders, int expectedPrice) {

            Orders orders = new Orders(inputOrders);
            OrderSheet orderSheet = orders.getOrderSheet();

            Assertions.assertThat(orderSheet.getTotalPrice())
                    .isEqualTo(expectedPrice);
        }
    }
}