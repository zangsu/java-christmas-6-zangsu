package christmas.view;

import christmas.domain.badge.model.Badge;
import christmas.domain.benefit.model.Benefit;
import christmas.domain.benefit.model.Benefits;
import christmas.domain.menu.model.MenuAndCount;
import christmas.domain.menu.model.collection.OrderSheet;
import christmas.view.constance.ViewConst;
import christmas.view.io.Printer;
import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Function;

public class OutputView {
    private static final DecimalFormat DECIMAL_FORMAT_PRICE = new DecimalFormat("###,##0");
    Printer printer = new Printer();

    public void sayHello() {
        printer.printMessage(ViewConst.HELLO_MESSAGE);
    }

    public void printException(IllegalArgumentException e) {
        printer.printMessage(e.getMessage());
    }

    public void printBenefitTitle(int date) {
        printer.printMessageUsingFormat(ViewConst.FORMAT_BENEFIT_TITLE, date);
        printer.printEmptyLine();
    }

    public void printOrderMenu(OrderSheet orderSheet) {
        printer.printMessage(ViewConst.TITLE_ORDER_MENU);
        printListUsingFormat(ViewConst.FORMAT_ORDER_MENU,
                orderSheet.getOrderSheet(),
                menuAndCount -> new Object[]{menuAndCount.getName(), menuAndCount.getCount()});
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
        printListUsingFormat(ViewConst.FORMAT_ORDER_MENU,
                gifts,
                gift -> new Object[]{gift.getName(), gift.getCount()});
        printer.printEmptyLine();
    }

    public void printBenefits(Benefits benefits) {
        printer.printMessage(ViewConst.TITLE_BENEFIT_LIST);
        printListUsingFormat(ViewConst.FORMAT_BENEFIT,
                benefits.getBenefits(),
                (Benefit benefit) -> new Object[]{
                        benefit.getDescription(),
                        getMoneyFormat(benefit.getBenefitPrice())
                });
        printer.printEmptyLine();
    }

    public void printTotalBenefitPrice(Benefits benefits) {
        int totalPrice = benefits.getTotalPrice();
        printer.printMessage(ViewConst.TITLE_BENEFIT_PRICE);
        printer.printMessageUsingFormat(ViewConst.FORMAT_PRICE,
                getMoneyFormat(totalPrice));
        printer.printEmptyLine();
    }

    public void printDiscountedPrice(int discountedPrice) {
        printer.printMessage(ViewConst.TITLE_TOTAL_PRICE_WITH_DISCOUNT);
        printer.printMessageUsingFormat(ViewConst.FORMAT_PRICE,
                getMoneyFormat(discountedPrice));
        printer.printEmptyLine();
    }

    public void printBadge(Badge badge) {
        printer.printMessage(ViewConst.TITLE_BADGE);
        printer.printMessage(badge.getName());
    }

    private String getMoneyFormat(int price) {
        return DECIMAL_FORMAT_PRICE.format(price);
    }

    private <T> void printListUsingFormat(String format,
                                          List<T> instances,
                                          Function<T, Object[]> argsExtractor) {
        if (instances.isEmpty()) {
            printer.printMessage(ViewConst.NONE);
            return;
        }

        instances.forEach(instance ->
                printer.printMessageUsingFormat(format, argsExtractor.apply(instance))
        );
    }
}

