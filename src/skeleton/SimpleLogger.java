package skeleton;

import java.util.Scanner;

/**
 * A szkeleton alkalmazás üzeneteinek naplózásáért és a felhasználónak feltett eldöntendő kérdések megejelenítéséért felel.
 */
class SimpleLogger {
    /**
     * Nem példányosítható osztály.
     */

    private SimpleLogger() {}

    /**
     * Egy indentálási szinten megjelenő space-ek.
     */
    private static final String spaces = "    ";

    /**
     * Indentálási szint.
     */
    private static int indentationLevel = 0;
    /**
     * A kapott üzenetet indentálva a standard kimenetre írja.
     * A végén új sor karaktert is tesz.
     *
     * @param message Megjelenítendő üzenet.
     */
    public static void logIndentedLine(String message) {
        System.out.println(spaces.repeat(indentationLevel) + message);
    }

    /**
     * A kapott üzenetet indentálva a standard kimenetre írja.
     *
     * @param message Megjelenítendő szöveg.
     */
    public static void logIndented(String message) {
        System.out.print(spaces.repeat(indentationLevel) + message);
    }

    /**
     * Metódushívást naplóz a megfelelő indentálási szinten.
     * Növeli az indentálási szintet.
     *
     * @param methodName A naplózandó metódus neve.
     */
    public static void logMethodCall(String methodName) {
        logIndentedLine(methodName + " called ");
        indentationLevel++;
    }

    /**
     * Metódusvisszatérést naplóz a megfelelő indentálási szinten.
     * Csökkenti az indentálási szintet.
     *
     * @param methodName A naplózandó metódus neve.
     */
    public static void logMethodReturn(String methodName) {
        indentationLevel--;
        logIndentedLine(methodName + " returned ");
    }

    /**
     * Metódusvisszatérést naplóz visszatérési értékkel a megfelelő indentálási szinten.
     * Csökkenti az indentálási szintet.
     *
     * @param methodName A naplózandó metódus neve.
     * @param retval Visszatérési érték.
     */
    public static <T> void logMethodReturn(String methodName, T retval) {
        indentationLevel--;
        logIndentedLine(methodName + " returned " + retval);
    }

    /**
     * Eldöntentdő kérdést ír ki. Addig kéri be a választ, amíg helyes formátumú választ nem kap.
     * Formátumok: igen, i, nem, n és ezek a karakterek bárhogyan nagybetűsítve.
     *
     * @param message Maga az eldöntendő kérdés.
     * @return Igen/Nem, azaz Igaz/Hamis választ ad adja vissza.
     */
    public static boolean getYesOrNoInput(String message) {
        logIndented(message + " [I/N] ");
        String input;
        while (true) {
            input = Skeleton.scanner.nextLine().toLowerCase();
            if (input.equals("i") || input.equals("igen")) return true;
            else if (input.equals("n") || input.equals("nem")) return false;
            logIndented("Invalid input, try again! ");
        }
    }
}