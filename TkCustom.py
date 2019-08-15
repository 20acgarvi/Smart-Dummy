from tkinter import *
from tkinter import ttk
from tkinter import filedialog
from PIL import ImageTk, Image

class EmptyPopup(Tk):
    def __init__(self, *args, **kwargs):
        Tk.__init__(self, *args, **kwargs)

class AddPopup(Tk):
    def __init__(self, *args, **kwargs):
        Tk.__init__(self, *args, **kwargs)
        self.geometry("575x350")
        self.frame()
        
    def frame(self):
        #intializes the frame
        self.canvas = Canvas(self)
        self.canvas.pack(expand = 1, fill = "both")
        #add images
        try:
            self.image = ImageTk.PhotoImage(Image.open(
                "/Users/arthurgarvin/Downloads/chicken-man-seed.jpg"
                ))
            self.canvas.create_image((20,20), image = self.image, anchor = NW)
        except FileNotFoundError:
            pass
        self.canvas.create_rectangle(210, 20, 250, 60, fill = "")
        self.canvas.create_rectangle(260, 20, 300, 60, fill = "")
        self.canvas.create_rectangle(210, 70, 250, 110, fill = "")
        self.canvas.create_rectangle(260, 70, 300, 110, fill = "")
        self.canvas.create_rectangle(210, 120, 250, 160, fill = "")
        self.canvas.create_rectangle(260, 120, 300, 160, fill = "")

        #adds buttons and entries
        self.label = Label(self, text = "Name:")
        self.label.place(x = 310, y = 20)
        self.name_entry = Entry(self)
        self.name_entry.place(x = 360, y = 20)
        self.button = Button(self, text = "Upload Image")
        self.button.place(x = 60, y = 210)
        self.button2 = Button(self, text = "Add Scenario")
        self.button2.place(x = 460, y = 310)

    def upload(self):
        filename = filedialog.askopenfilename()
        #

if __name__ == "__main__":
    root = AddPopup()
    root.title("untitled")
    root.mainloop()
