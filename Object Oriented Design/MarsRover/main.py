from rover import Rover


if __name__ == "__main__":

    while True:
        #Menu
        print('\t\t\tMenu')
        print('\t\t1. Add Rover')
        print('\t\t2. Exit')
        choice = int(input("\tEnter Your Choice: "))

        if choice == 1:
            xCord, yCord, direction = input("\n\tEnter Details: ").split()
            roverObject = Rover(xCord, yCord, direction)
            instructions = list(input("\tEnter Instructions: "))
            roverObject.move(instructions)
            roverObject.getPosition()

        else:
            print("\n\t\tThank You!")
            break
