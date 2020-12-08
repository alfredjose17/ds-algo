from abc import ABC, abstractmethod
from enum import Enum


class RoomType(Enum):
    SINGLE, DOUBLE, TRIPLE = 1, 2, 3


class Room(ABC):
    def __init__(self, type, price):
        self.type = type
        self.price = price
        self.rooms = []


class SingleRoom(Room):
    def __init__(self, price, floors, rooms):
        super().__init__(RoomType.SINGLE, price)
        for i in range(floors):
            start = ((i + 1) * 100) + 1
            for j in range(start, start + (rooms//3)):
                self.rooms.append(str(j) + "A")


class DoubleRoom(Room):
    def __init__(self, price, floors, rooms):
        super().__init__(RoomType.DOUBLE, price)
        for i in range(floors):
            start = ((i + 1) * 100) + 1 + (rooms//3)
            for j in range(start, start + (rooms//3)):
                self.rooms.append(str(j) + "B")


class TripleRoom(Room):
    def __init__(self, price, floors, rooms):
        super().__init__(RoomType.TRIPLE, price)
        for i in range(floors):
            start = ((i + 1) * 100) + 1 + 2*(rooms//3)
            for j in range(start, start + (rooms//3)):
                self.rooms.append(str(j) + "C")