from rooms import SingleRoom, DoubleRoom, TripleRoom
from customer import Customer


class Hotel:
    bill_id = 0
    def __init__(self, name, zipcode, floors, rooms, single_price, double_price, triple_price, ac_price):
        self.__name = name
        self.__zipcode = zipcode
        self.__floors = floors
        self.__rooms = rooms
        self.__single = SingleRoom(single_price, floors, rooms)
        self.__double = DoubleRoom(double_price, floors, rooms)
        self.__triple = TripleRoom(triple_price, floors, rooms)
        self.__ac_price = ac_price
        self.__single_left = floors * (rooms // 3)
        self.__double_left = floors * (rooms // 3)
        self.__triple_left = floors * (rooms // 3)
        self.__customers = {}

    def book_room(self, customer):
        rooms = []
        for i in range(customer.get_single()):
            rooms.append(self.__single.rooms.pop(0))
            self.__single_left -= 1
        for i in range(customer.get_double()):
            rooms.append(self.__double.rooms.pop(0))
            self.__double_left -= 1
        for i in range(customer.get_triple()):
            rooms.append(self.__triple.rooms.pop(0))
            self.__triple_left -= 1
        Hotel.bill_id += 1
        price = self.__single.price * customer.get_single() + self.__double.price * customer.get_double() + self.__triple.price * customer.get_triple()
        if customer.get_ac():
            price += self.__ac_price
        price *= customer.get_days()
        customer.assign_rooms(Hotel.bill_id, rooms, price)
        self.__customers[Hotel.bill_id] = customer

    def vaccate_room(self, bill_id):
        try:
            customer = self.__customers.pop(bill_id)
            for i in customer.get_rooms():
                if 'A' in i:
                    self.__single.rooms.append(i)
                    self.__single_left += 1
                elif 'B' in i:
                    self.__double.rooms.append(i)
                    self.__double_left += 1
                else:
                    self.__triple.rooms.append(i)
                    self.__triple_left += 1
            print(f'\n\t\tThank You {customer.get_name()}!')
        except:
            print("\n\t\tInvalid Bill ID!")

    def rooms_available(self):
        print(f'\tSingle - {self.__single_left}')
        print(f'\tDouble - {self.__double_left}')
        print(f'\tTriple - {self.__triple_left}')

    def check_available(self, single, double, triple):
        return self.__single_left >= single and self.__double_left >= double and self.__triple_left >= triple
