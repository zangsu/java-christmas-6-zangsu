package christmas.domain.menu.model;

/**
 * 각 메뉴의 주문 정보를 나타내기 위한 클래스 입니다.
 */
public class MenuAndCount {
    private final Menu menu;
    private final int count;

    public MenuAndCount(Menu menu, int count) {
        this.menu = menu;
        this.count = count;
    }

    public Menu getMenu() {
        return menu;
    }

    public String getName(){
        return menu.getName();
    }

    public int getCount() {
        return count;
    }

    public MenuType getMenuType(){
        return menu.getType();
    }

    public int getTotalPrice(){
        return menu.getPrice() * count;
    }
}
