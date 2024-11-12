package skeleton;

/**
 * A játékban megtalálható tranzisztorokat reprezentáló osztály.
 * A tranzisztorok összekapcsolásáért, aktiválásért és felvételéért felel.
 */
public class Transistor extends Item {
    // active : bool
    /**
     * Tárol egy szobát. Ha aktív és van párja, akkor a párjának aktiválása esetén
     * az aktiválást kezdeményező hallgató ebbe a szobába fog kerülni.
     */
    private Room room = null;
    /**
     * A tranzisztor (és párjának) állapotától függően eltérően viselkedik.
     * Ha a tranzisztornak nincs párja, akkor azonnal visszatér.
     * Ha van párja, de az nem aktív, akkor beregisztrál egy szobát és aktívvá állítja a tranzisztort,
     * majd lehelyezi azt a szobában. Ha van párja és az aktív, akkor ez a tranzisztor kikerül
     * a hallgató tárgyai közül a hallgató szobájába és a párjában beregisztrált szobához
     * teleportálja felhasználóját, a hallgatót.
     *
     * @param student A metódust meghívó hallgató.
     */
    public void activate(Student student) {
        SimpleLogger.logMethodCall("Transistor.activate(student: Student)");
        if (pair == null) {
            SimpleLogger.logMethodReturn("Transistor.activate(student: Student)");
            return;
        }

        Transistor pair = (Transistor) this.pair;
        if(SimpleLogger.getYesOrNoInput("Aktiv a tranzisztor parja?")) {
            boolean accepted = pair.teleport(student);
            if(accepted) {
                pair = null; // KEll?
                setPair(null);
            }
        } else {
            room = student.getRoom();
            setRoom(room);
            setActive(true);
            student.drop(this);
        }

        SimpleLogger.logMethodReturn("Transistor.activate(student: Student)");
    }

    /**
     * Ha a tranzisztor aktív, akkor hamissal tér vissza, ezzel megtagadva a felvételt.
     * Ellenkező esetben igazzal tér vissza.
     *
     * @param entity A metódust meghívó entitás.
     * @return Fel lehet-e venni a tranzisztort.
     */
    public boolean pickup(Entity entity) {
        SimpleLogger.logMethodCall("Transistor.pickup(entity: Entity");

        if(SimpleLogger.getYesOrNoInput("Aktiv ez a Tranzisztor?")) {
            SimpleLogger.logMethodReturn("Transistor.pickup(entity: Entity", false);
            return false;
        }

        SimpleLogger.logMethodReturn("Transistor.pickup(entity: Entity", true);
        return true;
    }

    /**
     * Meghívja a paraméterként kapott tárgyon a link(transistor) fejlécű
     * metódust, ha az tranzisztor, akkor a link(transistor) igazzal tér vissza, ilyenkor a link
     * hívója is beregisztrálja magában a másik tárgyat, hiszen az egy tranzisztor.
     *
     * @param item Az a tárgy, amivel össze szeretnénk kapcsolni a hívott tranzisztort.
     */
    public void link(Item item) {
        SimpleLogger.logMethodCall("Transistor.link(item: Item)");

        boolean linked = item.link(this);
        if(linked) {
            setPair(item);
        }

        SimpleLogger.logMethodReturn("Transistor.link(item: Item)");
    }

    /**
     * Beregisztrálja magában a kapott tranzisztort egy párként, majd igazzal tér vissza.
     *
     * @param transistor Az a tranzisztor amivel össze szeretnénk kapcsolni ezt a tranzisztort.
     * @return Sikeres volt-e a paraméterként kapott tranzisztor beregisztrálása.
     */
    public boolean link(Transistor transistor) {
        SimpleLogger.logMethodCall("Transistor.link(transistor: Transistor)");

        setPair(transistor);

        SimpleLogger.logMethodReturn("Transistor.link(transistor: Transistor)", true);
        return true;
    }

    /**
     * Megpróbálja áttolni a kapott hallgatót a regisztrált szobába.
     * Ha ez sikerül, akkor elejteti a hallgatóval a párját és átállítja a szobákat, majd deaktiválja magát és lekapcsolja a párját.
     * Különben nem tesz semmit, a kapcsolat megmarad, a hallgató a régi szobában marad.
     *
     * @param student A tranzisztor párjának az aktiválását kiváltó hallgató.
     * @return Sikerült-e a teleport.
     */
    boolean teleport(Student student) {
        SimpleLogger.logMethodCall("Transistor.teleport(student: Student)");
        boolean accepted = room.acceptEntity(student);
        if(accepted) {
            // Move student
            student.drop(pair);
            student.getRoom().removeEntity(student);
            room.addEntity(student);
            student.setRoom(room);
            // Deactivate and unlink
            setActive(false);
            setPair(null);
        }
        SimpleLogger.logMethodReturn("Transistor.teleport(student: Student)", accepted);
        return accepted;
    }

    public void setRoom(Room room) {
        SimpleLogger.logMethodCall("Transistor.setRoom(room: Room)");
        this.room = room;
        SimpleLogger.logMethodReturn("Transistor.setRoom(room: Room)");
    }

    public void setActive(boolean active) {
        SimpleLogger.logMethodCall("Item.setActive(active: boolean)");
        //this.active = active;
        SimpleLogger.logMethodReturn("Item.setActive(active: boolean)");
    }
}
