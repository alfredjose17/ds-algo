class ParkingLot:
    __ticket_id = 0

    def __init__(self, name, zipcode, capacity, rate):
        self.__name = name
        self.__zipcode = zipcode
        self.__capacity = capacity
        self.__rate = rate
        self.__smallSpots = []
        self.__mediumSpots = []
        self.__largeSpots = []
        self.__vehicleList = {}

    def park_vehicle(self, vehicle):
        if vehicle.get_type() == "1":
            #Appending to stack
            self.__smallSpots.append(vehicle)
            ParkingLot.__ticket_id += 1
            vehicle.assign_ticket(ParkingLot.__ticket_id, len(self.__smallSpots) - 1)
            #Adding to hashmap
            self.__vehicleList[ParkingLot.__ticket_id] = vehicle

        elif vehicle.get_type() == "2":
            #Appending to stack
            self.__mediumSpots.append(vehicle)
            ParkingLot.__ticket_id += 1
            vehicle.assign_ticket(ParkingLot.__ticket_id, len(self.__mediumSpots) - 1)
            #Adding to hashmap
            self.__vehicleList[ParkingLot.__ticket_id] = vehicle

        elif vehicle.get_type() == "3":
            #Appending to stack
            self.__largeSpots.append(vehicle)
            ParkingLot.__ticket_id += 1
            vehicle.assign_ticket(ParkingLot.__ticket_id, len(self.__largeSpots) - 1)
            #Adding to hashmap
            self.__vehicleList[ParkingLot.__ticket_id] = vehicle

    def remove_vehicle(self, ticket_id):
        try:
            my_spot = self.__vehicleList.pop(ticket_id)
            if my_spot.get_type() == "1":
                self.__smallSpots.pop(my_spot.get_position())
                print(f"\n\t\tVehicle {ticket_id} removed!\n")
            elif my_spot.get_type() == "2":
                self.__mediumSpots.pop(my_spot.get_position())
                print(f"\n\t\tVehicle {ticket_id} removed!\n")
            elif my_spot.get_type() == "3":
                self.__largeSpots.pop(my_spot.get_position())
                print(f"\n\t\tVehicle {ticket_id} removed!\n")
        except:
            print("\n\t\tVehicle not found!\n")


    def isFull(self, type):
        if type == "1":
            return len(self.__smallSpots) < (self.__capacity // 3)
        elif type == "2":
            return len(self.__mediumSpots) < (self.__capacity // 3)
        elif type == "3":
            return len(self.__largeSpots) < (self.__capacity // 3)


class Vehicles:
    total = 0

    def __init__(self, license_number, type):
        self.__license_number = license_number
        self.__type = type
        self.__ticket = None
        self.__position = None
        Vehicles.total += 1

    def get_license_number(self):
        return self.__license_number   

    def get_type(self):
        return self.__type

    def assign_ticket(self, ticket, position):
        self.__ticket = ticket
        self.__position = position

    def get_position(self):
        return self.__position

    def bill(self):
        print(f"\n\t\tWelcome\n\tTicket ID: {self.__ticket}")
        print(f"\tLicense Number: {self.__license_number}")
        if self.__type == "1":
            print(f"\tVehicle Type: MotorCycle\n")
        elif self.__type == "2":
            print(f"\tVehicle Type: Car\n")
        elif self.__type == "3":
            print(f"\tVehicle Type: Bus\n")


if __name__ == "__main__":
    parkingLot1 = ParkingLot("Lulu", "682024", 6, 100)

    # Menu
    print("\t\t\tMENU\n\t1. Park Vehicle\n\t2. Remove Car\n\t3. Exit\n")

    while True:
        choice = int(input("\tEnter your choice: "))
        if choice == 1:
            type = input("\n\tEnter Type(1.MotorCycle / 2.Car / 3.Bus): ")
            if parkingLot1.isFull(type):
                license_number = input("\tEnter License Number: ")
                vobj = Vehicles(license_number, type)
                parkingLot1.park_vehicle(vobj)
                vobj.bill()
            else:
                print("\n\t\tParking Full!\n")

        elif choice == 2:
            ticket_id = int(input("\n\tEnter Ticket ID: "))
            parkingLot1.remove_vehicle(ticket_id)

        elif choice == 3:
            print("\n\t\tThank You!")
            break