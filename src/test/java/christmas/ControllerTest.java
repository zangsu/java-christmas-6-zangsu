package christmas;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;

import camp.nextstep.edu.missionutils.test.NsTest;
import christmas.exception.handler.RetryHandler;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("통합 테스트")
class ControllerTest extends NsTest {

    Controller controller = new Controller(new RetryHandler());

    @Override
    protected void runMain() {
        controller.run();
    }

    @Nested
    @DisplayName("날짜 입력 테스트")
    class 날짜_테스트 {
        @ParameterizedTest(name = "{0}일은 올바른 입력으로 프로그램이 정상 동작한다.")
        @ValueSource(strings = {"1", "5", "27", "31"})
        void 올바른_날짜_테스트(String date) {
            assertSimpleTest(() -> {
                run(date, "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
                Assertions.assertThat(output())
                        .contains("<주문 메뉴>");
            });
        }

        @ParameterizedTest(name = "{0}일은 잘못된 입력으로 오류 메시지를 출력한다.")
        @ValueSource(strings = {"-1", "a", "32", "0", "1 1", "\n", " "})
        void 잘못된_날짜_테스트(String date) {
            assertSimpleTest(() -> {
                System.out.println("\"" + date + "\"");
                runException(date);
                Assertions.assertThat(output())
                        .contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
            });
        }
    }

    @Nested
    @DisplayName("주문 입력 테스트")
    class 주문_테스트 {
        @ParameterizedTest(name = "\"{0}\" 은 올바른 주문이다.")
        @ValueSource(strings = {
                "양송이수프-1,바비큐립-3,초코케이크-1",
                "양송이수프-1",
                "티본스테이크-20",
                "초코케이크-19,티본스테이크-1"
        })
        void 올바른_주문_테스트(String orders) {
            assertSimpleTest(() -> {
                run("2", orders);
                Assertions.assertThat(output())
                        .contains("<주문 메뉴>");
            });
        }

        @ParameterizedTest(name = "\"{0}\" 은 없는 메뉴의 주문으로 오류 메시지를 출력한다.")
        @ValueSource(strings = {"황금올리브-1", "양송이수프-1,황금올리브-2"})
        void 없는_메뉴_테스트(String order) {
            assertSimpleTest(() -> {
                runException("2", order);
                Assertions.assertThat(output())
                        .contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            });
        }

        @ParameterizedTest(name = "\"{0}\" 은 메뉴의 잘못된 이름으로 오류 메시지를 출력한다.")
        @ValueSource(strings = {"양송이스프-1", "티본 스테이크-1", "TAPAS-1"})
        void 메뉴_오타_테스트(String order) {
            assertSimpleTest(() -> {
                runException("2", order);
                Assertions.assertThat(output())
                        .contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            });
        }

        @ParameterizedTest(name = "\"{0}\" 은 메뉴의 개수가 잘못 입력되어 오류 메시지를 출력한다.")
        @ValueSource(strings = {"티본스테이크-a", "티본스테이크-0"})
        void 메뉴_개수_오류_테스트(String order) {
            assertSimpleTest(() -> {
                runException("2", order);
                Assertions.assertThat(output())
                        .contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            });
        }

        @ParameterizedTest(name = "\"{0}\" 은 메뉴가 20개 이상 주문되어 오류 메시지를 출력한다.")
        @ValueSource(strings = {"티본스테이크-21", "아이스크림-19,티본스테이크-2"})
        void 메뉴_최대주문개수_초과_테스트(String order) {
            assertSimpleTest(() -> {
                runException("2", order);
                Assertions.assertThat(output())
                        .contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            });
        }

        @ParameterizedTest(name = "\"{0}\" 은 음료로만 구성된 주문으로 오류 메시지를 출력한다.")
        @ValueSource(strings = {"레드와인-1", "레드와인-1,샴페인-1"})
        void 음료만_주문_테스트(String order) {
            assertSimpleTest(() -> {
                runException("2", order);
                Assertions.assertThat(output())
                        .contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            });
        }

