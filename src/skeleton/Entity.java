package skeleton;

import java.util.ArrayList;

public abstract class  Entity {
    protected Game game;
    protected Room room;

    private final int itemCapacity=5;
    protected ArrayList<Item> items = new ArrayList<>();

    /**
     * A paraméterben kapott "dest" szobába megpróbálja
     * átléptetni, az adott karaktert a paraméterben kapott "door" ajtón keresztül.
     * Azaz ezen függvény úgymond, elindítja az átléptetés folyamatát.
     *
     * @param dest cél szoba, tehát amely szobába át akar lépni a karakter
     * @param door az ajtó, amelyen keresztül át akar menni a célszobába a karakter
     *
     * @return sikeres-e az átkelés
     */
    public boolean move(Room dest, Door door) {
        SimpleLogger.logMethodCall("Entity.move(dest: Room,door: Door)");

        boolean ret = door.letEntityThrough(this, dest);

        if (ret) {
            room.removeEntity(this);
            room = dest;
            dest.addEntity(this);
        }

        SimpleLogger.logMethodReturn("Entity.move(dest: Room,door: Door)", ret);

        return ret;
    }

    /**
     * A paraméterben kapott "item" tárgyat megpróbálja felszedni az adot karakter, azaz a birtokolt tárgyak közé helyezni.
     * Elindítja egy karakter tárgyfelvételének folyamatát.
     *
     * @param item a felvenni kivánt tárgy
     */
    public void pickUp(Item item) {
        SimpleLogger.logMethodCall("Entity.pickUp(Item: item)");

        if (items.size() < itemCapacity) {
            if (room.take(this, item)) {
                addItem(item);
            }
        }

        SimpleLogger.logMethodReturn("Entity.pickUp(Item: item)");
    }


    /**
     * Ezen metódus hatására a paraméterben kapott tárgyat eldobja/lerakja az adott
     * karakter, azaz kikerül a tárolt tárgyai közül és a szobájába kerül.
     *
     * @param item az eldobandó tárgy
     */
    public void drop(Item item) {
        SimpleLogger.logMethodCall("Entity.drop(Item: item)");

        room.addItem(item);
        removeItem(item);

        SimpleLogger.logMethodReturn("Entity.drop(Item: item)");
    }

    /**
     * Ezen metódus abban az esetben hívódik, amennyiben az adott karakter logarléc típusú tárgyat próbál felvenni. Ezen metódus ezen
     * alapimplementációjában a felvétel nem sikeres, így visszatérési értéke hamis (false).
     *
     * @return sikeres-e a logarléc felvétele ((itt mindig hamis alapból))
     */
    public boolean tryForLogarlec() {
        SimpleLogger.logMethodCall("Entity.tryForLogarlec()");
        SimpleLogger.logMethodReturn("Entity.tryForLogarlec()", false);
        return false;
    }

    /**
     *  Ezen metódus a hívott entitást felszólítja, hogy mutatkozzon be a
     * paraméterben kapott  entitásnak.
     * @param entity a karakter amelynek mutatkozzon be ezen entitás
     */
    public void meet(Entity entity) {
        SimpleLogger.logMethodCall("Entity.meet(entity: Entity)");
        SimpleLogger.logMethodReturn("Entity.meet(entity: Entity)");
    }

    public void meet(Prof prof) {
        SimpleLogger.logMethodCall("Entity.meet(prof: Prof)");
        SimpleLogger.logMethodReturn("Entity.meet(prof: Prof)");
    }

    public void meet(Student student) {
        SimpleLogger.logMethodCall("Entity.meet(student: Student)");
        SimpleLogger.logMethodReturn("Entity.meet(student: Student)");
    }

