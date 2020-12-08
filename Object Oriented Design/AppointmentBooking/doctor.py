class Doctor:
    def __init__(self, name, specialization, availableDay, availableFrom, availableTo):
        self.__name = name
        self.__specialization = specialization
        self.__availableDay = availableDay
        self.__availableFrom = availableFrom
        self.__availableTo = availableTo

    def getSpecialization(self):
        return self.__specialization

    def getDay(self):
        return self.__availableDay

    def getFromTime(self):
        return self.__availableFrom

    def getToTime(self):
        return self.__availableTo

    def getName(self):
        return self.__name
        