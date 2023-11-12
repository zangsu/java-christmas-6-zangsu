package christmas.benefit.model.discount;

import java.util.Objects;

public class Discount {
    private final DiscountType type;
    private final int price;

    public Discount(DiscountType type, int price) {
        this.type = type;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Discount discount = (Discount) o;
        return price == discount.price && type == discount.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, price);
    }

    public DiscountType getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }
}
