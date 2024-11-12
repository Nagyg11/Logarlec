package skeleton;

/**
 * Az ajtót reprezentáló osztály.
 */
public class Door {

    private Room firstRoom;
    private Room secondRoom;

    //private boolean closed;

    //private boolean oneway;

    /**
     * Megváltoztatja az ajtó állapotát (nyitott vagy zárt).
     */
    public void changeClosed() {
        SimpleLogger.logMethodCall("Door.changeClosed()");

        //closed = !closed;
        SimpleLogger.logMethodReturn("Door.changeClosed()");
    }

    /**
     * Engedi az entitást át az ajtón, ha az az adott szobából jön.
     *
     * @param e      Az áthaladni kívánó entitás.
     * @param dest Az a szoba, amelyből az entitás jön.
     * @return Igaz, ha az entitás áthaladhatott az ajtón, egyébként hamis.
     */
    public boolean letEntityThrough(Entity e, Room dest) {
        SimpleLogger.logMethodCall("Door.letEntityThrough(e: Entity, dest: Room)");

        if (firstRoom == dest || secondRoom == dest) {
            boolean ret = dest.acceptEntity(e);
            SimpleLogger.logMethodReturn("Door.letEntityThrough(e: Entity, dest: Room)", ret);
            return ret;
        }
        SimpleLogger.logMethodReturn("Door.letEntityThrough(e: Entity, dest: Room)", false);
        return false;
    }

    /**
     * Kicseréli az ajtóhoz kapcsolódó szobákat.
     *
     * @param oldRoom A régi szoba.
     * @param newRoom Az új szoba.
     */
    public void swapRoom(Room oldRoom, Room newRoom) {
        SimpleLogger.logMethodCall("Door.swapRoom(oldRoom: Room, newRoom: Room)");
        if (firstRoom == oldRoom)
            firstRoom = newRoom;
        if (secondRoom == oldRoom)
            secondRoom = newRoom;
        SimpleLogger.logMethodReturn("Door.swapRoom(oldRoom: Room, newRoom: Room)");
    }

    /**
     * Beállítja az ajtóhoz kapcsolódó szobákat.
     *
     * @param r1 Az egyik szoba.
     * @param r2 A másik szoba.
     */
    public void setNeighbours(Room r1, Room r2) {
        SimpleLogger.logMethodCall("Door.setNeighbours(r1: Room, r2: Room)");
        firstRoom = r1;
        secondRoom = r2;
        SimpleLogger.logMethodReturn("Door.setNeighbours(r1: Room, r2: Room)");
    }

    /**
     * Visszaadja az ajtóhoz kapcsolódó szomszédos szobát.
     *
     * @param r Az aktuális szoba.
     * @return A szomszédos szoba.
     */
    public Room getNeighbour(Room r) {
        SimpleLogger.logMethodCall("Door.getNeighbour(r: Room)");
        if (r == firstRoom) {
            SimpleLogger.logMethodReturn("Door.getNeighbour(r: Room)", "secondRoom");
            return secondRoom;
        }
        if (r == secondRoom) {
            SimpleLogger.logMethodReturn("Door.getNeighbour(r: Room)", "firstRoom");
            return firstRoom;
        }

        SimpleLogger.logMethodReturn("Door.getNeighbour(r: Room)", "null");
        return null;
    }
}
