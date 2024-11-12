package skeleton;

/**
 * A játékban szereplő TVSZ objektumok reprezentálásért felel.
 * A felhasználóját megvédi az oktatók támadásaitól.
 */
public class TVSZ extends Item {
    /**
     * Arra szolgál, hogy a tárgy megvédje a hallgatót az oktatók támadásaitól.
     * Ha van élete megvédi a hívóját.
     *
     * @return Megvédi-e a hívóját az oktató támadásától.
     */
    public boolean protectAgainstProf() {
        SimpleLogger.logMethodCall("TVSZ.protectAgainstProf()");

        if(!SimpleLogger.getYesOrNoInput("Maradt meg eletpontja ennek az TVSZ-nek?")) {
            SimpleLogger.logMethodReturn("TVSZ.protectAgainstProf()", false);
            return false;
        }

        SimpleLogger.logIndentedLine("Decrement health of TVSZ");
        SimpleLogger.logMethodReturn("TVSZ.protectAgainstProf()", true);
        return true;
    }
}
