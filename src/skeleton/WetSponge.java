package skeleton;

/**
 * A szobát, amelyben a hallgató aktiválja ezt a tárgyat nedvessé teszi (letörlődik a tábla).
 */
public class WetSponge extends Item {
    /**
     * Aktiválja az adott szivacsot ezzel, nedvesre állítva szobát,
     * amelyben a paraméterben kapott s hallgató tartózkodik.
     *
     * @param student A metódust meghívó hallgató.
     */
    public void activate(Student student) {
        SimpleLogger.logMethodCall("WetSponge.activate(student: Student)");

        if(SimpleLogger.getYesOrNoInput("Volt mar aktivalva ez a Nedves tablatorlorongy?")) {
            SimpleLogger.logMethodReturn("WetSponge.activate(student: Student)");
            return;
        }

        Room studentRoom = student.getRoom();
        studentRoom.makeRoomWet();
        SimpleLogger.logIndentedLine("Decrement health of WetSponge");
        // decrementHealth();

        SimpleLogger.logMethodReturn("WetSponge.activate(student: Student)");
    }
}
