package skeleton;

import java.util.ArrayList;
import java.util.Random;

/**
 * Az szobát reprezentáló osztály.
 */
public class Room {

    private final ArrayList<Door> doors = new ArrayList<>();

    private final ArrayList<Item> items = new ArrayList<>();

    private final ArrayList<Entity> entities = new ArrayList<>();

    private int capacity;

    //private boolean isToxic;

    //private boolean isWet;

    //private boolean isCursed;

    /**
     * Megpróbálja befogadni a paraméterként kapott entitást.
     * @param e Az entitás, aki be szeretne jutni.
     * @return true, ha sikeresen be tud jutni, false, ha nem.
     */
    public boolean acceptEntity(Entity e) {
        SimpleLogger.logMethodCall("Room.acceptEntity(e: Entity)");
        boolean ret = true;

        if (entities.size() >= capacity) {
            SimpleLogger.logMethodReturn("Room.acceptEntity(e: Entity)", false);
            return false;
        }

        if (SimpleLogger.getYesOrNoInput("Gazos a szoba?")) {
            ret = !e.toxicate(this);
            if (!ret) {
                SimpleLogger.logMethodReturn("Room.acceptEntity(e: Entity)", ret);
                return ret;
            }
        }

        if (SimpleLogger.getYesOrNoInput("Nedves a szoba?")) {
            ret = !e.immobilize();
            if (!ret) {
                SimpleLogger.logMethodReturn("Room.acceptEntity(e: Entity)", ret);
                return ret;
            }
        }

        for (Entity ent : entities) {
            ent.meet(e);
        }

        SimpleLogger.logMethodReturn("Room.acceptEntity(e: Entity)", ret);
        return ret;
    }

    /**
     * A szoba entitásaihoz ad egy újabbat.
     * @param e a hozzáadandó entitás.
     */
    public void addEntity(Entity e) {
        SimpleLogger.logMethodCall("Room.addEntity(e: Entity)");

        entities.add(e);
        SimpleLogger.logMethodReturn("Room.addEntity(e: Entity)");
    }

    /**
     * Egy entitást eltávolít a sajátjai közül.
     * @param e a törlendő entitás
     */
    public void removeEntity(Entity e) {
        SimpleLogger.logMethodCall("Room.removeEntity(e: Entity)");

        entities.remove(e);
        SimpleLogger.logMethodReturn("Room.removeEntity(e: Entity)");
    }

    /**
     * Az e entitás az i tárgyat szeretné elvenni a szobától.
     * @param e a kapott entitás
     * @param i a felvevendő tárgy
     * @return igazzal tér vissza, ha el tudja venni és hamissal, ha nem.
     */
    public boolean take(Entity e, Item i) {
        SimpleLogger.logMethodCall("Room.take(e: Entity, i: Item)");

        boolean ret = i.pickup(e);
        if (ret)
            removeItem(i);

        SimpleLogger.logMethodReturn("Room.take(e: Entity, i: Item)", ret   );
        return ret;
    }

    /**
     * Egy ajtót ad a szobához.
     * @param door az ajtó, amit hozzáadunk
     */
    public void addDoor(Door door){
        SimpleLogger.logMethodCall("Room.addDoor(door: Door)");

        doors.add(door);
        SimpleLogger.logMethodReturn("Room.addDoor(door: Door)");
    }

    /**
     *
     * Kitöröl egy ajtót a szobából.
     * @param door a törlendő ajtó
     */
    public void removeDoor(Door door) {
        SimpleLogger.logMethodCall("Room.removeDoor(door: Door)");

        doors.remove(door);
        SimpleLogger.logMethodReturn("Room.removeDoor(door: Door)");
    }

    /**
     * Hozzáad a szobában tárolt tárgyakhoz egy újat.
     * @param i az hozzáadandó tárgy
     */
    public void addItem(Item i) {
        SimpleLogger.logMethodCall("Room.addItem(i: Item)");

        items.add(i);
        SimpleLogger.logMethodReturn("Room.addItem(i: Item)");
    }

    /**
     * Több tárgyat adunk a szobához
     * @param items a tárgyak listája, amit hozzáadunk
     */
    public void addItems(ArrayList<Item> items) {
        SimpleLogger.logMethodCall("Room.addItems(items: Item[])");

        this.items.addAll(items);
        SimpleLogger.logMethodReturn("Room.addItems(items: Item[])");
    }

    /**
     * Egy tárgyat kitörlünk a szobából.
     * @param i a törlendő tárgy
     */
    public void removeItem(Item i) {
        SimpleLogger.logMethodCall("Room.removeItem(i: Item)");

        items.remove(i);
        SimpleLogger.logMethodReturn("Room.removeItem(i: Item)");
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
        SimpleLogger.logMethodCall("Room.merge(r: Room)");

        if (!this.entities.isEmpty() || !r.entities.isEmpty()) {
            SimpleLogger.logMethodReturn("Room.merge(r: Room)", false);
            return false;
        }

        if (SimpleLogger.getYesOrNoInput("Gazos az adott szoba?")) {
            makeRoomToxic();
        }
        if (SimpleLogger.getYesOrNoInput("Nedves az adott szoba?")) {
            makeRoomWet();
        }
        if (SimpleLogger.getYesOrNoInput("Atkozott az adott szoba?")) {
            makeRoomCursed();
        }

        SimpleLogger.logIndentedLine("Take items of the room about to be merged");
        items.addAll(r.items);
        r.items.clear();

        SimpleLogger.logIndentedLine("Take doors of the room about to be merged");
        for (Door door : r.doors) { //Átállítja az r ajtajait this-re
            door.swapRoom(r, this);
            if (door.getNeighbour(this) != this) {
                addDoor(door);
            }
        }
        r.doors.clear();

        if (r.capacity > capacity)
            setCapacity(r.capacity);

        SimpleLogger.logMethodReturn("Room.merge(r: Room)", true);
        return true;
    }

