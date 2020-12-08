from product import Product, ImportedProduct


class Customer:
    def __init__(self, name, phone):
        self.__name = name
        self.__phone = phone
        self.__products = []     # To store the products entered by user

    def addProduct(self, product):
        self.__products.append(product)

    def printBill(self):
        print("\n\t\tReciept")
        count = 1
        for product in self.__products:
            print(f'\t{count}.{product.getName()} - {product.getQuantity()} - {product.getPrice()}')
        print(f'\n\tThank You {self.__name}!\n')

