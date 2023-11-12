package christmas;

import christmas.domain.badge.BadgeService;
import christmas.domain.badge.model.Badge;
import christmas.domain.benefit.BenefitService;
import christmas.domain.benefit.model.Benefits;
import christmas.domain.date.DateService;
import christmas.domain.date.model.PromotionDay;
import christmas.domain.menu.MenuService;
import christmas.domain.menu.model.collection.OrderSheet;
import christmas.exception.ExceptionHandler;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.List;
import java.util.function.Supplier;
import javax.print.DocFlavor.STRING;

public class Controller {
    private final DateService dateService = new DateService();
    private final MenuService menuService = new MenuService();
    private final BenefitService benefitService = new BenefitService();
    private final BadgeService badgeService = new BadgeService();

    private final InputView inputView = new InputView();

    private final OutputView outputView = new OutputView();
    private final ExceptionHandler handler;

    public Controller(ExceptionHandler handler) {
        this.handler = handler;
    }

    public void run(){
        outputView.sayHello();

        PromotionDay promotionDay = getPromotionDay();
        OrderSheet orderSheet = getOrderSheet();
        Benefits benefits = getBenefits(promotionDay, orderSheet);
        Badge badge = getBadge(benefits);

        printResult(promotionDay, orderSheet, benefits, badge);
    }

    private PromotionDay getPromotionDay(){
        return getResultUsingExceptionHandler(() -> {
            int visitDate = inputView.getVisitDate();
            return dateService.getPromotionDay(visitDate);
        });
    }

    private OrderSheet getOrderSheet(){
        return getResultUsingExceptionHandler(() -> {
            List<String> orders = inputView.getOrders();
            return menuService.getOrderSheet(orders.toArray(String[]::new));
        });
    }

    private <T> T getResultUsingExceptionHandler(Supplier<T> logic){
        return handler.get(logic, outputView::printException);
    }

    private Benefits getBenefits(PromotionDay promotionDay, OrderSheet orderSheet){
        return benefitService.getBenefits(promotionDay, orderSheet);
    }

    private Badge getBadge(Benefits benefits) {
        return badgeService.getBadge(benefits);
    }

    private void printResult(PromotionDay promotionDay, OrderSheet orderSheet, Benefits benefits, Badge badge) {
        outputView.printBenefitTitle(promotionDay);
        printOrderSheeet(orderSheet);
        printBenefits(benefits);
        //결제 예상 금액
        printBadge(badge);
    }

    private void printOrderSheeet(OrderSheet orderSheet){
        outputView.printOrderMenu(orderSheet);
        outputView.printTotalPriceNoDiscount(orderSheet);
    }

    private void printBenefits(Benefits benefits){
        outputView.printGifts(benefits);
        outputView.printBenefits(benefits);
        outputView.printTotalBenefitPrice(benefits);
    }

    private void printBadge(Badge badge) {
        outputView.printBadge(badge);
    }
}