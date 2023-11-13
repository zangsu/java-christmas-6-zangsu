package christmas.domain.benefit.model.gift;

import christmas.domain.benefit.constance.BenefitConst;
import christmas.domain.benefit.model.Benefit;
import christmas.domain.benefit.model.BenefitFactory;
import christmas.domain.date.model.PromotionDay;
import christmas.domain.menu.model.Menu;
import christmas.domain.menu.model.MenuAndCount;
import christmas.domain.menu.model.collection.OrderSheet;
import java.util.Arrays;
import java.util.List;

public enum GiftFactories implements BenefitFactory {

    CHAMPAGNE(Menu.CHAMPAGNE, 1) {
        @Override
        public boolean canApply(PromotionDay promotionDay, OrderSheet orderSheet) {
            return orderSheet.getTotalPrice() >= BenefitConst.MIN_PRICE_FOR_CHAMPAGNE;
        }

        @Override
        public Benefit generate(PromotionDay promotionDay, OrderSheet orderSheet) {
            return new Gifts(List.of(new MenuAndCount(getMenu(), getCount())));
        }
    };

    private final Menu menu;
    private final int count;

    GiftFactories(Menu menu, int count) {
        this.menu = menu;
        this.count = count;
    }

    public static List<Benefit> of(PromotionDay promotionDay, OrderSheet orderSheet){
        return Arrays.stream(GiftFactories.values())
                .filter(factory -> factory.canApply(promotionDay, orderSheet))
                .map(factory -> factory.generate(promotionDay, orderSheet))
                .toList();
    }
    @Override
    abstract public boolean canApply(PromotionDay promotionDay, OrderSheet orderSheet);

    @Override
    abstract public Benefit generate(PromotionDay promotionDay, OrderSheet orderSheet);

    Menu getMenu() {
        return menu;
    }

    int getCount() {
        return count;
    }
}
