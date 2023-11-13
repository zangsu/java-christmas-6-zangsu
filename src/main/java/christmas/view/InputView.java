package christmas.view;

import christmas.exception.PromotionException;
import christmas.view.constance.ViewConst;
import christmas.view.io.Printer;
import christmas.view.io.Reader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputView {
    Printer printer = new Printer();
    Reader reader = new Reader();

    Pattern orderMenuRegex = Pattern.compile("[가-힣]*-\\d*");

    public int getVisitDate() {
        printer.printMessage(ViewConst.DATE_REQUEST_MESSAGE);
        try {
            return reader.getInteger();
        } catch (NumberFormatException e) {
            throw PromotionException.INVALID_DATE.makeException();
        }
    }

    public List<String> getOrders() {
        printer.printMessage(ViewConst.ORDER_REQUEST_MESSAGE);
        try {
            List<String> orders = reader.getStringsByDelimiter(ViewConst.ORDERS_DELIMITER);
            validOrdersPattern(orders);
            return orders;
        } catch (IllegalArgumentException e) {
            throw PromotionException.INVALID_ORDER.makeException();
        }
    }

    private void validOrdersPattern(List<String> orders) {
        boolean allMatch = orders.stream()
                .map(orderMenuRegex::matcher)
                .allMatch(Matcher::matches);
        if (!allMatch) {
            throw PromotionException.INVALID_ORDER.makeException();
        }
    }
}
