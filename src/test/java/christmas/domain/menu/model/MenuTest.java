package christmas.domain.menu.model;

import christmas.exception.PromotionException;
import christmas.domain.menu.model.Menu;
import java.util.Arrays;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuTest {

    @Nested
    @DisplayName("객체 생성 테스트")
    class 객체_생성_테스트{

        @ParameterizedTest(name = "\"{0}\" 이름을 사용해 {1} 객체가 생성된다.")
        @MethodSource("nameAndMenu")
        void 생성_성공(String inputName, Menu menu){;
            Assertions.assertThat(Menu.from(inputName))
                    .isEqualTo(menu);
        }

        static Stream<Arguments> nameAndMenu(){
            return Arrays.stream(Menu.values())
                    .map(menu -> Arguments.of(menu.getName(), menu));
        }

        @ParameterizedTest(name = "존재하지 않는 메뉴인 \"{0}\"를 사용하면 예외가 발생한다.")
        @ValueSource(strings = {"황금올리브", "고등어회", "자몽허니블랙티"})
        void 생성_실패(String menuName){
            Assertions.assertThatIllegalArgumentException()
                    .isThrownBy(() -> Menu.from(menuName))
                    .withMessage(PromotionException.INVALID_ORDER.getMessage());
        }

        @ParameterizedTest(name = "잘못된 이름인 \"{0}\"을 사용하면 예외가 발생한다.")
        @ValueSource(strings = {"티본-스테이크", "시저셀러드", "해산물 파스타"})
        void 올바르지_않은_형식(String inputName){
            Assertions.assertThatIllegalArgumentException()
                    .isThrownBy(() -> Menu.from(inputName))
                    .withMessage(PromotionException.INVALID_ORDER.getMessage());
        }
    }
}