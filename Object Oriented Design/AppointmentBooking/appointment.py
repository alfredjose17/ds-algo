from doctor import Doctor
from patient import Patient


class Appointment:
    def __init__(self):
        self.__doctors = []

    def addDoctor(self, doctor):
        self.__doctors.append(doctor)

    def assignDoctor(self, patient):
        #Checking for available Doctors
        for doctor in self.__doctors:
            if patient.getSpecialization() == doctor.getSpecialization():
                if patient.getDay() == doctor.getDay():
                    if patient.getFromTime() >= doctor.getFromTime() and patient.getToTime() =< doctor.getToTime():
                        patient.addDoctor(doctor)
                        print(f'\n\tYou are assigned to Dr.{doctor.getName()}')
                        return
        print("\n\t\tNo Doctors Available!")
        