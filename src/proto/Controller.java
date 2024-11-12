package proto;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * A játékot kezelő Controller osztály, amely vezérli a játék folyamatát és az entitások közötti interakciót a játékkörnyezettel.
 */
public class Controller {

    /**
     * A játékot tároló objektum.
     */
    private Game game;

    /**
     * Az aktív karaktert tároló entitás objektum.
     */
    private Entity activeCharacter;

    /**
     * Olyan Scanner objektum, amelyet a bemeneti adatok beolvasásához használunk.
     */
    private Scanner sharedScanner;

    /**
     * Olyan PrintWriter objektum, amelyet a kimeneti adatok írásához használunk.
     */
    private PrintWriter sharedWriter;

    /**
     * Egy Random objektum, amelyet közösen használunk a véletlenszerűség generálásához.
     */
    private final Random sharedRandom;

    /**
     * Konstruktor egy Controller példány létrehozásához egy adott játékobjektummal.
     */
    public Controller() {
        game = new Game();
        sharedRandom = new Random();
    }

    /**
     * Indítja a játékot a felhasználói bemenet fogadásával és a játék ciklusának futtatásával.
     */
    public void startGame() {
        sharedWriter = new PrintWriter(System.out, true);
        sharedScanner = new Scanner(System.in);

        load(new String[]{"load", "proto.data"});

        while (!game.gameEnded()) {
            ArrayList<Entity> entities = game.getEntities();
            for(Entity entity : entities) {
                activeCharacter = entity;
                if (entity instanceof Student student) {
                    handlePlayerTurn(student);
                } else if (entity instanceof Prof prof) {
                    handleProfTurn(prof);
                } else if (entity instanceof CleaningLady cleaningLady) {
                    handleCleaningLadyTurn(cleaningLady);
                }
            }

            game.tick();
        }

        if(sharedWriter != null) sharedWriter.close();
        if(sharedScanner != null) sharedScanner.close();
    }

    /**
     * Tesztelési mód elindítása, amely során tesztadatokat olvas be a megadott argumentumok alapján.
     * @param args A teszteléshez megadott argumentumok.
     */
    public void startTesting(String[] args) {
        if (args.length < 2) {
            System.out.println("Not enough arguments. Usage: test <test_number>/<exit>");
        }

        int number = Integer.parseInt(args[1]);
        String text = "tests" + File.separator + number + File.separator + "input.txt";
        String outputFile = "tests" + File.separator + number + File.separator + "generated_output.txt";

        try {
            sharedScanner = new Scanner(new File(text));
            sharedWriter = new PrintWriter(outputFile);

            while (sharedScanner.hasNextLine()) {
                String line = sharedScanner.nextLine();
                String[] cmd = line.split(" ");
                switch (cmd[0]) {
                    case "load" -> load(cmd);
                    case "save" -> save(cmd);
                    case "getinfo" -> getinfo(cmd);
                    case "list" -> list(cmd);
                    case "move" -> move(cmd);
                    case "pickup" -> pickup(cmd);
                    case "activate" -> activate(cmd);
                    case "drop" -> drop(cmd);
                    case "link" -> link(cmd);
                    case "random" -> random(cmd);
                    case "addPlayer" -> addPlayer(cmd);
                    case "addNPC" -> addNPC(cmd);
                    case "addRoom" -> addRoom(cmd);
                    case "addDoor" -> addDoor(cmd);
                    case "addItem" -> addItem(cmd);
                    case "setActiveCharacter" -> setActiveCharacter(cmd);
                    case "merge" -> merge(cmd);
                    case "split" -> split(cmd);
                    default -> sharedWriter.println("Invalid command!");
                }
            }

            sharedWriter.println(game.getinfo());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sharedScanner.close();
            sharedWriter.close();
        }
    }

