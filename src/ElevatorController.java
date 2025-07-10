
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElevatorController {
    private final List<Elevator> cars;
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public ElevatorController(int numCars, int maxFloor) {
        cars = new ArrayList<>(numCars);
        for (int i = 0; i < numCars; i++) {
            Elevator e = new Elevator(i + 1, maxFloor);
            cars.add(e);
            pool.submit(e);
        }
    }

    public void pickup(int floor, Direction dir) {
        Request r = new Request(floor, dir);
        Elevator chosen = chooseCar(floor, dir);
        chosen.addRequest(r);
        System.out.printf("Request (floor %d, %s) → Elevator %d%n", floor, dir, chosen.getId());
    }

    private Elevator chooseCar(int floor, Direction dir) {
        Elevator best = null;
        int bestScore = Integer.MAX_VALUE;

        for (Elevator e : cars) {
            int distance = Math.abs(e.getCurrentFloor() - floor);
            boolean aligned = (e.getState() == Direction.IDLE) ||
                    (e.getState() == dir &&
                            ((dir == Direction.UP && e.getCurrentFloor() <= floor) ||
                                    (dir == Direction.DOWN && e.getCurrentFloor() >= floor)));
            int score = aligned ? distance : distance + 100;
            if (score < bestScore) {
                bestScore = score;
                best = e;
            }
        }
        return best;
    }

    public void shutdown() {
        pool.shutdownNow();
    }
}
