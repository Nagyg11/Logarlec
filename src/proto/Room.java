package proto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Az szobát reprezentáló osztály.
 */
public class Room implements Serializable {

    /**
     * Ezen statikus tagváltozó azt számolja hány példány jött már létre ezn osztálybból.
     */
    private static int objectNum=0;

    /**
     * Ezen tagváltozó egy adott példány egyedi azonósítására szolgáló String értéket tárol.
     */
    private final String id;

    /**
     * A szoba ajtóinak listája.
     */
    private final ArrayList<Door> doors = new ArrayList<>();

    /**
     * A szoba tárgyainak listája.
     */
    private final ArrayList<Item> items = new ArrayList<>();

    /**
     * A szoba entitásai listája.
     */
    private final ArrayList<Entity> entities = new ArrayList<>();

    /**
     * A szoba kapacitása.
     */
    private int capacity;

    /**
     * A szoba gázos állapota.
     */
    private boolean isToxic;

    /**
     * A szoba vizes állapota.
     */
    private boolean isWet;

    /**
     * A szoba elátkozott állapota.
     */
    private boolean isCursed;

    /**
     * A szoba ragacsos állapota.
     */
    private boolean isSticky;

    /**
     * A szoba ragacsosság számlálója.
     */
    private short stickyCounter;

    /**
     * Az Room osztály konstruktora.
     * Növeli az objectNum értékét és beállítja az azonosítót, ezután a stickyCounter-t -1-re állítja.
     */
    public Room(){
        objectNum++;
        String[] objectName=getClass().getName().split("\\.");
        id=objectName[objectName.length-1]+"#"+objectNum;
        stickyCounter=-1;
    }

    /**
     * @return a szobában lévő tárgyak listája.
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Megpróbálja befogadni a paraméterként kapott entitást.
     * @param e Az entitás, aki be szeretne jutni.
     * @return true, ha sikeresen be tud jutni, false, ha nem.
     */
    public boolean acceptEntity(Entity e) {
        boolean ret = true;

        if (entities.size() >= capacity) {
            return false;
        }

        if(isToxic) {
            ret = !e.toxicate(this);
            if (!ret)
                return ret;
        }

        if(isWet) {
            ret = !e.immobilize();
            if (!ret)
                return ret;
        }

        for (Entity ent : entities) {
            ent.meet(e);
        }

        return ret;
    }

    /**
     * A szoba entitásaihoz ad egy újabbat.
     * @param e a hozzáadandó entitás.
     */
    public void addEntity(Entity e) {
        entities.add(e);
        if (stickyCounter > 0)
            stickyCounter--;
        if (stickyCounter == 0)
            makeRoomSticky();
    }

    /**
     * Egy entitást eltávolít a sajátjai közül.
     * @param e a törlendő entitás
     */
    public void removeEntity(Entity e) {
        entities.remove(e);
    }
    
    /**
     * Az e entitás az i tárgyat szeretné elvenni a szobától.
     * @param e a kapott entitás
     * @param i a felvevendő tárgy
     * @return igazzal tér vissza, ha el tudja venni és hamissal, ha nem.
     */
    public boolean take(Entity e, Item i) {
        if(!items.contains(i)) return false;

        if(isSticky) return false;

        boolean ret = i.pickup(e);
        if (ret)
            removeItem(i);
        return ret;
    }

    /**
     * Egy ajtót ad a szobához.
     * @param door az ajtó, amit hozzáadunk
     */
    public void addDoor(Door door){
        doors.add(door);
    }

    /**
     *
     * Kitöröl egy ajtót a szobából.
     * @param door a törlendő ajtó
     */
    public void removeDoor(Door door) {
        doors.remove(door);
    }

    /**
     * Hozzáad a szobában tárolt tárgyakhoz egy újat.
     * @param i az hozzáadandó tárgy
     */
    public void addItem(Item i) {
        items.add(i);
    }

    /**
     * Több tárgyat adunk a szobához
     * @param items a tárgyak listája, amit hozzáadunk
     */
    public void addItems(ArrayList<Item> items) {
        this.items.addAll(items);
    }

    /**
     * Egy tárgyat kitörlünk a szobából.
     * @param i a törlendő tárgy
     */
    public void removeItem(Item i) {
        items.remove(i);
    }

