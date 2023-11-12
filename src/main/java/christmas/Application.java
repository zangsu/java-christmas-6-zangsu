package christmas;

import christmas.exception.RetryHandler;

public class Application {
    public static void main(String[] args) {
        Controller controller = new Controller(new RetryHandler());
        controller.run();
    }
}
