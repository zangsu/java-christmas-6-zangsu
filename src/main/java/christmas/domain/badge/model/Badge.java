package christmas.domain.badge.model;

import christmas.domain.badge.constance.BadgeConst;
import java.util.Arrays;

public enum Badge {
    NONE("없음") {
        @Override
        boolean match(int price) {
            return false;
        }
    },

    STAR("별") {
        @Override
        boolean match(int price) {
            return price >= BadgeConst.STAR_MIN_PRICE
                    && price < BadgeConst.STAR_MAX_PRICE;
        }
    },
    TREE("트리") {
        @Override
        boolean match(int price) {
            return price >= BadgeConst.TREE_MIN_PRICE &&
                    price < BadgeConst.TREE_MAX_PRICE;
        }
    },
    SANTA("산타") {
        @Override
        boolean match(int price) {
            return price >= BadgeConst.SANTA_MIN_PRICE;
        }
    };
    private final String name;

    Badge(String name) {
        this.name = name;
    }

    abstract boolean match(int price);

    public static Badge from(int price){
        return Arrays.stream(Badge.values())
                .filter(badge -> badge.match(price))
                .findFirst()
                .orElse(NONE);
    }
}
