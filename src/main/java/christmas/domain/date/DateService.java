package christmas.domain.date;

import christmas.domain.date.model.PromotionDay;

/**
 * 날짜와 관련된 책임을 처리하는 클래스
 */
public class DateService {

    public PromotionDay getPromotionDay(int date) {
        return PromotionDay.from(date);
    }
}
