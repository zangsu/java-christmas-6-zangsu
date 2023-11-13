package christmas.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RegexTest {
    @Nested
    @DisplayName("\"한글-숫자\" 형태의 정규 표현식 테스트")
    class 정규표현식_테스트{
        Pattern pattern = Pattern.compile("[가-힣]*-\\d*");

        @ParameterizedTest(name = "{0}의 입력은 \"한글-숫자\" 형식입니다.")
        @ValueSource(strings = {"바비큐립-1", "토마토파스타-3", "아이스크림-12"})
        void successTest(String input){
            Matcher matcher = pattern.matcher(input);
            Assertions.assertThat(matcher.matches())
                    .isTrue();
        }

        @ParameterizedTest(name = "{0}의 입력은 \"한글-숫자\" 형식이 아닙니다.")
        @ValueSource(strings = {"steak-1", "토마토 파스타-3", "아이스크림:12"})
        void failTest(String input){
            Matcher matcher = pattern.matcher(input);
            Assertions.assertThat(matcher.matches())
                    .isFalse();
        }
    }
}
