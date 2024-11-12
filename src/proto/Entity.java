package proto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Az entitást reprezentáló absztrakt osztály.
 */
public abstract class Entity implements Serializable {

    /**
     * Ezen statikus tagváltozó azt számolja hány példány jött már létre ezn osztálybból.
     */
    protected static int objectNum=0;

    /**
     * Ezen tagváltozó egy adott példány egyedi azonósítására szolgáló String értéket tárol.
     */
    protected String id;

    /**
     * A játékot vezérlő osztály reprezentációja
     */
    protected Game game;


    /**
     * Ezen attribútumba tárolódik az Entity Room-ja, amelyben éppen van.
     */
    protected Room room;

    /**
     * Ezen attribútumba tárolódik, hogy hány Item-et birtokolhat ezen Entity.
     */
    private final int itemCapacity=5;

    /**
     * Azon Item-ek listája, amelyeket az Entity birtokol.
     */
    protected ArrayList<Item> items = new ArrayList<>();

    /**
     *  Ezen attribútumba tárolódik, hogy még hány tick-en erejéig kábult az adott entity.
     */
    protected int stunnedFor=0;

    /**
     * Ezen attribútumba tárolódik, hogy az adott entitás meghalt-e már.
     */
    private boolean killed=false;

    /**
     * @return igaz, ha halott, hamis, ha él.
     */
    public boolean isKilled() {
        return killed;
    }

    /**
     * A paraméterben kapott "dest" szobába megpróbálja
     * átléptetni, az adott karaktert a paraméterben kapott "door" ajtón keresztül.
     * Azaz ezen függvény úgymond, elindítja az átléptetés folyamatát.
     *
     * @param door az ajtó, amelyen keresztül át akar menni a célszobába a karakter
     *
     * @return sikeres-e az átkelés
     */
    public boolean move(Door door) {

        if(stunnedFor>0) {
            return false;
        }

        boolean ret = door.letEntityThrough(this, room);

        if (ret) {
            room.removeEntity(this);
            room = door.getNeighbour(room);
            room.addEntity(this);
        }


        return ret;
    }

    /**
     * A paraméterben kapott "item" tárgyat megpróbálja felszedni az adot karakter, azaz a birtokolt tárgyak közé helyezni.
     * Elindítja egy karakter tárgyfelvételének folyamatát.
     *
     * @param item a felvenni kivánt tárgy
     */
    public void pickUp(Item item) {

        if (items.size() > itemCapacity || stunnedFor>0) {
            return;
        }

        if (room.take(this, item)) {
            addItem(item);
        }

    }


    /**
     * Ezen metódus hatására a paraméterben kapott tárgyat eldobja/lerakja az adott
     * karakter, azaz kikerül a tárolt tárgyai közül és a szobájába kerül.
     *
     * @param item az eldobandó tárgy
     */
    public void drop(Item item) {

        if(stunnedFor>0) {
            return;
        }

        room.addItem(item);
        removeItem(item);

    }

    /**
     * Ezen metódus abban az esetben hívódik, amennyiben az adott karakter logarléc típusú tárgyat próbál felvenni. Ezen metódus ezen
     * alapimplementációjában a felvétel nem sikeres, így visszatérési értéke hamis (false).
     *
     * @return sikeres-e a logarléc felvétele ((itt mindig hamis alapból))
     */
    public boolean tryForLogarlec() {

        return false;
    }

    /**
     *  Ezen metódus a hívott entitást felszólítja, hogy mutatkozzon be a
     * paraméterben kapott  entitásnak.
     * @param entity a karakter amelynek mutatkozzon be ezen entitás
     */
    public void meet(Entity entity) {
    }

    /**
     *  Ezen metódus a hívott entitást felszólítja, hogy mutatkozzon be a
     * paraméterben kapott  professzornak.
     * @param prof a karakter amelynek mutatkozzon be ezen entitás
     */
    public void meet(Prof prof) {
    }

    /**
     *  Ezen metódus a hívott entitást felszólítja, hogy mutatkozzon be a
     * paraméterben kapott  studentnek.
     * @param student a karakter amelynek mutatkozzon be ezen entitás
     */
    public void meet(Student student) {
    }

