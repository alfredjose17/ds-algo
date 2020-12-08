class Customer:
    def __init__(self, name, single, double, triple, ac, days):
        self.__name = name
        self.__single_needed = single
        self.__double_needed = double
        self.__triple_needed = triple
        self.__ac = ac
        self.__days = days

    def assign_rooms(self, bill_id, rooms, price):
        self.__bill_id = bill_id
        self.__rooms = rooms
        self.__price = price

    def print_bill(self):
        print(f'\n\t\tWelcome {self.__name}')
        print(f'\tBill ID: {self.__bill_id}')
        print(f'\tRooms: {self.__rooms}')
        print(f'\tPrice: {self.__price}')

    def get_single(self):
        return self.__single_needed

    def get_double(self):
        return self.__double_needed

    def get_triple(self):
        return self.__triple_needed

    def get_ac(self):
        return self.__ac

    def get_days(self):
        return self.__days

    def get_rooms(self):
        return self.__rooms

    def get_name(self):
        return self.__name
