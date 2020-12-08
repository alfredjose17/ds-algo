from appointment import Appointment
from doctor import Doctor
from patient import Patient


# To Convert time
def calculateTime(availableFrom):
    availableFrom = availableFrom.split(":")
    availableFrom = (int(availableFrom[0]) * 60) + int(availableFrom[1])
    return availableFrom


if __name__ == "__main__":
    system1 = Appointment()

    while True:
        # Menu
        print("\n\t\t\tMENU")
        print("\t\t1. Add Doctor")
        print("\t\t2. Add Patient")
        print("\t\t3. Exit")
        choice = int(input("\tEnter your choice: "))

        if choice == 1:
            name = input("\n\tEnter Name: ")
            #Print Specializations
            print("\n\t\t1. Pediatrician\n\t\t2. ENT\n\t\t3. GP")
            doctorSpec = int(input("\tEnter Specialization: "))
            #Print Days
            print("\n\t\t1. Monday\n\t\t2. Tuesday\n\t\t3. Wednesday\n\t\t4. Thursday\n\t\t5. Friday\n\t\t6. Saturday\n\t\t7. Sunday")
            availableDay = int(input("\tEnter Available Day: "))
            availableFrom = input("\tEnter Available From Time(00:00): ")
            availableFrom = calculateTime(availableFrom)
            availableTo = input("\tEnter Available To Time(00:00): ")
            availableTo = calculateTime(availableTo)
            doctorObject = Doctor(name, doctorSpec, availableDay, availableFrom, availableTo)
            system1.addDoctor(doctorObject)

        elif choice == 2:
            name = input("\n\tEnter Name: ")
            #Print Specializations
            print("\n\t\t1. Pediatrician\n\t\t2. ENT\n\t\t3. GP")
            patientSpec = int(input("\tEnter Specialization Required: "))
            #Print Days
            print("\n\t\t1. Monday\n\t\t2. Tuesday\n\t\t3. Wednesday\n\t\t4. Thursday\n\t\t5. Friday\n\t\t6. Saturday\n\t\t7. Sunday")
            day = int(input("\tEnter Required Day: "))
            fromTime = input("\tEnter From Time(00:00): ")
            fromTime = calculateTime(fromTime)
            toTime = input("\tEnter To Time(00:00): ")
            toTime = calculateTime(toTime)
            patientObject = Patient(name, patientSpec, day, fromTime, toTime)
            system1.assignDoctor(patientObject)

        elif choice == 3:
            print("\n\t\tThank You!")
            break
        
        else:
            print("\n\t\tInvalid Choice!")
            continue