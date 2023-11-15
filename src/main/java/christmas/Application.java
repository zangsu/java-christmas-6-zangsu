package christmas;

import christmas.exception.handler.RetryHandler;

public class Application {
    public static void main(String[] args) {
        Controller controller = new Controller(new RetryHandler());
        controller.run();
    }
}