    /**
     * A hívott szobába olvad a paraméterként kapott szoba. Ennek
     * jelentése az, hogy a hívott szoba magára aggasztja a paraméterként kapott szoba
     * tulajdonságait, átveszi (és átírja) az ajtajait, valamint átveszi a tárgyait is.
     * @param r a kapott szoba, amivel összeovasztjuk a szobánkat
     * @return Nem megy
     * végbe az egyesülés, ha tartózkodnak a szobákban entitások vagy nem
     * szomszédosok. Ilyenkor hamissal tér vissza, egyébként igazzal.
     */
    public boolean merge(Room r) {
        if (!this.entities.isEmpty() || !r.entities.isEmpty()) {
            return false;
        }

        if (r.isCursed)
            makeRoomToxic();
        if (r.isWet)
            makeRoomWet();
        if (r.isCursed)
            makeRoomCursed();
        if (r.isSticky)
            makeRoomSticky();

        for (Item i : r.items) {
            items.add(i);
            r.removeItem(i);
            i.updateRoom(this);
        }

        for (Door door : r.doors) { //Átállítja az r ajtajait this-re
            door.swapRoom(r, this);
            if (door.getNeighbour(this) != this) {
                addDoor(door);
            }
        }
        r.doors.clear();

        if (r.capacity > capacity)
            setCapacity(r.capacity);

        return true;
    }

    /**
     * Randomicitás mellett
     * A hívott szobát bontja, osztja, ezáltal létrehoz egy új szobát. A szobák
     * osztoznak a hívott szoba tulajdonságain, ajtajain, valamint tárgyain.
     * @return Nem megy végbe az osztódás, ha tartózkodnak a szobában, ilyenkor érvénytelen
     * értéket ad, egyébként a szobát kapjuk vissza.
     */
    public Room randomSplit() {
        if (!entities.isEmpty())
            return null;

        Room newRoom = new Room();
        if (isCursed)
            newRoom.makeRoomToxic();
        if (isWet)
            newRoom.makeRoomWet();
        if (isCursed)
            newRoom.makeRoomCursed();
        if (isSticky)
            newRoom.makeRoomSticky();

        newRoom.capacity = capacity;

        Random random = new Random();

        int numItemsToMove = random.nextInt(items.size());
        for (int i = 0; i < numItemsToMove; i++) {
            if (!items.isEmpty()) {
                int randomIndex = random.nextInt(items.size() - 1);
                newRoom.addItem(items.get(randomIndex));
                removeItem(items.get(randomIndex));
            }
        }

        int numDoorsToMove = random.nextInt(doors.size());
        for (int i = 0; i < numDoorsToMove; i++) {
            if (!doors.isEmpty()) {
                int randomIndex = random.nextInt(doors.size() - 1);
                doors.get(randomIndex).swapRoom(this, newRoom);
                newRoom.addDoor(doors.get(randomIndex));
                removeDoor(doors.get(randomIndex));
            }
        }

        return newRoom;
    }

    /**
     * Randomicitás hiányában
     * A hívott szobát bontja, osztja, ezáltal létrehoz egy új szobát. A szobák
     * osztoznak a hívott szoba tulajdonságain, ajtajain, valamint tárgyain.
     * @return Nem megy végbe az osztódás, ha tartózkodnak a szobában, ilyenkor érvénytelen
     * értéket ad, egyébként a szobát kapjuk vissza.
     */
    public Room split() {
        if (!entities.isEmpty())
            return null;

        Room newRoom = new Room();
        if (isCursed)
            newRoom.makeRoomToxic();
        if (isWet)
            newRoom.makeRoomWet();
        if (isCursed)
            newRoom.makeRoomCursed();
        if (isSticky)
            newRoom.makeRoomSticky();

        newRoom.capacity = capacity;

        for (int i = 0; i < items.size() / 2; i++) {
            if (items.size() >= i) {
                newRoom.addItem(items.get(i));
                removeItem(items.get(i));
            }
        }

        for (int i = 0; i < doors.size() / 2; i++) {
            if (doors.size() >= i) {
                doors.get(i).swapRoom(this, newRoom);
                newRoom.addDoor(doors.get(i));
                removeDoor(doors.get(i));
            }
        }

        return newRoom;
    }

