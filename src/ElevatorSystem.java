import java.util.Scanner;

public class ElevatorSystem {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of elevators: ");
        int numElevators = scanner.nextInt();

        System.out.print("Enter the maximum floor number: ");
        int maxFloor = scanner.nextInt();

        ElevatorController ctl = new ElevatorController(numElevators, maxFloor);

        System.out.println("\nEnter elevator pickup requests (e.g., 5 UP), type 'exit' to stop:");

        while (true) {
            System.out.print("Request: ");
            String input = scanner.next();

            if ("exit".equalsIgnoreCase(input)) {
                break;
            }

            try {
                int floor = Integer.parseInt(input);
                String directionInput = scanner.next().toUpperCase();

                Direction direction;
                if ("UP".equals(directionInput)) {
                    direction = Direction.UP;
                } else if ("DOWN".equals(directionInput)) {
                    direction = Direction.DOWN;
                } else {
                    System.out.println("Invalid direction. Please enter UP or DOWN.");
                    continue;
                }

                if (floor < 0 || floor > maxFloor) {
                    System.out.println("Invalid floor number. Must be between 0 and " + maxFloor);
                    continue;
                }

                ctl.pickup(floor, direction);

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a floor number followed by a direction (UP/DOWN).");
                scanner.nextLine(); // clear input buffer
            }
        }

        System.out.println("\nWaiting for elevators to finish requests...");
        Thread.sleep(10000);
        ctl.shutdown();
        scanner.close();
        System.out.println("Elevator system terminated.");
    }
}
