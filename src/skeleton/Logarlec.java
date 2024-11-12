package skeleton;

/**
 * A játékban megtalálható logarléc objektumot reprezentálja.
 * Különleges felelőssége, hogy oktatóknak ne engedje a felvételét, hallgatóknak pedig igen.
 */
public class Logarlec extends Item {
    /**
     * A paraméterében megkapja, hogy melyik entitás szeretné felvenni.
     * Ezen entitáson meghívja a tryForLogarLec metódust,
     * hogy eldöntse, hogy az entitás felveheti-e az objektumot vagy sem.
     *
     * @param entity A metódust meghívó entitást.
     * @return Itt a tárgy felvehetőséget jelzi; azaz igaz, ha fel lehet venni és hamis, ha nem.
     */
    public boolean pickup(Entity entity) {
        SimpleLogger.logMethodCall("Logarlec.pickup(entity: Entity)");
        boolean ret = entity.tryForLogarlec();
        SimpleLogger.logMethodReturn("Logarlec.pickup(entity: Entity)", ret);
        return ret;
    }
}