    /**
     * Kezeli a játékos körét egy adott hallgató entitás esetén.
     * @param student Az aktív hallgató entitás, akinek a körét kezelni kell.
     */
    public void handlePlayerTurn(Student student) {
        boolean endTurn = false;
        while(!endTurn && !student.isKilled() && !game.gameEnded() && sharedScanner.hasNextLine()) {
            String line = sharedScanner.nextLine();
            if(line == null) break;

            String[] cmd = line.split(" ");
            switch (cmd[0]) {
                case "load" -> load(cmd);
                case "save" -> save(cmd);
                case "getinfo" -> getinfo(cmd);
                case "list" -> list(cmd);
                case "move" -> move(cmd);
                case "pickup" -> pickup(cmd);
                case "activate" -> activate(cmd);
                case "drop" -> drop(cmd);
                case "link" -> link(cmd);
                case "endTurn" -> endTurn = true;
                case "random" -> random(cmd);
                default -> sharedWriter.println("Invalid command!");
            }
        }
    }

    /**
     * Kezeli a professzor körét egy adott professzor entitás esetén.
     * @param prof Az aktív professzor entitás, akinek a körét kezelni kell.
     */
    public void handleProfTurn(Prof prof) {
        ArrayList<Door> doors = prof.getRoom().getDoors();
        if(!doors.isEmpty()) {
            int index = sharedRandom.nextInt(doors.size());
            prof.move(doors.get(index));
        }

        ArrayList<Item> items = prof.getRoom().getItems();
        if(!items.isEmpty()) {
            int index = sharedRandom.nextInt(items.size());
            prof.pickUp(items.get(index));
        }
    }

    /**
     * Kezeli a takarítónő körét egy adott takarítónő entitás esetén.
     * @param cleaningLady Az aktív takarítónő entitás, akinek a körét kezelni kell.
     */
    public void handleCleaningLadyTurn(CleaningLady cleaningLady) {
        ArrayList<Door> doors = cleaningLady.getRoom().getDoors();
        if(!doors.isEmpty()) {
            int index = sharedRandom.nextInt(doors.size());
            cleaningLady.move(doors.get(index));
        }
    }

    /**
     * Betölti a játék állapotát egy megadott fájlból.
     * @param cmd A parancs argumentumai, amelyek között a második a fájl elérési útja.
     */
    public void load(String[] cmd) {
        try {
            FileInputStream fileIn = new FileInputStream(cmd[1]);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            game = (Game) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            sharedWriter.println("Sikertelen fájl betöltés!");
        }

        game.updateObjectNums();
    }

    /**
     * Elmenti a játék állapotát egy megadott fájlba.
     * @param cmd A parancs argumentumai, amelyek között a második a fájl elérési útja.
     */
    public void save(String[] cmd) {

        try {
            FileOutputStream fileOut = new FileOutputStream(cmd[1]);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            game.saveObjectNum();
            out.writeObject(game);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            sharedWriter.println("Sikertelen fájl kimentés!");
        }

    }

    /**
     * Beállítja a játék véletlenszerűségét az argumentumban megadott értékre.
     * @param cmd A parancs argumentumai, amelyek között a második a véletlenszerűség állapota.
     */
    public void random(String[] cmd) {
        if(cmd.length < 2) return;

        boolean random = Boolean.parseBoolean(cmd[1]);
        game.setRandom(random);
    }

    /**
     * Visszaadja az adott entitás vagy szoba információit, ha azonosító alapján keresve találhatóak meg.
     * @param cmd A parancs argumentumai, amelyek között a második az entitás vagy szoba azonosítója.
     */
    public void getinfo(String[] cmd) {
        if(cmd.length < 2) sharedWriter.println(game.getinfo());
        else {
            ArrayList<Entity> entities = game.getEntities();
            for(Entity entity : entities) {
                if(entity.getID().equals(cmd[1])) {
                    entity.getinfo();
                    return;
                }
            }

            ArrayList<Room> rooms = game.getRooms();
            for(Room room : rooms) {
                if(room.getID().equals(cmd[1])) {
                    room.getinfo();
                    return;
                }
            }
        }
    }

