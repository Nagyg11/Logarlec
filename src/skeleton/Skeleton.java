package skeleton;

import java.util.Scanner;

public class Skeleton {

    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        String input;
        boolean isRunning = true;
        while (isRunning) {
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    test1();
                    break;
                case "2":
                    test2();
                    break;
                case "3":
                    test3();
                    break;
                case "4":
                    test4();
                    break;
                case "5":
                    test5();
                    break;
                case "6":
                    test6();
                    break;
                case "7":
                    test7();
                    break;
                case "8":
                    test8();
                    break;
                case "9":
                    test9();
                    break;
                case "10":
                    test10();
                    break;
                case "11":
                    test11();
                    break;
                case "12":
                    test12();
                    break;
                case "13":
                    test13();
                    break;
                case "14":
                    test14();
                    break;
                case "15":
                    test15();
                    break;
                case "16":
                    test16();
                    break;
                case "17":
                    test17();
                    break;
                case "18":
                    test18();
                    break;
                case "19":
                    test19();
                    break;
                case "close":
                    isRunning = false;
                    break;
            }
        }
        scanner.close();
    }

    public static void test1() {
        System.out.println("Init");
        Room curr = new Room();
        Prof p = new Prof();
        Beer b = new Beer();

        p.setRoom(curr);
        curr.addItem(b);
        curr.addEntity(p);
        System.out.println("Init");

        p.pickUp(b);
    }

    public static void test2() {
        System.out.println("Init");
        Beer b = new Beer();
        Student s = new Student();

        s.addItem(b);
        System.out.println("Init");

        s.activateItem(b);
    }

    public static void test3() {
        System.out.println("Init");
        Camembert c = new Camembert();
        Room room = new Room();
        Student s = new Student();

        s.setRoom(room);
        s.addItem(c);
        room.addEntity(s);
        System.out.println("Init");

        s.activateItem(c);
    }

    public static void test4() {
        System.out.println("Init");
        WetSponge w = new WetSponge();
        Room room = new Room();
        Student s = new Student();

        s.setRoom(room);
        s.addItem(w);
        room.addEntity(s);
        System.out.println("Init");

        s.activateItem(w);
    }

    public static void test5() {
        System.out.println("Init");
        Game game = new Game();
        Room room = new Room();
        Student student = new Student();
        Logarlec logar = new Logarlec();

        student.setRoom(room);
        student.setGame(game);
        room.addItem(logar);
        room.addEntity(student);
        game.addRoom(room);
        game.addEntity(student);
        System.out.println("Init");

        student.pickUp(logar);
    }

    public static void test6() {
        System.out.println("Init");
        Prof prof = new Prof();
        Logarlec logar = new Logarlec();
        Room room = new Room();

        prof.setRoom(room);
        room.addItem(logar);
        room.addEntity(prof);
        System.out.println("Init");

        prof.pickUp(logar);
    }

    public static void test7() {
        System.out.println("Init");
        TVSZ tvsz = new TVSZ();
        Room r = new Room();
        Student s = new Student();

        s.setRoom(r);
        s.addItem(tvsz);
        r.addEntity(s);
        System.out.println("Init");

        s.drop(tvsz);
    }

    public static void test8() {
        System.out.println("Init");
        Game game = new Game();
        Room r = new Room();
        Student s = new Student();
        TVSZ tvsz = new TVSZ();

        s.setRoom(r);
        s.setGame(game);
        s.addItem(tvsz);
        r.addEntity(s);
        game.addRoom(r);
        game.addEntity(s);
        System.out.println("Init");

        s.kill();
    }

    public static void test9() {
        System.out.println("Init");
        Transistor t1 = new Transistor();
        Transistor t2 = new Transistor();
        Student s = new Student();

        s.addItem(t1);
        s.addItem(t2);
        System.out.println("Init");

        s.linkItems(t1, t2);
    }

    public static void test10() {
        System.out.println("Init");
        Transistor tr = new Transistor();
        Transistor pair = new Transistor();
        Room newRoom = new Room();
        Room room = new Room();
        Student s = new Student();
        newRoom.setCapacity(10);
        room.setCapacity(10);

        s.setRoom(room);
        newRoom.addItem(pair);
        tr.link(pair);
        s.addItem(tr);
        room.addEntity(s);
        pair.setRoom(newRoom);
        System.out.println("Init");

        s.activateItem(tr);
    }

    public static void test11() {
        System.out.println("Init");
        Door door = new Door();
        Room src = new Room();
        Room dest = new Room();
        Prof prof = new Prof();
        src.setCapacity(10);
        dest.setCapacity(10);

        prof.setRoom(src);
        door.setNeighbours(dest, src);
        src.addEntity(prof);
        src.addDoor(door);
        dest.addDoor(door);
        System.out.println("Init");

        prof.move(dest, door);
    }

    public static void test12() {
        System.out.println("Init");
        Room dest = new Room();
        Room src = new Room();
        Door door = new Door();
        Student student = new Student();
        src.setCapacity(10);
        dest.setCapacity(10);

        student.setRoom(src);
        door.setNeighbours(dest, src);
        src.addEntity(student);
        src.addDoor(door);
        dest.addDoor(door);
        System.out.println("Init");

        student.move(dest, door);
    }

    public static void test13() {
        System.out.println("Init");
        Room curr = new Room();
        Transistor tr = new Transistor();
        Student s = new Student();

        s.setRoom(curr);
        curr.addItem(tr);
        curr.addEntity(s);
        System.out.println("Init");

        s.pickUp(tr);
    }

    public static void test14() {
        System.out.println("Init");
        Room src = new Room();
        Room dest = new Room();
        Door door = new Door();
        FFP2 ffp2 = new FFP2();
        Student stud = new Student();
        src.setCapacity(10);
        dest.setCapacity(10);

        stud.setRoom(src);
        door.setNeighbours(dest, src);
        stud.addItem(ffp2);
        src.addEntity(stud);
        src.addDoor(door);
        dest.addDoor(door);
        System.out.println("Init");

        stud.move(dest, door);
    }

    public static void test15() {
        System.out.println("Init");
        Room r1 = new Room();
        Room r2 = new Room();

        r1.setCapacity(10);
        r2.setCapacity(11);
        System.out.println("Init");

        r1.merge(r2);
    }

    public static void test16() {
        System.out.println("Init");
        Door d1 = new Door();
        Door d2 = new Door();
        FFP2 ffp2 = new FFP2();
        Logarlec logar = new Logarlec();
        Room room = new Room();
        Room randomRoom1 = new Room();
        Room randomRoom2 = new Room();

        d1.setNeighbours(room, randomRoom1);
        d2.setNeighbours(room, randomRoom2);
        room.addItem(ffp2);
        room.addItem(logar);
        room.addDoor(d1);
        room.addDoor(d2);
        System.out.println("Init");

        room.split();
    }

    public static void test17() {
        System.out.println("Init");
        Student student = new Student();
        System.out.println("Init");

        student.tick();
    }

    public static void test18() {
        System.out.println("Init");
        Student sM = new Student();
        Student sNM = new Student();
        Prof p = new Prof();
        FFP2 ffp2 = new FFP2();
        Room curr = new Room();

        sM.setRoom(curr);
        sNM.setRoom(curr);
        p.setRoom(curr);
        curr.addEntity(sM);
        curr.addEntity(sNM);
        curr.addEntity(p);
        sM.addItem(ffp2);
        System.out.println("Init");

        curr.tick();
    }

    public static void test19() {
        System.out.println("Init");
        Student s = new Student();
        Student sInRoom = new Student();
        Prof pInRoom = new Prof();
        Room src = new Room();
        Room dest = new Room();
        Game game = new Game();
        Beer beer = new Beer();
        TVSZ tvsz = new TVSZ();
        Door door = new Door();

        s.setGame(game);
        s.setRoom(src);
        src.addEntity(s);
        sInRoom.setRoom(dest);
        dest.addEntity(sInRoom);
        pInRoom.setRoom(dest);
        dest.addEntity(pInRoom);
        door.setNeighbours(dest, src);
        src.addDoor(door);
        dest.addDoor(door);
        src.setCapacity(10);
        dest.setCapacity(10);
        s.addItem(beer);
        s.addItem(tvsz);
        game.addRoom(src);
        game.addRoom(dest);
        game.addEntity(s);
        game.addEntity(sInRoom);
        game.addEntity(pInRoom);
        System.out.println("Init");

        s.move(dest, door);
    }
}
