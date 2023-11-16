package christmas.domain.menu.model;

import java.util.Objects;

public class MenuAndCount {
    private final Menu menu;
    private final int count;

    public MenuAndCount(Menu menu, int count) {
        this.menu = menu;
        this.count = count;
    }

    public String getName() {
        return menu.getName();
    }

    public int getCount() {
        return count;
    }

    public boolean isTypeOf(MenuType type){
        return menu.isTypeOf(type);
    }

    public int getTotalPrice() {
        return menu.getPrice() * count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuAndCount that = (MenuAndCount) o;
        return count == that.count && menu == that.menu;
    }

    @Override
    public int hashCode() {
        return Objects.hash(menu, count);
    }
}
