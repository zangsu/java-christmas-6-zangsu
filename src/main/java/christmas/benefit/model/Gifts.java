package christmas.benefit.model;

import christmas.benefit.constance.BenefitConst;
import christmas.menu.model.MenuAndCount;
import java.util.Collections;
import java.util.List;

public class Gifts implements Benefit{

    private final List<MenuAndCount> gifts;

    public Gifts(List<MenuAndCount> gifts) {
        this.gifts = gifts;
    }

    @Override
    public String getName() {
        return BenefitConst.GiftDescription;
    }

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
