package skeleton;

/**
 * A professzort reprezentáló osztály.
 */
public class Prof extends Entity {

    /**
     * Reagál, a paraméterben kapott entity bemutatkozására.
     * @param entity A találkozott entitás.
     */
    public void meet(Entity entity) {
        SimpleLogger.logMethodCall("Prof.meet(entity: Entity)");
        entity.meet(this);
        SimpleLogger.logMethodReturn("Prof.meet(entity: Entity)");
    }

    /**
     * A paraméterben kapott bemutatkozó hallgatóra, meghívja a megöléséhez
     * szükséges függvényt ezen függvény.
     * @param student a hallgató amelyet megtámad
     */
    public void meet(Student student){
        SimpleLogger.logMethodCall("Prof.meet(student: Student)");
        student.kill();
        SimpleLogger.logMethodReturn("Prof.meet(student: Student)");
    }
}
