package christmas.domain.benefit.model.gift;

import christmas.domain.benefit.constance.BenefitConst;
import christmas.domain.benefit.model.Benefit;
import christmas.domain.menu.model.MenuAndCount;
import christmas.domain.menu.model.collection.OrderSheet;
import java.util.List;

public class Gifts implements Benefit {

    private final OrderSheet gifts;

    Gifts(List<MenuAndCount> gifts) {
        this.gifts = new OrderSheet(gifts);
    }

    @Override
    public String getDescription() {
        return BenefitConst.GIFT_DESCRIPTION;
    }

    @Override
    public int getBenefitPrice() {
        return -gifts.getTotalPrice();
    }

    public List<MenuAndCount> getGifts() {
        return gifts.getOrderSheet();
    }
}
