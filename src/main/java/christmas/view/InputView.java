package christmas.view;

import christmas.view.constance.ViewConst;
import christmas.view.io.Printer;
import christmas.view.io.Reader;
import java.util.List;

public class InputView {
    Printer printer = new Printer();
    Reader reader = new Reader();

    public int getVisitDate(){
        printer.printMessage(ViewConst.DATE_REQUEST_MESSAGE);
        return reader.getInteger();
    }

    public List<String> getOrders(){
        printer.printMessage(ViewConst.ORDER_REQUEST_MESSAGE);
        return reader.getStringsByDelimiter(ViewConst.ORDERS_DELIMITER);
    }
}
