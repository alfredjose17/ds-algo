DIR = {'N':0, 'E':1, 'S':2, 'W':3}

class Rover:
    def __init__(self, xCord, yCord, direction):
        self.__position = [int(xCord), int(yCord)]
        self.__direction = DIR[direction]

    def move(self, instructions):
        for instruction in  instructions:
            if instruction == 'L':
                if self.__direction == 0:
                    self.__direction = 3
                else:
                    self.__direction -= 1
            elif instruction == 'R':
                self.__direction = (self.__direction + 1) % 4
            else:
                if self.__direction == 0:
                    self.__position[1] += 1
                elif self.__direction == 1:
                    self.__position[0] += 1
                elif self.__direction == 2:
                    self.__position[1] -= 1
                elif self.__direction == 3:
                    self.__position[0] -= 1

    def getPosition(self):
        for key, value in DIR.items(): 
            if value == self.__direction:
                finalDir = key
                break
        print(f'\n\tFinal Position: {self.__position[0]} {self.__position[1]} {finalDir}')