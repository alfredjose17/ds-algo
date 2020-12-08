from customer import Customer
from product import Product, ImportedProduct


if __name__ == "__main__":
    
    while True:
        # Menu
        print("\t\t\tMENU")
        print("\t\t1. Use TaxBot")
        print("\t\t2. Exit")
        choice = int(input("\n\tEnter Choice: "))

        if choice == 1:
            name = input("\n\tEnter Customer Name: ")
            phone = input("\tEnter Mobile Number: ")
            customerObject = Customer(name, phone)

            while True:
                choiceProduct = input("\tDo you wish to Add Products(Y/N): ")
                if choiceProduct == 'y' or choiceProduct == 'Y':
                    # Product List
                    print("\n\t\t1. Food")
                    print("\t\t2. Book")
                    print("\t\t3. Medicine")
                    print("\t\t4. Others")
                    type = input("\tEnter Type of Product: ")
                    productName = input("\tEnter Product Name: ")
                    price = float(input("\tEnter Price: "))
                    quantity = int(input("\tEnter Quantity: "))
                    imported = input("\tIf Imported(Y/N): ")
                    if imported == 'y' or imported == 'Y':
                        productObject = ImportedProduct(productName, type, price, quantity)
                    else:
                        productObject = Product(productName, type, price, quantity)
                    customerObject.addProduct(productObject)
                else:
                    break
                
            customerObject.printBill()

        else:
            print("\n\t\tThank You!")
            break