    /**
     * Kiírja a játékbeli szobák vagy entitások azonosítóit.
     * @param cmd A parancs argumentumai
     */
    public void list(String[] cmd) {
        if (cmd.length > 2) return;

        if (cmd[1].equals("rooms"))
            for (Room room : game.getRooms())
                sharedWriter.println(room.getID());

        if (cmd[1].equals("entities"))
            for (Entity entity : game.getEntities())
                sharedWriter.println(entity.getID());
    }

    /**
     * Mozgatja az aktív karaktert egy adott ajtón keresztül egy másik szobába.
     * @param cmd A parancs argumentumai
     */
    public void move(String[] cmd) {
        if(cmd.length < 2) return;
        Room room = activeCharacter.getRoom();
        if(room == null) return;

        Door selectedDoor = null;
        ArrayList<Door> doors = room.getDoors();
        for(Door door : doors) {
            if(door.getID().equals(cmd[1])) {
                selectedDoor = door;
                break;
            }
        }
        if(selectedDoor == null) return;

        activeCharacter.move(selectedDoor);
    }

    /**
     * Felvehet egy tárgyat az aktív karakter a jelenlegi szobájából.
     * @param cmd A parancs argumentumai
     */
    public void pickup(String[] cmd) {
        if(cmd.length < 2) return;


        ArrayList<Item> items = activeCharacter.getRoom().getItems();
        for(Item item : items) {
            if(item.getID().equals(cmd[1])) {
                activeCharacter.pickUp(item);
                return;
            }
        }
    }

    /**
     * Aktivál egy tárgyat az aktív karakter jelenlegi szobájában.
     * @param cmd A parancs argumentumai
     */
    public void activate(String[] cmd) {
        if(cmd.length < 2) return;

        ArrayList<Item> items = activeCharacter.getItems();
        for(Item item : items) {
            if(item.getID().equals(cmd[1])) {
                activeCharacter.activateItem(item);
                return;
            }
        }
    }

    /**
     * Elhelyez egy tárgyat az aktív karakter jelenlegi szobájában.
     * @param cmd A parancs argumentumai
     */
    public void drop(String[] cmd) {
        if(cmd.length < 2) return;

        ArrayList<Item> items = activeCharacter.getItems();
        for(Item item : items) {
            if(item.getID().equals(cmd[1])) {
                activeCharacter.drop(item);
                return;
            }
        }
    }

    /**
     * Összekapcsol két tárgyat az aktív karakter jelenlegi szobájában.
     * @param cmd A parancs argumentumai
     */
    public void link(String[] cmd) {
        if(cmd.length < 3) return;

        Item item1 = null;
        Item item2 = null;

        ArrayList<Item> items = activeCharacter.getItems();
        for(Item item : items) {
            if(item.getID().equals(cmd[1])) {
                item1 = item;
            }
            if(item.getID().equals(cmd[2])) {
                item2 = item;
            }
        }

        if(item1 == null || item2 == null || item1==item2) return;

        activeCharacter.linkItems(item1, item2);
    }

    /**
     * Hozzáad egy új játékost a játékhoz.
     * @param cmd A parancs argumentumai
     */
    public void addPlayer(String[] cmd) {
        if(cmd.length < 3) return;

        Entity student;
        ArrayList<Entity> entities = game.getEntities();
        for(Entity entity : entities) {
            if(entity.getID().equals(cmd[1])) {
                return;
            }
        }

        ArrayList<Room> rooms = game.getRooms();
        for(Room room : rooms) {
            if(room.getID().equals(cmd[2])) {
                student = new Student(cmd[1]);
                student.setRoom(room);
                room.addEntity(student);
                game.addEntity(student);
                return;
            }
        }
    }

    /**
     * Hozzáad egy új NPC-t a játékhoz.
     * @param cmd A parancs argumentumai
     */
    public void addNPC(String[] cmd) {
        if(cmd.length < 3) return;

        Entity entity = null;
        try {
            Class<?> npcClass = Class.forName("proto." + cmd[1]);
            entity = (Entity) npcClass.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            sharedWriter.println("Nincs ilyen NPC tipus!");
        }
        if(entity == null) return;

        ArrayList<Room> rooms = game.getRooms();
        for(Room room : rooms) {
            if(room.getID().equals(cmd[2])) {
                entity.setRoom(room);
                room.addEntity(entity);
                game.addEntity(entity);
                return;
            }
        }
    }

