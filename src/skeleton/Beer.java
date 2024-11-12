package skeleton;

/**
 * A sör egy aktiválással hasznosítható tárgy.
 * Aktiválásával a hallgató védettségre tehet szert, (részeggé válással) az oktatók támadásával szemben.
 */
public class Beer extends Item {
    /**
     * Aktiválja a tárgyat, és a tárgy health-ét megfelelő értékre állítja.
     * Meghívja a hallgató védetté („részeggé”) válásához szükséges függvényeket.
     *
     * @param student A metódust meghívó hallgató.
     */
    public void activate(Student student) {
        SimpleLogger.logMethodCall("Beer.activate(student: Student)");

        if(SimpleLogger.getYesOrNoInput("Volt mar aktivalva ez a Sor?")) {
            SimpleLogger.logMethodReturn("Beer.activate(student: Student)");
            return;
        }

        student.makeStudentDrunk();
        SimpleLogger.logIndentedLine("Decrement health of Beer");
        // decrementHealth();

        SimpleLogger.logMethodReturn("Beer.activate(student: Student)");
    }
}
