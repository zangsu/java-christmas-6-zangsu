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

    public List<MenuAndCount> getGifts() {
        return benefits.stream()
                .filter(benefit -> benefit.getClass().equals(Gifts.class))
                .map(gift -> ((Gifts) gift).getGifts())
                .flatMap(List::stream)
                .toList();
    }

    public List<Benefit> getBenefits() {
        return Collections.unmodifiableList(benefits);
    }

    public int getTotalPrice() {
        return benefits.stream()
                .mapToInt(Benefit::getBenefitPrice)
                .sum();
    }

    public int getDiscountPrice() {
        return benefits.stream()
                .filter(benefit -> benefit.getClass().equals(Discount.class))
                .mapToInt(Benefit::getBenefitPrice)
                .sum();
    }
}
