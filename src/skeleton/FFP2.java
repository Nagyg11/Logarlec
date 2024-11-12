package skeleton;

/**
 * Azon tárgy, amely a hallgatókat képes megvédeni mérges gázzal teli szoba hatásától.
 */
public class FFP2 extends Item {
    /**
     * Megvizsgálja ezen metódus, hogy az adott FFP2-es maszk, az adott állapotában képes-e megvédeni
     * egy hallgatót mérges gázzal teli szoba hatásától, és amennyiben igen igaz, ha nem hamis értékkel
     * tér vissza a metódus.
     *
     * @return Megvédi-e a hívóját a mérgező szoba támadásától.
     */
    public boolean protectAgainstToxicRoom() {
        SimpleLogger.logMethodCall("FFP2.protectAgainstToxicRoom()");

        if(!SimpleLogger.getYesOrNoInput("Maradt meg eletpontja ennek az FFP2 maszknak?")) {
            SimpleLogger.logMethodReturn("FFP2.protectAgainstToxicRoom()", false);
            return false;
        }

        SimpleLogger.logIndentedLine("Decrement health of FFP2");
        SimpleLogger.logMethodReturn("FFP2.protectAgainstToxicRoom()", true);
        return true;
    }
}
