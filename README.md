# UPM Student Cab App (JavaFX + RMI)

A desktop app built with JavaFX for booking cab rides around Universiti Putra Malaysia (UPM). Students order a ride and see the price, drivers see incoming orders and accept them, and the student and driver can chat after an order is taken. Orders and chat messages are passed between the apps through Java RMI servers.


## Features

**Student (passenger)**
- Login, sign-up and UPM student verification screens (matric number and password)
- Order a cab by choosing pickup location, destination and number of passengers
- Price is calculated automatically from a fixed price table
- Shows an error if a field is empty or if pickup and destination are the same
- Order confirmation screen with the order time
- Chat with the driver
- Popular places section, account page and log out

**Driver**
- Login, sign-up and verification screens (sign-up includes car brand and licence plate)
- Refresh button to load orders from the server
- Take an order, which marks it as "Successful" on the server
- Chat with the passenger after taking an order
- Account page and log out

**Other**
- Forgot password screen with email and a verification code (mock code: `123456`)
- Screens are sized like a phone (360 × 640)

## Locations and pricing

Prices come from a 9 × 9 price table in `PriceCalculatorPassenger.java`. The total price is the base fare multiplied by the number of passengers.

Locations: KC, KTDI, KPZ, K12, PFC, IOI City Mall, Restaurant PKS, Sultan Abdul Samad Library, MRT UPM

Base fares range from RM2 (Sultan Abdul Samad Library to MRT UPM) to RM22 (KC to MRT UPM).

## How it works

There are two RMI servers. They keep orders and messages in memory, so the data is lost when a server stops.

| Server | Port | Remote interface | Methods |
|---|---|---|---|
| `CabOrderServer` | 1099 | `CabOrderService` | `addOrder`, `getOrders`, `updateOrderStatus` |
| `ChatServer` | 50000 | `ChatInterface` | `sendMessage`, `getAllMessages` |

1. The student confirms an order, and `orderCabMethodPassenger` sends it to `CabOrderService`.
2. The driver clicks **Refresh Orders**, and `DriverClient` fetches the order list.
3. The driver clicks **Take Order**, and the order status changes from "Pending" to "Successful".
4. Both sides open their chat window, which sends and reads messages through `ChatService`.

## Project structure

```
src/sem3_project/
├── firstPage.java                 # Start screen: choose Driver or Student
├── studentPage.java / driverPage.java              # Login screens
├── verifyPageStudent.java / verifyPageDriver.java  # Verification screens
├── signupPageStudent.java / signupPageDriver.java  # Sign-up screens
├── forgotPassPage.java
├── passengerUI.java               # Student home: order a cab
├── PriceCalculatorPassenger.java  # Price table
├── orderCabMethodPassenger.java   # Order summary, confirm and send to server
├── orderPassenger.java            # Order details and chat button
├── placeDetailsPagePassenger.java # Popular place details
├── driverUI.java                  # Driver home: view and take orders
├── DriverClient.java              # Driver's RMI client
├── PassengerChatUI.java / DriverChatUI.java        # Chat windows
├── CabOrderServer.java, CabOrderService.java, CabOrderServiceImpl.java
├── ChatServer.java, ChatInterface.java, ChatServiceImpl.java
├── updateStatus.java              # Stores the current order status
├── account*, logOut*, ErrorPage*  # Account, log out and error pop-ups
└── *.png, *.jpg                   # Images and icons
```

## Requirements

- Java 17 or newer
- JavaFX SDK 21 ([download from gluonhq.com](https://gluonhq.com/products/javafx/))
- Eclipse IDE

## Setup in Eclipse

1. Clone the repo:
   ```
   git clone https://github.com/DANIAL3304/studentCab_javaFx_Project.git
   ```
2. In Eclipse, go to **File → Import → Existing Projects into Workspace** and select the folder.
3. Add JavaFX:
   - Right-click the project and choose **Build Path → Configure Build Path → Libraries**.
   - Remove the old JavaFX jars. They point to a path on the original laptop.
   - Click **Add External JARs** and select all the jars in your `javafx-sdk-21/lib` folder.
4. When you run a class that has a `main` method, add these VM arguments under **Run Configurations → Arguments**:
   ```
   --module-path "path/to/javafx-sdk-21/lib" --add-modules javafx.controls,javafx.fxml
   ```

## How to run

Run the classes in this order, each as its own **Java Application**:

1. `CabOrderServer`: starts the order server
2. `ChatServer`: starts the chat server
3. `passengerUI`: the student app
4. `driverUI`: the driver app

To see the login and sign-up screens, run `firstPage`.


## Future improvements

- Add a database for user accounts and order history
- Connect the login screens to the passenger and driver screens
- Separate chat rooms for each order
- Show the driver's details to the passenger after an order is accepted
- Real email verification