        @ParameterizedTest(name = "\"{0}\" 은 입력 형식이 잘못되어 오류 메시지를 출력한다.")
        @ValueSource(strings = {"티본스테이크-1,티본스테이크-1", "아이스크림-1,티본스테이크-2,아이스크림-1"})
        void 중복_주문_테스트(String order) {
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
                ",양송이수프-1",
                " ",
                //"", -> 테스트코드에서는 검증 불가, 아래 테스트 케이스로 대체
                "양송이수프:1,바비큐립:3"
        })
        void 입력_형식_테스트(String order) {
            assertSimpleTest(() -> {
                runException("2", order);
                Assertions.assertThat(output())
                        .contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            });
        }

    }

    @DisplayName("정상 동작 결과 테스트")
    @Nested
    class 정상_동작_테스트 {

        static Stream<Arguments> weekendTest() {
            return Stream.of(
                    Arguments.of("2",
                            "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1",
                            new String[]{
                                    "티본스테이크 1개",
                                    "바비큐립 1개",
                                    "초코케이크 2개",
                                    "제로콜라 1개",
                                    "142,000원",
                                    "샴페인 1개",
                                    "크리스마스 디데이 할인: -1,100원",
                                    "주말 할인: -4,046원",
                                    "증정 이벤트: -25,000원",
                                    "-30,146원",
                                    "136,854원",
                                    "산타"
                            }
                    ),
                    Arguments.of("23",
                            "티본스테이크-1,바비큐립-2,해산물파스타-1,샴페인-1,제로콜라-1",
                            new String[]{
                                    "티본스테이크 1개",
                                    "바비큐립 2개",
                                    "해산물파스타 1개",
                                    "샴페인 1개",
                                    "제로콜라 1개",
                                    "226,000원",
                                    "샴페인 1개",
                                    "크리스마스 디데이 할인: -3,200원",
                                    "주말 할인: -8,092원",
                                    "증정 이벤트: -25,000원",
                                    "-36,292원",
                                    "214,708원",
                                    "산타"
                            }
                    ),
                    Arguments.of("16",
                            "아이스크림-1",
                            new String[]{
                                    "아이스크림 1개",
                                    "5,000원",
                                    "없음",
                                    "0원",
                                    "5,000원",
                                    "없음"
                            }
                    ),
                    Arguments.of("16",
                            "아이스크림-2",
                            new String[]{
                                    "아이스크림 2개",
                                    "10,000원",
                                    "크리스마스 디데이 할인: -2,500원",
                                    "-2,500원",
                                    "7,500원",
                                    "없음"
                            }
                    ),
                    Arguments.of("30",
                            "타파스-1,크리스마스파스타-2,제로콜라-1",
                            new String[]{
                                    "타파스 1개",
                                    "크리스마스파스타 2개",
                                    "제로콜라 1개",
                                    "58,500원",
                                    "주말 할인: -4,046원",
                                    "-4,046원",
                                    "54,454원",
                                    "없음"
                            }
                    )

            );
        }

