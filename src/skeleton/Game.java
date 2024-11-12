package skeleton;

import java.util.ArrayList;
import java.util.Random;

/**
 * Az játékot reprezentáló osztály.
 */
public class Game {

    //private boolean gameWon = false;

    private ArrayList<Room> rooms = new ArrayList<>();

    private ArrayList<Entity> entities = new ArrayList<>();

    /**
     * Jelzi, hogy a játék megnyerésre került.
     */
    public void gameWon() {
        SimpleLogger.logMethodCall("Game.gameWon()");

        //gameWon = true;
        SimpleLogger.logMethodReturn("Game.gameWon()");
    }

    /**
     * Hozzáad egy entitást a játékhoz.
     * @param e A hozzáadandó entitás.
     */
    public void addEntity(Entity e) {
        SimpleLogger.logMethodCall("Game.addEntity(e: Entity)");

        entities.add(e);
        SimpleLogger.logMethodReturn("Game.addEntity(e: Entity)");
    }

    /**
     * Eltávolít egy entitást a játékból.
     * @param e Az eltávolítandó entitás.
     */
    public void entityDied(Entity e) {
        SimpleLogger.logMethodCall("Game.entityDied(e: Entity)");

        entities.remove(e);
        SimpleLogger.logMethodReturn("Game.entityDied(e: Entity)");
    }

    /**
     * Hozzáad egy szobát a játékhoz.
     * @param r A hozzáadandó szoba.
     */
    public void addRoom(Room r) {
        SimpleLogger.logMethodCall("Game.addRoom(r: Room)");

        rooms.add(r);
        SimpleLogger.logMethodReturn("Game.addRoom(r: Room)");
    }

    /**
     * Eltávolít egy szobát a játékból.
     * @param r Az eltávolítandó szoba.
     */
    public void removeRoom(Room r) {
        SimpleLogger.logMethodCall("Game.removeRoom(r: Room)");

        rooms.remove(r);
        SimpleLogger.logMethodReturn("Game.removeRoom(r: Room)");
    }

    /**
     * Végrehajt egy játék ciklust, frissítve az entitások és szobák állapotát.
     */
    public void tick() {
        for (Entity e : entities) {
            e.tick();
        }

        for (Room r : rooms) {
            r.tick();
        }

        // Véletlenszerűen módosítja a szobákat
        Random random = new Random();
        if (random.nextInt(5) == 1) {
            int roomsToChange = random.nextInt(rooms.size());
            for (int i = 0; i < roomsToChange; i++) {
                int randomIndex = random.nextInt(rooms.size());
                Room randomNeighbour = rooms.get(randomIndex).getDoors().get(0).getNeighbour(rooms.get(randomIndex)); //Egy random szobának az első szomszédját megkapjuk
                if (rooms.get(randomIndex).merge(randomNeighbour)) {
                    removeRoom(randomNeighbour); //Ha mergelünk, akkor a régit kitöröljük
                }
            }

            roomsToChange = random.nextInt(rooms.size());
            for (int i = 0; i < roomsToChange; i++) {
                int randomIndex = random.nextInt(rooms.size());
                Room newRoom = rooms.get(randomIndex).split();
                if (newRoom != null)
                    rooms.add(newRoom);
            }
        }
    }
}
