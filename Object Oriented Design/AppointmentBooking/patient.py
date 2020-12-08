from doctor import Doctor


class Patient:
    ID = 0
    def __init__(self, name, specialization, day, fromTime, toTime):
        Patient.ID += 1
        self.__patientID = Patient.ID
        self.__name = name
        self.__specialization = specialization
        self.__day = day
        self.__fromTime = fromTime
        self.__toTime = toTime
        self.__doctors = []

    def getID(self):
        return self.__patientID

    def getSpecialization(self):
        return self.__specialization

    def getDay(self):
        return self.__day

    def getFromTime(self):
        return self.__fromTime

    def getToTime(self):
        return self.__toTime

    def addDoctor(self, doctor):
        self.__doctors.append(doctor)
