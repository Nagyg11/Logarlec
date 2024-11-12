package skeleton;

/**
 * Ez a tárgy az őt felhasználó hallgató szobáját mérgezővé teszi, aktiválás hatására.
 */
public class Camembert extends Item {
    /**
     * Aktiválja a tárgyat, és a tárgy health-ét megfelelő értékre állítja.
     * Meghívja a mérgező szoba létrejövéséhez szükséges függvényeket.
     *
     * @param student A metódust meghívó hallgató.
     */
    public void activate(Student student) {
        SimpleLogger.logMethodCall("Camembert.activate(student: Student)");

        if(SimpleLogger.getYesOrNoInput("Volt mar aktivalva ez a Dobozolt kaposztas camembert?")) {
            SimpleLogger.logMethodReturn("Camembert.activate(student: Student)");
            return;
        }

        Room studentRoom = student.getRoom();
        studentRoom.makeRoomToxic();
        SimpleLogger.logIndentedLine("Decrement health of Camembert");
        // decrementHealth();

        SimpleLogger.logMethodReturn("Camembert.activate(student: Student)");
    }
}
