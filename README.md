# 🚪 Elevator Design

This project simulates a dynamic, multi-elevator system using Java concurrency. Elevators respond in real-time to user-generated requests, efficiently managing simultaneous calls.


## ✨ Features

* 🚀 Multiple elevators operating concurrently.
* 🎯 Dynamic user-generated elevator requests.
* 📍 Efficient elevator assignment based on proximity and requested direction.
* 📊 Real-time console feedback on elevator movements and request handling.

---

## 📝 Usage

When launched, you'll be asked to enter:

* Number of elevators
* Maximum number of floors
* Elevator requests (floor number followed by direction: `UP` or `DOWN`)

To end input, type:

```
exit
```

### 🎬 Example Input

```
Enter number of elevators: 2
Enter the maximum floor number: 10

Enter elevator pickup requests (e.g., 5 UP), type 'exit' to stop:
Request: 3 UP
Request: 7 DOWN
Request: exit
```

---

