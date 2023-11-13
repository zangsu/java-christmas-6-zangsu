package christmas.domain.benefit.model;

import christmas.domain.benefit.model.discount.Discount;
import christmas.domain.benefit.model.gift.Gifts;
import christmas.domain.menu.model.MenuAndCount;
import java.util.Collections;
import java.util.List;

public class Benefits {
    private final List<Benefit> benefits;

    public Benefits(List<Benefit> benefits) {
        this.benefits = benefits;
    }

    /**
     * 증정품 내역을 리턴합니다.
     *
     * @return
     */
    public List<MenuAndCount> getGifts() {
        return benefits.stream()
                .filter(benefit -> benefit.getClass().equals(Gifts.class))
                .map(gift -> ((Gifts) gift).getGifts())
                .flatMap(List::stream)
                .toList();
    }

    /**
     * 모든 혜택 내역을 리턴합니다.
     *
     * @return 적용된 모든 혜택 내역
     */
    public List<Benefit> getBenefits() {
        return Collections.unmodifiableList(benefits);
    }

    /**
     * 전체 혜택 금액을 리턴합니다.
     *
     * @return 모든 혜택 금액의 총 합
     */
    public int getTotalPrice() {
        return benefits.stream()
                .mapToInt(Benefit::getPrice)
                .sum();
    }

    /**
     * 전체 할인 금액을 리턴합니다.
     *
     * @return 혜택 중 할인에 해당하는 금액들의 총 합
     */
    public int getDiscountPrice() {
        return benefits.stream()
                .filter(benefit -> benefit.getClass().equals(Discount.class))
                .mapToInt(Benefit::getPrice)
                .sum();
    }
}
