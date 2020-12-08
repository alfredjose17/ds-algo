class Product:
    def __init__(self, name, type, price, quantity):
        self.__name = name
        self.__type = type
        self._price = price
        # Adding Sales Tax for Other Types
        if self.__type == 4:
            self.__price += self.__price * 0.10
        self.__quantity = quantity

    def getName(self):
        return self.__name

    def getPrice(self):
        return self._price

    def getQuantity(self):
        return self.__quantity
        

class ImportedProduct(Product):
    def __init__(self, name, type, price, quantity):
        super().__init__(name, type, price, quantity)
        # Adding Import Tax
        self._price += self._price * 0.05

    