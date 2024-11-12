package skeleton;

/**
 * A hallgatót reprezentáló osztály.
 */
public class Student extends Entity{

    /**
     * A hívott karaktert megpróbálja megölni ezen metódus. Ez sikeres amennyiben nem tud védekezni.
     */
    public void kill(){
        SimpleLogger.logMethodCall("Student.kill()");

        if(SimpleLogger.getYesOrNoInput("Részeg-e a hallgató? (drunkFor nagyobb-e, mint 0)")){

            SimpleLogger.logMethodReturn("Student.kill()");
            return;
        }

        for (Item i : items) {
            if(i.protectAgainstProf()) {
                SimpleLogger.logMethodReturn("Student.kill()");
                return;
            }
        }

        room.addItems(items);
        SimpleLogger.logIndentedLine("Clear items of Student");
        items.clear();

        room.removeEntity(this);
        game.entityDied(this);

        SimpleLogger.logMethodReturn("Student.kill()");
    }

    /**
     *  Ezen függvény  a paraméterben kapott tárolt tárgy aktiválási folyamatát indítja el.
     * @param item aktiválni kivánt tárgy
     */
    public void activateItem(Item item){
        SimpleLogger.logMethodCall("Student.activateItem(item: Item)");

        item.activate(this);

        SimpleLogger.logMethodReturn("Student.activateItem(item: Item)");
    }

    /**
     * Ezen metódus abban az estben hívódik, amennyiben az adott hallgató logarléc típusú tárgyat próbál felvenni. A student osztály
     * által ezen felüldefiniált verziója a metódusnak, a játék megnyeréséhez vezet.
     * @return sikerül-e a logarlécet felvenni ((hallgató esetén, azaz itt mindig true))
     */
    public boolean tryForLogarlec(){
        SimpleLogger.logMethodCall("Student.tryForLogarlec()");
        game.gameWon();

        SimpleLogger.logMethodReturn("Student.tryForLogarlec()",true);
        return true;
    }

    /**
     * Reagál, a paraméterben kapott entity bemutatkozására.
     * @param entity A találkozott entitás.
     */
    public void meet(Entity entity) {
        SimpleLogger.logMethodCall("Student.meet(entity: Entity)");
        entity.meet(this);
        SimpleLogger.logMethodReturn("Student.meet(entity: Entity)");
    }

    /**
     * Ezen függvény bemutatja a paraméterben kapott oktatónak önmagát.
     * @param prof oktató amelynek bemutatkozik az adott hallgató
     */
    public void meet(Prof prof){
        SimpleLogger.logMethodCall("Student.meet(prof: Prof)");
        prof.meet(this);
        SimpleLogger.logMethodReturn("Student.meet(prof: Prof)");
    }

    /**
     * Az adott hallgatót megmérgezését végzi ezen függvény, amennyiben nincs védekezési lehetőseég.
     * @return sikeres-e a mérgezés
     * @param room a szoba emyleből a mérgező
     */
    public boolean toxicate(Room room){
        SimpleLogger.logMethodCall("Student.toxicate(room: Room)");

        for (Item i : items) {
            if(i.protectAgainstToxicRoom()){
                SimpleLogger.logMethodReturn("Student.toxicate(room: Room)", false);
                return false;
            }
        }

        setStunnedFor(3);
        room.addItems(items);
        SimpleLogger.logIndentedLine("Clear item list of Student");
        items.clear();

        SimpleLogger.logMethodReturn("Student.toxicate()",true);
        return true;
    }

    /**
     * Ezen metódus, az adott karaktert megpróbálja lebénítani.
     * Ezen implementácójában a metódus visszatérési értéke hamis, mivel a hallgatók ilyen
     * formában nem béníthatók.
     * @return sikeres-e a bénítás ((mindig false ebben az esetben))
     */
    public boolean immobilize(){
        SimpleLogger.logMethodCall("Student.immobilize()");
        SimpleLogger.logMethodReturn("Student.immobilize()",false);
        return false;
    }

    /**
     * Ezen függvény a paraméterben kapott két tárgyat próbálja összepárosítani.
     * @param item1 azon tárgy, amelyre meghívódik az összepárosítás
     * @param item2 azon tárgy ameylet tovább adjuk paraméterként
     */
    public void linkItems(Item item1, Item item2){
        SimpleLogger.logMethodCall("Student.linkItems(item1: Item,item2: Item)");

        item1.link(item2);

        SimpleLogger.logMethodReturn("Student.linkItems(item1: Item,item2: Item)");
    }

    /**
     * Ezen függvény az adott hallgatót részeggé teszi 3 értékkel.
     */
    public void makeStudentDrunk(){
        SimpleLogger.logMethodCall("Student.makeStudentDrunk()");

        //drunkFor=3;
        SimpleLogger.logMethodReturn("Student.makeStudentDrunk()");
    }

    /**
     * Ezen metódus adott időegységenként szükséges feladatok elvégzését látja el az adott
     * karakteren. Ezen implementációja esetén, ha szükséges a ittaségi attribútum értékét csökkenti.
     */
    @Override
    public void tick() {
        SimpleLogger.logMethodCall("Student.tick()");
        if(SimpleLogger.getYesOrNoInput("Jelenleg benult az Hallgato?")) {
            SimpleLogger.logIndentedLine("Decrement stunnedFor");
            //stunnedFor--;
        }
        if(SimpleLogger.getYesOrNoInput("Jelenleg reszeg az Hallgato?")) {
            SimpleLogger.logIndentedLine("Decrement drunkFor");
            //drunkFor--;
        }
        SimpleLogger.logMethodReturn("Student.tick()");
    }


    /**
     * A paraméterben kapott értékre állítja a hallgató ittasági állapotát.
     * @param value a beállítandó érték
     */
    public void setDrunkFor(int value){
        SimpleLogger.logMethodCall("Student.setDrunkFor(value: int)");

        //drunkFor=value;
        SimpleLogger.logMethodReturn("Student.setDrunkFor(value: int)");
    }
}
