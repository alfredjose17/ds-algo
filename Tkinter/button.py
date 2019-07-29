from tkinter import *

def closeWindow():
	exit()

master = Tk()
button1 = Button(master, text = "Close", command = closeWindow)

button1.pack()

mainloop()