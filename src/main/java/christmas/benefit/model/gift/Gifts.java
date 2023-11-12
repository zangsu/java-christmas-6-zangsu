package christmas.benefit.model.gift;

import christmas.benefit.constance.BenefitConst;
import christmas.benefit.model.Benefit;
import christmas.menu.model.MenuAndCount;
import java.util.Collections;
import java.util.List;

/**
 * 증정품 목록을 저장하기 위한 클래스 입니다.
 * <p>증정품은 판매 메뉴와 증정 개수로 구성되어 있습니다.</p>
 */
public class Gifts implements Benefit {

    private final List<MenuAndCount> gifts;

    public Gifts(List<MenuAndCount> gifts) {
        this.gifts = gifts;
    }

    /**
     * 증정 이벤트 설명을 반환합니다.
     * @return
     */
    @Override
    public String getDescription() {
        return BenefitConst.GIFT_DESCRIPTION;
    }

    /**
     * 전체 증정품의 총 합계 금액을 반환합니다.
     * @return 증정된 메뉴의 총 합계 금액
     */
    @Override
    public int getPrice() {
        return gifts.stream()
                .mapToInt(MenuAndCount::getTotalPrice)
                .sum();
    }

    // TODO : OrderSheet 와 통합 방법 고민 필요
    public List<MenuAndCount> getGifts() {
        return Collections.unmodifiableList(gifts);
    }
}
