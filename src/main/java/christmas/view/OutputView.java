package christmas.view;

import christmas.domain.badge.model.Badge;
import christmas.domain.benefit.model.Benefits;
import christmas.domain.date.model.PromotionDay;
import christmas.domain.menu.model.MenuAndCount;
import christmas.domain.menu.model.collection.OrderSheet;
import christmas.view.constance.ViewConst;
import christmas.view.io.Printer;
import java.text.DecimalFormat;
import java.util.List;

//TODO : 없음 출력
public class OutputView {
    private static final DecimalFormat DECIMAL_FORMAT_PRICE = new DecimalFormat("###,##0");
    Printer printer = new Printer();

    public void sayHello() {
        printer.printMessage(ViewConst.HELLO_MESSAGE);
    }

    public void printException(IllegalArgumentException e){
        printer.printMessage(e.getMessage());
        printer.printEmptyLine();
    }

    public void printBenefitTitle(int date) {
        printer.printMessageUsingFormat(ViewConst.FORMAT_BENEFIT_TITLE, date);
        printer.printEmptyLine();
    }

    public void printOrderMenu(OrderSheet orderSheet) {
        printer.printMessage(ViewConst.TITLE_ORDER_MENU);
        orderSheet.getOrderSheet().forEach(orderMenu ->
                printer.printMessageUsingFormat(ViewConst.FORMAT_ORDER_MENU,
                        orderMenu.getName(), orderMenu.getCount())
        );
        printer.printEmptyLine();
    }

    public void printTotalPriceNoDiscount(OrderSheet orderSheet) {
        int totalPrice = orderSheet.getTotalPrice();
        printer.printMessage(ViewConst.TITLE_TOTAL_PRICE_NO_DISCOUNT);
        printer.printMessageUsingFormat(ViewConst.FORMAT_PRICE,
                getMoneyFormat(totalPrice));
        printer.printEmptyLine();
    }

    public void printGifts(Benefits benefits) {
        List<MenuAndCount> gifts = benefits.getGifts();
        printer.printMessage(ViewConst.TITLE_GIFT_LIST);
        gifts.forEach(gift ->
                printer.printMessageUsingFormat(ViewConst.FORMAT_ORDER_MENU,
                        gift.getName(), gift.getCount()));
        printer.printEmptyLine();
    }

    public void printBenefits(Benefits benefits){
        printer.printMessage(ViewConst.TITLE_BENEFIT_LIST);
        benefits.getBenefits().forEach(benefit ->
                printer.printMessageUsingFormat(ViewConst.FORMAT_BENEFIT,
                        benefit.getDescription(),
                        getMoneyFormat(benefit.getPrice()))
        );
        printer.printEmptyLine();
    }

    public void printTotalBenefitPrice(Benefits benefits){
        int totalPrice = benefits.getTotalPrice();
        printer.printMessage(ViewConst.TITLE_BENEFIT_PRICE);
        printer.printMessageUsingFormat(ViewConst.FORMAT_BENEFIT_PRICE,
                getMoneyFormat(totalPrice));
        printer.printEmptyLine();
    }

    public void printDiscountedPrice(int discountedPrice){
        printer.printMessage(ViewConst.TITLE_TOTAL_PRICE_WITH_DISCOUNT);
        printer.printMessageUsingFormat(ViewConst.FORMAT_PRICE,
                getMoneyFormat(discountedPrice));
    }

    public void printBadge(Badge badge){
        printer.printMessage(ViewConst.TITLE_BADGE);
        printer.printMessage(badge.name());
    }

    private String getMoneyFormat(int price) {
        return DECIMAL_FORMAT_PRICE.format(price);
    }
}