    /**
     * A hívott szobát bontja, osztja, ezáltal létrehoz egy új szobát. A szobák
     * osztoznak a hívott szoba tulajdonságain, ajtajain, valamint tárgyain.
     * @return Nem megy végbe az osztódás, ha tartózkodnak a szobában, ilyenkor érvénytelen
     * értéket ad, egyébként a szobát kapjuk vissza.
     */
    public Room split() {
        SimpleLogger.logMethodCall("Room.split()");
        if (!entities.isEmpty()) {
            SimpleLogger.logMethodReturn("Room.split()", "null");
            return null;
        }

        Room newRoom = new Room();
        if (SimpleLogger.getYesOrNoInput("Gazos az adott szoba?")) {
            newRoom.makeRoomToxic();
        }
        if (SimpleLogger.getYesOrNoInput("Nedves az adott szoba?")) {
            newRoom.makeRoomWet();
        }
        if (SimpleLogger.getYesOrNoInput("Atkozott az adott szoba?")) {
            newRoom.makeRoomCursed();
        }
        newRoom.capacity = capacity;

        Random random = new Random();

        int numItemsToMove = random.nextInt(items.size());
        for (int i = 0; i < numItemsToMove; i++) {
            int randomIndex = random.nextInt(items.size());
            newRoom.addItem(items.get(randomIndex));
            removeItem(items.get(randomIndex));
        }

        int numDoorsToMove = random.nextInt(doors.size());
        for (int i = 0; i < numDoorsToMove; i++) {
            int randomIndex = random.nextInt(doors.size());
            doors.get(randomIndex).swapRoom(this, newRoom);
            newRoom.addDoor(doors.get(randomIndex));
            removeDoor(doors.get(randomIndex));
        }

        SimpleLogger.logMethodReturn("Room.split()", "newRoom");
        return newRoom;
    }

    /**
     * Tulajdonságaitól függően megtámadhatja a benne megtalálható entitásokat,
     * valamint eltűntetheti/megjelenítheti a szoba ajtajait.
     */
    public void tick() {
        SimpleLogger.logMethodCall("Room.tick()");

        if (SimpleLogger.getYesOrNoInput("Gazos a szoba?")) {
            for (Entity e : entities) {
                if (e.toxicate(this)) {
                    for (Door door : doors) { //Ha valamelyik szomszédba mehet, akkor oda is megy
                        Room neighbour = door.getNeighbour(this);
                        if (e.move(neighbour, door))
                            break;
                    }
                }
            }
        }

        if (SimpleLogger.getYesOrNoInput("Nedves a szoba?")) {
            for (Entity e : entities) {
                if (e.immobilize()) {
                    for (Door door : doors) { //Ha valamelyik szomszédba mehet, akkor oda is megy
                        Room neighbour = door.getNeighbour(this);
                        if (e.move(neighbour, door))
                            break;
                    }
                }
            }
        }

        if (SimpleLogger.getYesOrNoInput("Atkozott a szoba?")) {
            if (SimpleLogger.getYesOrNoInput("Eltunnek az ajtok?"))
            {
                magicDoors();
            }
        }

        SimpleLogger.logMethodReturn("Room.tick()");
    }

    /**
     * Eltűnteti, megjeleníti a szoba ajtajait.
     */
    public void magicDoors() {
        SimpleLogger.logMethodCall("Room.magicDoors()");
        for (Door door : doors) {
            door.changeClosed();
        }
        SimpleLogger.logMethodReturn("Room.magicDoors()");
    }

    /**
     * Beállítja a szoba befogadóképességét a megadott értékre.
     *
     * @param capacity A szoba befogadóképessége.
     */
    public void setCapacity(int capacity) {
        SimpleLogger.logMethodCall("Room.setCapacity(capacity: int)");
        this.capacity = capacity;

        SimpleLogger.logMethodReturn("Room.setCapacity(capacity: int)");
    }

    /**
     * Visszaadja a szoba befogadóképességét.
     *
     * @return A szoba befogadóképessége.
     */
    public int getCapacity() {
        SimpleLogger.logMethodCall("Room.getCapacity()");

        SimpleLogger.logMethodReturn("Room.getCapacity()", capacity);
        return capacity;
    }

    /**
     * Mérgezővé teszi a szobát.
     */
    public void makeRoomToxic() {
        SimpleLogger.logMethodCall("Room.makeRoomToxic()");
        //isToxic = true;

        SimpleLogger.logMethodReturn("Room.makeRoomToxic()");
    }

    /**
     * Nedvesé teszi a szobát.
     */
    public void makeRoomWet() {
        SimpleLogger.logMethodCall("Room.makeRoomWet()");
        //isWet = true;

        SimpleLogger.logMethodReturn("Room.makeRoomWet()");
    }

    /**
     * Elátkozottá teszi a szobát.
     */
    public void makeRoomCursed() {
        SimpleLogger.logMethodCall("Room.makeRoomCursed()");
        //isCursed = true;

        SimpleLogger.logMethodReturn("Room.makeRoomCursed()");
    }

    public ArrayList<Door> getDoors() {
        SimpleLogger.logMethodCall("Room.getDoors()");

        SimpleLogger.logMethodReturn("Room.getDoors()", "Doors");
        return doors;
    }
}
