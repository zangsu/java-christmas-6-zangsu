package christmas.domain.date.model;

import christmas.domain.date.constance.DateConst;
import christmas.exception.PromotionException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class PromotionDay {

    private static final Set<DayOfWeek> weekend = Set.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);

    private static final Set<Integer> specialDay = Set.of(3, 10, 17, 24, 25, 31);

    private static final LocalDate christmas = LocalDate.of(DateConst.YEAR, DateConst.MONTH, 25);

    private final LocalDate localDate;

    private PromotionDay(LocalDate localDate) {
        this.localDate = localDate;
    }

    public static PromotionDay from(int date) {
        validateDate(date);
        LocalDate localDate = LocalDate.of(DateConst.YEAR, DateConst.MONTH, date);
        return new PromotionDay(localDate);
    }

    private static void validateDate(int date) {
        if (date < DateConst.DECEMBER_DATE_START || date > DateConst.DECEMBER_DATE_END) {
            throw PromotionException.INVALID_DATE.makeException();
        }
    }

    public boolean isWeekend() {
        return weekend.contains(localDate.getDayOfWeek());
    }

    public boolean isWeekDay() {
        return !isWeekend();
    }

    public boolean isSpecialDay() {
        int day = this.localDate.getDayOfMonth();
        return specialDay.contains(day);
    }

    public int getDDayFromXMax() {
        return christmas.compareTo(this.localDate);
    }

    public int getDayFromStart() {
        return getDate() - 1;
    }

    public int getDate() {
        return this.localDate.getDayOfMonth();
    }
}
