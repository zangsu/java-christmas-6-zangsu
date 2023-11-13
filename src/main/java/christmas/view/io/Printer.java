package christmas.view.io;

public class Printer {
    public void printEmptyLine() {
        System.out.println();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printMessageUsingFormat(String format, Object... objects) {
        System.out.printf(format, objects);
        System.out.println();
    }
}
