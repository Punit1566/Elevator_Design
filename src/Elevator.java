import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.Comparator;

public class Elevator implements Runnable {
    private final int id;
    private final int maxFloor;
    private int currentFloor = 0;
    private Direction state = Direction.IDLE;
    private final BlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    public Elevator(int id, int maxFloor) {
        this.id = id;
        this.maxFloor = maxFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getState() {
        return state;
    }

    public int getId() {
        return id;
    }

    public void addRequest(Request r) {
        queue.offer(r);
    }

    private void moveOneFloor(Direction dir) throws InterruptedException {
        if (dir == Direction.UP && currentFloor < maxFloor) {
            currentFloor++;
        } else if (dir == Direction.DOWN && currentFloor > 0) {
            currentFloor--;
        }
        Thread.sleep(300);
        System.out.printf("Elevator %d ➔ floor %d%n", id, currentFloor);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Request req = queue.poll(250, TimeUnit.MILLISECONDS);
                if (req == null) {
                    state = Direction.IDLE;
                    continue;
                }

                state = (req.floor >= currentFloor) ? Direction.UP : Direction.DOWN;

                while (currentFloor != req.floor) {
                    moveOneFloor(state);
                }

                System.out.printf("Elevator %d PICKUP at %d (%s request)%n", id, currentFloor, req.direction);
                state = req.direction;
                serviceAlongCurrentDirection();
                state = Direction.IDLE;
            }
        } catch (InterruptedException stop) {
            System.out.printf("Elevator %d terminating …%n", id);
        }
    }

    private void serviceAlongCurrentDirection() throws InterruptedException {
        while (true) {
            Optional<Request> next = queue.stream()
                    .filter(r -> r.direction == state &&
                            ((state == Direction.UP && r.floor >= currentFloor) ||
                                    (state == Direction.DOWN && r.floor <= currentFloor)))
                    .min(Comparator.comparingInt(r -> Math.abs(r.floor - currentFloor)));

            if (next.isEmpty()) break;

            queue.remove(next.get());
            while (currentFloor != next.get().floor) {
                moveOneFloor(state);
            }
            System.out.printf("Elevator %d PICKUP en route at %d (%s)%n", id, currentFloor, state);
        }
    }
}
