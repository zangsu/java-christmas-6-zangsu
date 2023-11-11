package christmas.date.model;

import christmas.constance.Const;
import christmas.constance.DateConst;
import christmas.constance.PromotionException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

/**
 * 프로모션에서 사용할 날짜 정보입니다.
 * <p>
 * 날짜 정보와, 달력의 별이 표시되어 있는 스페셜 데이인지의 정보를 가지고 있습니다.
 * </p>
 * <p>
 * 비즈니스 로직에 필요한 기능들 (평일/주말인지의 여부 확인, 크리스마스까지 남은 날짜 확인, 스페셜 데이인지 확인)을 제공합니다.
 * </p>
 */
public class PromotionDay {

    /**
     * 프로모션 기준의 주말 정보를 가집니다.
     */
    private static final Set<DayOfWeek> weekend = Set.of(
            DayOfWeek.FRIDAY, DayOfWeek.SATURDAY
    );

    /**
     * 프로모션 기준의 스페셜 데이들을 가집니다.
     */
    private static final Set<Integer> specialDay = Set.of(3, 10, 17, 24, 25, 31);

    private static final LocalDate christmas = LocalDate.of(2023, 12, 25);

    /**
     * 날짜 정보를 기억합니다.
     */
    private final LocalDate localDate;

    private PromotionDay(LocalDate localDate) {
        this.localDate = localDate;
    }

    public static PromotionDay from(int date) {
        validateDate(date);
        LocalDate localDate = LocalDate.of(DateConst.YEAR, DateConst.MONTH, date);
        return new PromotionDay(localDate);
    }

    private static void validateDate(int date){
        if(date < DateConst.DECEMBER_DATE_START || date > DateConst.DECEMBER_DATE_END){
            throw PromotionException.INVALID_DATE.makeException();
        }
    }

    /**
     * 해당 날짜가 주말인지 확인합니다.
     *
     * @return true - 해당 날짜가 주말인 경우 <br> false - 해당 날짜가 평일인 경우
     */
    public boolean isWeekend() {
        return weekend.contains(localDate.getDayOfWeek());
    }

    /**
     * 해당 날짜가 평일인지 확인합니다.
     *
     * @return true - 해당 날짜가 평일인 경우 <br> false - 해당 날짜가 주말인 경우
     */
    public boolean isWeekDay() {
        return !isWeekend();
    }


    /**
     * 해당 날짜가 스페셜 데이 인지 확인합니다.
     *
     * @return true - 해당 날짜가 스페셜 데이인 경우 <br> false - 해당 날짜가 스페셜 데이가 아닌 경우
     */
    public boolean isSpecialDay() {
        int day = this.localDate.getDayOfMonth();
        return specialDay.contains(day);
    }

    /**
     * 크리스마스까지 남은 날을 구합니다.
     *
     * @return 양수 - 크리스마스 이전의 날인 경우 <br> 0 - 크리스마스 당일인 경우 <br> 음수 - 크리스마스 이후의 날일 경우
     */
    public int getDDayFromXMax() {
        return christmas.compareTo(this.localDate);
    }
}