    /**
     * Tulajdonságaitól függően megtámadhatja a benne megtalálható entitásokat,
     * valamint eltűntetheti/megjelenítheti a szoba ajtajait.
     */
    public void tick() {
        if (isToxic) {
            for (Entity e : entities) {
                if (e.toxicate(this)) {
                    for (Door door : doors) { //Ha valamelyik szomszédba mehet, akkor oda is megy
                        if (e.move(door))
                            break;
                    }
                }
            }
        }

        if (isWet) {
            for (Entity e : entities) {
                if (e.immobilize()) {
                    for (Door door : doors) { //Ha valamelyik szomszédba mehet, akkor oda is megy
                        if (e.move(door))
                            break;
                    }
                }
            }
        }

        if (isCursed)
            magicDoors();
    }

    /**
     * Eltűnteti, megjeleníti a szoba ajtajait.
     */
    public void magicDoors() {
        for (Door door : doors) {
            door.changeClosed();
        }
    }

    /**
     * A szoba gázosságát leállítja.
     */
    public void detoxicate() {
        isToxic = false;
    }

    /**
     * Kitessékeli a szobából az entitásokat, ami nem egyezik a paraméterként kapottal
     * a stickyCounter-t 3-ra állítja.
     * @param e Az entitás, aki meghívja a takarítást
     */
    public void clean(Entity e) {
        if(isToxic)
            isToxic = false;
        for (int i = 0; i < entities.size(); i++) {
            if (!entities.get(i).equals(e))
                for (Door door : doors)
                    if (entities.get(i).move(door)){
                        i--;
                        break;
                    }
        }
        stickyCounter = 3;
    }

    /**
     * Beállítja a szoba befogadóképességét a megadott értékre.
     *
     * @param capacity A szoba befogadóképessége.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Visszaadja a szoba befogadóképességét.
     *
     * @return A szoba befogadóképessége.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Mérgezővé teszi a szobát.
     */
    public void makeRoomToxic() {
        isToxic = true;
    }

    /**
     * Nedvesé teszi a szobát.
     */
    public void makeRoomWet() {
        isWet = true;
    }

    /**
     * Elátkozottá teszi a szobát.
     */
    public void makeRoomCursed() {
        isCursed = true;
    }

    /**
     * Ragacsossá teszi a szobát.
     */
    public void makeRoomSticky() {
        isSticky = true;
    }

    /**
     * @return a szobában lévő ajtók listája.
     */
    public ArrayList<Door> getDoors() {
        return doors;
    }

    /**
     * @return Visszatér az osztály id tagváltozójával.
     */
    public String getID(){
        return id;
    }

    /**
     * @return Visszatér a szoba összes információjával.
     */
    public String getinfo() {
        StringBuilder builder = new StringBuilder();
        String newLine = System.lineSeparator();
        String tab = "\t";

        builder.append("ID: ").append(id).append(newLine);
        builder.append("Capacity: ").append(capacity).append(newLine);
        builder.append("Toxic: ").append(isToxic).append(newLine);
        builder.append("Wet: ").append(isWet).append(newLine);
        builder.append("Cursed: ").append(isCursed).append(newLine);
        builder.append("Sticky: ").append(isSticky).append(newLine);

        builder.append("Entities of room: ");
        if(entities.isEmpty()) builder.append("No entities.");
        builder.append(newLine);

        for(Entity entity : entities) {
            builder.append(tab);
            builder.append("ID: ").append(entity.getID());
            builder.append(newLine);
        }

        builder.append("Items of room: ");
        if(items.isEmpty()) builder.append("No items.");
        builder.append(newLine);

        for(Item item : items) {
            String[] lines = item.getinfo().split(newLine);
            for (String line : lines) {
                builder.append(tab);
                builder.append(line);
                builder.append(newLine);
            }
        }

        builder.append("Doors of room: ");
        if(doors.isEmpty()) builder.append("No doors.");
        builder.append(newLine);

        for(Door door : doors) {
            String[] lines = door.getinfo().split(newLine);
            for (String line : lines) {
                builder.append(tab);
                builder.append(line);
                builder.append(newLine);
            }
        }

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
}