    /**
     * Hozzáad egy új szobát a játékhoz.
     * @param cmd A parancs argumentumai
     */
    public void addRoom(String[] cmd) {
        if(cmd.length < 2) return;

        Room room = new Room();
        int capacity = Integer.parseInt(cmd[1]);
        room.setCapacity(capacity);

        for(int i = 2; i < cmd.length; i++) {
            switch (cmd[i]) {
                case "toxic" -> room.makeRoomToxic();
                case "wet" -> room.makeRoomWet();
                case "cursed" -> room.makeRoomCursed();
                case "sticky" -> room.makeRoomSticky();
            }
        }

        game.addRoom(room);
    }

    /**
     * Hozzáad egy új ajtót a játékhoz.
     * @param cmd A parancs argumentumai
     */
    public void addDoor(String[] cmd) {
        if(cmd.length < 3) return;

        Room room1 = null;
        Room room2 = null;
        boolean oneway = false;

        ArrayList<Room> rooms = game.getRooms();
        for(Room room : rooms) {
            if(room.getID().equals(cmd[1])) {
                room1 = room;
            }
            if(room.getID().equals(cmd[2])) {
                room2 = room;
            }
        }

        if(room1 == null || room2 == null || room1 == room2) return;

        if(cmd.length > 3 && cmd[3].equals("oneway")) oneway = true;

        Door door = new Door();
        door.setNeighbours(room1, room2);
        door.setOneway(oneway);
        room1.addDoor(door);
        room2.addDoor(door);
    }

    /**
     * Hozzáad egy új tárgyat a játékhoz.
     * @param cmd A parancs argumentumai
     */
    public void addItem(String[] cmd) {
        if(cmd.length < 3) return;

        Item item = null;
        try {
            Class<?> npcClass = Class.forName("proto." + cmd[1]);
            item = (Item) npcClass.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            sharedWriter.println("Nincs ilyen targytipus!");
        }
        if(item == null) return;

        ArrayList<Room> rooms = game.getRooms();
        for(Room room : rooms) {
            if(room.getID().equals(cmd[2])) {
                room.addItem(item);
                return;
            }
        }

        ArrayList<Entity> entities = game.getEntities();
        for(Entity entity : entities) {
            if(entity.getID().equals(cmd[2])) {
                entity.addItem(item);
                return;
            }
        }
    }

    /**
     * Beállítja az aktív karaktert az adott azonosítójú entitásra a játékban.
     * @param cmd A parancs argumentumai
     */
    public void setActiveCharacter(String[] cmd) {
        if(cmd.length < 2) return;

        ArrayList<Entity> entities = game.getEntities();
        for(Entity entity : entities) {
            if(entity.getID().equals(cmd[1])) {
                activeCharacter = entity;
                return;
            }
        }
    }

    /**
     * Összefésüli két adott szoba entitásait egyetlen szobává.
     * @param cmd A parancs argumentumai
     */
    public void merge(String[] cmd) {
        if(cmd.length < 3) return;

        Room room1 = null;
        Room room2 = null;

        for (Room room : game.getRooms()) {
            if (room.getID().equals(cmd[1]))
                room1 = room;
            if (room.getID().equals(cmd[2]))
                room2 = room;
        }

        if (room1 == null || room2 == null || room1 == room2) return;

        room1.merge(room2);
        game.removeRoom(room2);
    }

    /**
     * Szétválasztja egy adott szoba entitásait két külön szobába.
     * @param cmd A parancs argumentumai
     */
    public void split(String[] cmd) {
        if(cmd.length < 2) return;

        Room room1 = null;

        for (Room room : game.getRooms()) {
            if (room.getID().equals(cmd[1])) {
                room1 = room;
                break;
            }
        }

        if (room1 == null) return;

        if (game.isRandom())
            game.addRoom(room1.randomSplit());
        if (!game.isRandom())
            game.addRoom(room1.split());
    }

}