    /**
     * Ezen metódus, az adott karaktert megpróbálja megmérgezni. Ezen
     * alapimplementációban ez mindig sikeres, ezért a visszatérési értéke igaz (true) lesz.
     * @param destRoom a szoba emyleből a mérgező
     * @return sikeres-e a karakter megmérgezése ((itt mindig igaz alapból))
     */
    public boolean toxicate(Room destRoom) {
        SimpleLogger.logMethodCall("Entity.toxicate(room: Room)");

        setStunnedFor(3);
        destRoom.addItems(items);
        SimpleLogger.logIndentedLine("Clear item list of Entity");
        items.clear();

        SimpleLogger.logMethodReturn("Entity.toxicate(room: Room)", true);
        return true;
    }

    /**
     * Ezen metódus, az adott karaktert megpróbálja lebénítani. Ezen alapimplementációban ez mindig sikeres, ezért a visszatérési értéke igaz lesz.
     *
     * @return sikeres-e a karakter megbénítása ((itt mindig true))
     */
    public boolean immobilize() {
        SimpleLogger.logMethodCall("Entity.immobilize()");
        setStunnedFor(3);
        SimpleLogger.logMethodReturn("Entity.immobilize()", true);
        return true;
    }

    /**
     * Ezen metódus a paraméterben kapott tárgyat az adott karakter birtokolt tárgyaihoz adja.
     *
     * @param item a hozzáadni kivánt tárgy
     */
    public void addItem(Item item) {
        SimpleLogger.logMethodCall("Entity.addItem(item: Item)");
        if(items.size() < itemCapacity) {
            items.add(item);
        }
        SimpleLogger.logMethodReturn("Entity.addItem(item: Item)");
    }

    /**
     * Ezen metódus a paraméterben kapott tárgyat az adott
     * karakter birtokolt tárgyai közül kiveszi.
     *
     * @param item az eltávolítandani kivánt tárgy
     */
    public void removeItem(Item item) {
        SimpleLogger.logMethodCall("Entity.removeItem(item: Item)");

        items.remove(item);

        SimpleLogger.logMethodReturn("Entity.removeItem(item: Item)");
    }

    /**
     * Ezen metódus adott időegységenként szükséges feladatok elvégzését látja el
     * az adott karakteren. Ezen implementációja esetén, ha szükséges az elkábított idő
     * attribútum értékét csökkenti.
     */

    public void tick() {
        SimpleLogger.logMethodCall("Entity.tick()");
        if(SimpleLogger.getYesOrNoInput("Jelenleg benult az Entitas?")) {
            SimpleLogger.logIndentedLine("Decrement stunnedFor");
            //stunnedFor--;
        }
        SimpleLogger.logMethodReturn("Entity.tick()");
    }

    /**
     * Ezen függvénnyel lekérdezhető az adott karakter aktuális szobája.
     * @return a karakter szobája
     */
    public Room getRoom(){
        SimpleLogger.logMethodCall("Entity.getRoom()");

        SimpleLogger.logMethodReturn("Entity.getRoom()", "Room");
        return room;
    }

    /**
     * A paraméterben kapott értékre állítja a kábulás hosszának értékét.
     * @param value a kapott érték
     */
    public void setStunnedFor(int value){
        SimpleLogger.logMethodCall("Entity.setStunnedFor(value: int)");
        //stunnedFor=value;

        SimpleLogger.logMethodReturn("Entity.setStunnedFor(value: int)");
    }

    /**
     * Beállítja az entitás szobáját.
     * @param room Az entitás új szobája.
     */
    public void setRoom(Room room) {
        SimpleLogger.logMethodCall("Entity.setRoom(room: Room)");

        this.room = room;
        SimpleLogger.logMethodReturn("Entity.setRoom(room: Room)");
    }

    /**
     * Beállítja az entitás játékát.
     * @param game Az entitás új játéka.
     */
    public void setGame(Game game) {
        SimpleLogger.logMethodCall("Entity.setGame(game: Game)");
        this.game = game;
        SimpleLogger.logMethodReturn("Entity.setGame(game: Game)");
    }
}