    /**
     *  Ezen metódus a hívott entitást felszólítja, hogy mutatkozzon be a
     * paraméterben kapott  cleaningladynek.
     * @param cl a karakter amelynek mutatkozzon be ezen entitás
     */
    public void meet(CleaningLady cl) {
    }

    /**
     * Ezen metódus, az adott karaktert megpróbálja megmérgezni. Ezen
     * alapimplementációban ez mindig sikeres, ezért a visszatérési értéke igaz (true) lesz.
     * @param destRoom a szoba emyleből a mérgező
     * @return sikeres-e a karakter megmérgezése ((itt mindig igaz alapból))
     */
    public boolean toxicate(Room destRoom) {

        setStunnedFor(3);
        destRoom.addItems(items);
        items.clear();

        return true;
    }

    /**
     * Ezen metódus, az adott karaktert megpróbálja lebénítani. Ezen alapimplementációban ez mindig sikeres, ezért a visszatérési értéke igaz lesz.
     *
     * @return sikeres-e a karakter megbénítása ((itt mindig true))
     */
    public boolean immobilize() {
        setStunnedFor(3);

        return true;
    }

    /**
     * Ezen metódus a paraméterben kapott tárgyat az adott karakter birtokolt tárgyaihoz adja.
     *
     * @param item a hozzáadni kivánt tárgy
     */
    public void addItem(Item item) {

        if(items.size() < itemCapacity) {
            items.add(item);
        }
    }

    /**
     * Ezen metódus a paraméterben kapott tárgyat az adott
     * karakter birtokolt tárgyai közül kiveszi.
     *
     * @param item az eltávolítandani kivánt tárgy
     */
    public void removeItem(Item item) {

        items.remove(item);

    }

    /**
     * Ezen metódus adott időegységenként szükséges feladatok elvégzését látja el
     * az adott karakteren. Ezen implementációja esetén, ha szükséges az elkábított idő
     * attribútum értékét csökkenti.
     */
    public void tick() {

        if(stunnedFor>0) {
            stunnedFor--;
        }

    }

    /**
     * Ezen függvénnyel lekérdezhető az adott karakter aktuális szobája.
     * @return a karakter szobája
     */
    public Room getRoom(){
        return room;
    }

    /**
     * A paraméterben kapott értékre állítja a kábulás hosszának értékét.
     * @param value a kapott érték
     */
    public void setStunnedFor(int value){
        stunnedFor=value;
    }

    /**
     * Beállítja az entitás szobáját.
     * @param room Az entitás új szobája.
     */
    public void setRoom(Room room) {

        this.room = room;
    }

    /**
     * Beállítja az entitás játékát.
     * @param game Az entitás új játéka.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Összekapcsolja az entitás 2 különböző tárgyát.
     * @param i1 az első tárgy
     * @param i2 a második tárgy
     */
    public void linkItems(Item i1, Item i2){}

    /**
     * Activálja az entitás egy tárgyát.
     * @param item az aktiválandó tárgy
     */
    public void activateItem(Item item){}

    /**
     * @return Visszatér az osztály id tagváltozójával.
     */
    public String getID(){
        return id;
    }

    public void setKilled() {
        killed = true;
    }

    /**
     * @return Visszatér az entitás összes információjával.
     */
    public String getinfo() {
        StringBuilder builder = new StringBuilder();
        String newLine = System.lineSeparator();
        String tab = "\t";

        builder.append("ID: ").append(id).append(newLine);
        builder.append("Stunned for: ").append(stunnedFor).append(newLine);
        builder.append("Killed: ").append(killed).append(newLine);
        builder.append("Items of entity: ");

        if(items.isEmpty()) builder.append("No items.");
        builder.append(newLine);

        for(Item item : items) {
            String[] lines = item.getinfo().split(newLine);
            for(String line : lines) {
                builder.append(tab);
                builder.append(line);
                builder.append(newLine);
            }
        }

        builder.append("Room: ").append(room.getID()).append(newLine);

        return builder.toString();
    }


    /**
     * @return Az osztályból létrehozott példányok számát adja vissza.
     */
    public static int getObjectNum(){
        return objectNum;
    }

    /**
     * Frissíti az objectNum statikus tagváltozót, ha nagyobb értéket kap.
     * @param value az új érték amire módosulni próbál
     */
    public static void updateObjectNum(int value){
        if(objectNum<value)
            objectNum=value;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
