package christmas.domain.date;

import christmas.domain.date.model.PromotionDay;

/**
 * 날짜와 관련된 책임을 처리하는 클래스
 */
public class DateService {

    /**
     * 정적 팩토리 메서드인 Promotion.from()을 통해 {@link PromotionDay}를 생성 후 반환
     * @param date - 생성할 날짜 정보
     * @return 생성된 {@link PromotionDay}
     */
    public PromotionDay getPromotionDay(int date){
        return PromotionDay.from(date);
    }
}
