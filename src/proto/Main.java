package proto;

public class Main {

    /**
     * A program belépési pontja.
     * @param args a belépési paraméter.
     */
    public static void main(String[] args) {
        Controller controller = new Controller();

        if(args.length > 0 && args[0].equals("test")) {
            controller.startTesting(args);
        } else {
            controller.startGame();
        }
    }
}
