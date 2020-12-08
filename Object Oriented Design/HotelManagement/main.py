from hotel import Hotel
from customer import Customer


if __name__ == "__main__":
    hotel1 = Hotel("Mariotte", "682024", 5, 3, 2000, 3000, 4000, 1000)

    # Menu
    print("\t\t\tMENU\n\t1. Book Rooms\n\t2. Vaccate Rooms\n\t3. Exit")

    while True:
        choice = int(input("\n\tEnter your choice: "))
        if choice == 1:
            name = input("\n\tEnter your Name: ")
            print(f'\n\t\tRooms Available')
            hotel1.rooms_available()
            single, double, triple = map(int, input("\tEnter Number of Single/Double/Triple Rooms(Space Seperated): ").split())
            if hotel1.check_available(single, double, triple):
                ac = input("\tDo you wish to have AC(Y/N): ")
                if ac == 'y' or 'Y':
                    ac = True
                else:
                    ac = False
                days = int(input("\tEnter Number of Days: "))
                customer_obj = Customer(name, single, double, triple, ac, days)
                hotel1.book_room(customer_obj)
                customer_obj.print_bill()
            else:
                print("\n\t\tRooms Not Available!")

        elif choice == 2:
            bill_id = int(input("\n\tEnter Bill ID: "))
            hotel1.vaccate_room(bill_id)

        elif choice == 3:
            print("\n\t\tThank You!")
            break