        static Stream<Arguments> weekdaySpecialTest() {
            return Stream.of(
                    Arguments.of("10",
                            "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1",
                            new String[]{
                                    "티본스테이크 1개",
                                    "바비큐립 1개",
                                    "초코케이크 2개",
                                    "제로콜라 1개",
                                    "142,000원",
                                    "샴페인 1개",
                                    "특별 할인: -1,000원",
                                    "크리스마스 디데이 할인: -1,900원",
                                    "평일 할인: -4,046원",
                                    "증정 이벤트: -25,000원",
                                    "-31,946원",
                                    "135,054원",
                                    "산타"
                            }
                    ),
                    Arguments.of("25",
                            "시저샐러드-2,티본스테이크-1,크리스마스파스타-1,초코케이크-1,레드와인-1",
                            new String[]{
                                    "티본스테이크 1개",
                                    "시저샐러드 2개",
                                    "크리스마스파스타 1개",
                                    "초코케이크 1개",
                                    "레드와인 1개",
                                    "171,000원",
                                    "샴페인 1개",
                                    "특별 할인: -1,000원",
                                    "크리스마스 디데이 할인: -3,400원",
                                    "평일 할인: -2,023원",
                                    "증정 이벤트: -25,000원",
                                    "-31,423원",
                                    "164,577원",
                                    "산타"
                            }
                    ),
                    Arguments.of("31",
                            "양송이수프-1,바비큐립-1,티본스테이크-1",
                            new String[]{
                                    "양송이수프 1개",
                                    "바비큐립 1개",
                                    "티본스테이크 1개",
                                    "115,000원",
                                    "특별 할인: -1,000원",
                                    "-1,000원",
                                    "114,000원",
                                    "없음"
                            }
                    )
            );
        }

        static Stream<Arguments> weekdayNormalTest() {
            return Stream.of(
                    Arguments.of("19",
                            "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1",
                            new String[]{
                                    "티본스테이크 1개",
                                    "바비큐립 1개",
                                    "초코케이크 2개",
                                    "제로콜라 1개",
                                    "142,000원",
                                    "샴페인 1개",
                                    "크리스마스 디데이 할인: -2,800원",
                                    "평일 할인: -4,046원",
                                    "증정 이벤트: -25,000원",
                                    "-31,846원",
                                    "135,154원",
                                    "산타"
                            }
                    ),
                    Arguments.of("26",
                            "시저샐러드-2,티본스테이크-1,크리스마스파스타-1,초코케이크-1,레드와인-1",
                            new String[]{
                                    "티본스테이크 1개",
                                    "시저샐러드 2개",
                                    "크리스마스파스타 1개",
                                    "초코케이크 1개",
                                    "레드와인 1개",
                                    "171,000원",
                                    "샴페인 1개",
                                    "평일 할인: -2,023원",
                                    "증정 이벤트: -25,000원",
                                    "-27,023원",
                                    "168,977원",
                                    "산타"
                            }
                    ),
                    Arguments.of("28",
                            "양송이수프-1,제로콜라-1",
                            new String[]{
                                    "양송이수프 1개",
                                    "제로콜라 1개",
                                    "9,000원",
                                    "없음",
                                    "0원",
                                    "9,000원",
                                    "없음"
                            }
                    ),
                    Arguments.of("28",
                            "초코케이크-5",
                            new String[]{
                                    "초코케이크 5개",
                                    "75,000원",
                                    "없음",
                                    "평일 할인: -10,115원",
                                    "-10,115원",
                                    "64,885원",
                                    "트리"
                            }
                    )
            );
        }

        @ParameterizedTest(name = "{0}일 테스트")
        @DisplayName("주말 테스트")
        @MethodSource("weekendTest")
        void 주말_테스트(String date, String orders, String[] expectedResult) {
            runAndCheckResult(date, orders, expectedResult);
        }

        @ParameterizedTest(name = "{0}일 테스트")
        @DisplayName("평일/스페셜 테스트")
        @MethodSource("weekdaySpecialTest")
        void 평일_스페셜_테스트(String date, String orders, String[] expectedResult) {
            runAndCheckResult(date, orders, expectedResult);
        }

        @ParameterizedTest(name = "{0}일 테스트")
        @DisplayName("평일/일반 테스트")
        @MethodSource("weekdayNormalTest")
        void 평일_일반_테스트(String date, String orders, String[] expectedResult) {
            runAndCheckResult(date, orders, expectedResult);
        }

        private void runAndCheckResult(String date, String orders, String... expectedResult) {
            assertSimpleTest(
                    () -> {
                        run(date, orders);
                        Assertions.assertThat(output())
                                .contains(expectedResult);
                    }
            );
        }
    }
}