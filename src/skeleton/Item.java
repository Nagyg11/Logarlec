package skeleton;

/**
 * A játékban megtalálható tárgyakat reprezentáló osztály.
 * A tárgyak felhasználásáért, összekapcsolásáért, valamint felvételéért felel.
 */
public abstract class Item {
    // health : int
    /**
     * Tárol egy tárgyat, ezzel a tárggyal van összekapcsolva, ha az érvényes objektum.
     */
    protected Item pair = null;

    /**
     * Ebben az implementációban igazzal tér vissza. Arra szolgál, hogy például a logarléc vagy
     * a tranzisztor felüldefiniálja és új viselkedést vezessenek be.
     *
     * @param entity A metódust meghívó entitás.
     * @return Itt a tárgy felvehetőséget jelzi; azaz igaz, ha fel lehet venni és hamis, ha nem.
     */
    public boolean pickup(Entity entity) {
        SimpleLogger.logMethodCall("Item.pickup(entity: Entity)");
        SimpleLogger.logMethodReturn("Item.pickup(entity: Entity)", true);
        return true;
    }

    /**
     * Ez egy üres implementáció, arra szolgál, hogy a leszármazott tárgyak felüldefiniálják,
     * ha parancsra fel lehet őket használni.
     *
     * @param student A hallgató, aki meghívta a metódust.
     */
    public void activate(Student student) {
        SimpleLogger.logMethodCall("Item.activate(student: Student)");
        SimpleLogger.logMethodReturn("Item.activate(student: Student)");
    }

    /**
     * Arra szolgál, hogy a tárgy megvédje a hallgatót a mérgező szoba támadásától.
     * Ebben az implementációban hamissal tér vissza, a leszármazottak, amiknek van ilyen képességük felüldefiniálják.
     *
     * @return A tárgy megvédi-e a hallgatót a mérgező szobától.
     */
    public boolean protectAgainstToxicRoom() {
        SimpleLogger.logMethodCall("Item.protectAgainstToxicRoom()");
        SimpleLogger.logMethodReturn("Item.protectAgainstToxicRoom()", false);
        return false;
    }

    /**
     * Arra szolgál, hogy a tárgy megvédje a hallgatót az oktatók támadásaitól.
     * Ebben az implementációban hamissal tér vissza, a leszármazottak, amiknek van ilyen képességük felüldefiniálják.
     *
     * @return A tárgy megvédi-e a hallgatót az oktató támadásától.
     */
    public boolean protectAgainstProf() {
        SimpleLogger.logMethodCall("Item.protectAgainstProf()");
        SimpleLogger.logMethodReturn("Item.protectAgainstProf()", false);
        return false;
    }

    /**
     * Ebben az implementációban üres. Arra szolgál, hogy felülíríák a leszármazottak, amiket össze lehet kapcsolni.
     *
     * @param item Tárgy, amivel össze szeretnénk kapcsolni a hívott tárgyat.
     */
    public void link(Item item) {
        SimpleLogger.logMethodCall("Item.link(item: Item)");
        SimpleLogger.logMethodReturn("Item.link(item: Item)");
    }

    /**
     * Ebben az implementációban hamissal tér vissza, arra szolgál, hogy a tranzisztor felüldefiniálja.
     * Használata azt biztosítja, hogy tranzisztort csak tranzisztorral lehessen összekapcsolni.
     *
     * @param transistor Transistor, amivel össze szeretnénk kapcsolni a tárgyat.
     * @return Össze sikerült-e kapcsolni a tárgyat a kapott tranzisztorral.
     */
    public boolean link(Transistor transistor) {
        SimpleLogger.logMethodCall("Item.link(transistor: Transistor)");
        SimpleLogger.logMethodReturn("Item.link(transistor: Transistor)", false);
        return false;
    }

    public void setPair(Item pair) {
        SimpleLogger.logMethodCall("Item.setPair(pair: Item)");
        this.pair = pair;
        SimpleLogger.logMethodReturn("Item.setPair(pair: Item)");
    }
}
