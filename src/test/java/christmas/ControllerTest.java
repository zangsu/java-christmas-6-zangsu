package christmas;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;

import camp.nextstep.edu.missionutils.test.NsTest;
import christmas.exception.RetryHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("통합 테스트")
class ControllerTest extends NsTest {

    Controller controller = new Controller(new RetryHandler());

    @Nested
    @DisplayName("날짜 입력 테스트")
    class 날짜_테스트{
        @ParameterizedTest(name = "{0}일은 올바른 입력으로 프로그램이 정상 동작한다.")
        @ValueSource(strings = {"1", "5", "27", "31"})
        void 올바른_날짜_테스트(String date){
            assertSimpleTest(() -> {
                run(date, "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
                Assertions.assertThat(output())
                        .contains("<주문 메뉴>");
            });
        }

        @ParameterizedTest(name = "{0}일은 잘못된 입력으로 오류 메시지를 출력한다.")
        @ValueSource(strings = {"-1", "a", "32", "0", "1 1", ""})
        void 잘못된_날짜_테스트(String date){
            assertSimpleTest(() -> {
                runException(date);
                Assertions.assertThat(output())
                        .contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
            });
        }
    }

    @Nested
    @DisplayName("주문 입력 테스트")
    class 주문_테스트{
        @ParameterizedTest(name = "\"{0}\" 은 올바른 주문이다.")
        @ValueSource(strings = {
                "양송이수프-1,바비큐립-3,초코케이크-1",
                "양송이수프-1",
                "티본스테이크-20",
                "초코케이크-19,티본스테이크-1"
        })
        void 올바른_주문_테스트(String orders){
            assertSimpleTest(() -> {
                run("2", orders);
                Assertions.assertThat(output())
                        .contains("<주문 메뉴>");
            });
        }

        @ParameterizedTest(name = "\"{0}\" 은 없는 메뉴의 주문으로 오류 메시지를 출력한다.")
        @ValueSource(strings = {"황금올리브-1", "양송이수프-1,황금올리브-2"})
        void 없는_메뉴_테스트(String order){
            assertSimpleTest(() -> {
                runException("2", order);
                Assertions.assertThat(output())
                        .contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            });
        }

        @ParameterizedTest(name = "\"{0}\" 은 메뉴의 잘못된 이름으로 오류 메시지를 출력한다.")
        @ValueSource(strings = {"양송이스프-1", "티본 스테이크-1", "TAPAS-1"})
        void 메뉴_오타_테스트(String order){
            assertSimpleTest(() -> {
                runException("2", order);
                Assertions.assertThat(output())
                        .contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            });
        }

        @ParameterizedTest(name = "\"{0}\" 은 메뉴의 개수가 잘못 입력되어 오류 메시지를 출력한다.")
        @ValueSource(strings = {"티본스테이크-a", "티본스테이크-0"})
        void 메뉴_개수_오류_테스트(String order){
            assertSimpleTest(() -> {
                runException("2", order);
                Assertions.assertThat(output())
                        .contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            });
        }

        @ParameterizedTest(name = "\"{0}\" 은 메뉴가 20개 이상 주문되어 오류 메시지를 출력한다.")
        @ValueSource(strings = {"티본스테이크-21", "아이스크림-19,티본스테이크-2"})
        void 메뉴_최대주문개수_초과_테스트(String order){
            assertSimpleTest(() -> {
                runException("2", order);
                Assertions.assertThat(output())
                        .contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            });
        }

        @ParameterizedTest(name = "\"{0}\" 은 음료로만 구성된 주문으로 오류 메시지를 출력한다.")
        @ValueSource(strings = {"레드와인-1", "레드와인-1,샴페인-1"})
        void 음료만_주문_테스트(String order){
            assertSimpleTest(() -> {
                runException("2", order);
                Assertions.assertThat(output())
                        .contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            });
        }

        @ParameterizedTest(name = "\"{0}\" 은 입력 형식이 잘못되어 오류 메시지를 출력한다.")
        @ValueSource(strings = {"티본스테이크-1,티본스테이크-1", "아이스크림-1,티본스테이크-2,아이스크림-1"})
        void 중복_주문_테스트(String order){
            assertSimpleTest(() -> {
                runException("2", order);
                Assertions.assertThat(output())
                        .contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            });
        }

        @ParameterizedTest(name = "\"{0}\" 은 잘못된 형식 주문으로 오류 메시지를 출력한다.")
        @ValueSource(strings = {
                "양송이수프-1, 바비큐립-3, 초코케이크-1",
                "양송이수프-1,",
                "",
                "양송이수프:1,바비큐립:3"
        })
        void 입력_형식_테스트(String order){
            assertSimpleTest(() -> {
                runException("2", order);
                Assertions.assertThat(output())
                        .contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            });
        }
    }
    @Override
    protected void runMain() {
        controller.run();
    }
}