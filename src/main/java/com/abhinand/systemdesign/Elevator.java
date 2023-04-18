package com.abhinand.systemdesign;


import java.util.*;


enum REQ_TYPE {
    ONBOARD, DESTINATION,

}

class Clock {
    public static int time;

    public static int getTime() {
        return time++;
    }
}

enum DIRECTION {
    UP, DOWN, STILL
}

class ERequest {
    public REQ_TYPE type;

    public DIRECTION direction;
    public Integer floor;

    public int time;

    public boolean processed = false;

    public ERequest(int floor) {
        this.type = REQ_TYPE.DESTINATION;
        this.floor = floor;
        this.direction = DIRECTION.STILL;
        time = Clock.getTime();
    }

    public ERequest(DIRECTION direction, int floor) {
        this.type = REQ_TYPE.ONBOARD;
        this.direction = direction;
        this.floor = floor;
        time = Clock.getTime();
    }

    public int getTime() {
        return time;
    }
}

public class Elevator {
    private final int startFloor;
    private final int endFloor;

    private final Set<ERequest> requests;

//    HashMap<Integer, ERequest> floorRequests;

    // Elevator state
    private int currFloor;
    private boolean on;
    private int weight;
    private boolean close;

    private DIRECTION direction;


    public Elevator(int startFloor, int endFloor) {
        this.startFloor = startFloor;
        this.endFloor = endFloor;
        requests = new HashSet<>();
        this.close = true;
        this.weight = 0;
    }

    private void addRequest(ERequest request) {
        synchronized (this) {
            if (request.floor < startFloor | request.floor > endFloor) {
                throw new IllegalArgumentException();
            }
            requests.add(request);
        }
    }

    public int getDestination() {
        synchronized (this) {
            requests.stream().filter(x -> x.floor == getCurrFloor())
                    .filter(x -> x.type == REQ_TYPE.DESTINATION || x.type == REQ_TYPE.ONBOARD && x.direction == direction)
                    .forEach(x -> x.processed = true);
            Optional<Integer> destination = requests.stream()
                    .filter(x -> x.processed)
                    .map(x -> x.floor).findAny();
            requests.removeIf(x -> x.processed);
            return destination.orElseGet(() ->
                    requests.stream()
                            .min(Comparator.comparingInt(ERequest::getTime))
                            .map(x -> x.floor)
                            .orElse(getCurrFloor()));
        }

    }

    public void moveUp() {
        sleep(5);
        this.currFloor += 1;
        direction = DIRECTION.UP;
    }

    private static void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void moveDown() {
        sleep(5);
        direction = DIRECTION.DOWN;
        this.currFloor -= 1;
    }

    public void open() {
        close = false;
    }

    public void close() {
        if (getWeight() > 800) {
            return;
        }
        close = true;
    }

    private int getWeight() {
        //TODO: fetch from weight sensor
        return weight;
    }


    public void start() {
        on = true;
        while (on) {
            System.out.println("Elevator in "+ getCurrFloor() );
            if (getCurrFloor() == getDestination()) {
                open();
                System.out.println("Opened on Floor " + getCurrFloor());
                while (!close) {
                    sleep(10);
                    close();
                }
                while (requests.isEmpty() && getCurrFloor() == getDestination()) {
                    sleep(1);
                };
            } else if (getCurrFloor() < getDestination()) {
                moveUp();
            } else {
                moveDown();
            }
        }
    }

    public void stop() {
        on = false;
    }

    private int getCurrFloor() {
        return this.currFloor;
    }

    public static void main(String[] args) {
        Elevator elevator = new Elevator(0, 100);
        Thread elevatorThread = new Thread(elevator::start);
        elevatorThread.start();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter Request Type \n");
            System.out.println("1: Destination");
            System.out.println("2: Onboard");
            int type = sc.nextInt();
            if (type == 1) {
                System.out.println("Enter Floor");
                int floor = sc.nextInt();
                elevator.addRequest(new ERequest(floor));
            } else if (type == 2) {
                System.out.println("Enter UP or DOWN");
                String direction = sc.next();
                System.out.println("Enter your floor");
                int floor = sc.nextInt();
                elevator.addRequest(new ERequest(DIRECTION.valueOf(direction), floor));
            }

        }
    }

    public static void simulate(Elevator elevator){

    }
}
