from tkinter import *
from tkinter import ttk
from PIL import ImageTk, Image
from Collection import *

#For demo only
class EmptyTab(Frame):
    def __init__(self, parent, *args, **kwargs):
        Frame.__init__(self, parent, *args, **kwargs)
        self.parent = parent

#For demo only
class ImageTab(Frame):
    def __init__(self, parent, *args, collection = None, **kwargs):
        Frame.__init__(self, parent, *args, **kwargs)
        self.parent = parent
        self.canvas = Canvas(self, width = 600, height = 600)
        self.canvas.pack(expand = 1, fill = "both")
        if collection != None:
            self.collection = collection
        self.change_image(self.collection.active.image)
                
    def change_image(self, img):
        if img != None:
            try:
                self.image = ImageTk.PhotoImage(Image.open(img))
                self.canvas.create_image((0,0),image = self.image,anchor = NW)
            except FileNotFoundError:
                #The image does not exist
                pass
        
class HeartRate(Frame):
    def __init__(self, parent, *args, **kwargs):
        Frame.__init__(self, parent, *args, **kwargs)
        self.parent = parent
        #To be implemented
        self.placeholder = Entry(self)
        self.placeholder.pack(expand = 1, fill = "both")
    
class ScenarioList(Frame):
    def __init__(self, parent, *args, collection = None, **kwargs):
        Frame.__init__(self, parent, *args, **kwargs)
        self.parent = parent
        if collection != None:
            self.collection = collection
        self.scenarios = Listbox(self, selectmode = SINGLE)
        self.scenarios.bind("<Double-Button-1>", self.change_active)
        self.add_scenarios()
        self.scenarios.pack(expand = 1, fill = "both")

    def add_scenarios(self):
        for i in range(len(self.collection.scenarios)):
            self.scenarios.insert(i, self.collection.scenarios[i].name)
        #Entries

    def change_active(self, event):
        select = self.scenarios.curselection()[0]
        self.collection.change_active(select)
    
class MainApplication(Tk):
    def __init__(self, *args, collection = None, **kwargs):
        Tk.__init__(self, *args, **kwargs)
        self.collection = collection
        self.leftNotebook = ttk.Notebook()
        self.rightNotebook = ttk.Notebook()
        self.add_tabs()

        self.screen_width = self.winfo_screenwidth()
        self.screen_height = self.winfo_screenheight()
        self.leftNotebook.place(x = 0, y = 0, relheight = 1.0, relwidth = 0.25)
        self.rightNotebook.place(x = self.screen_width / 4, y = 0, relheight = 1.0, relwidth = 0.75)

    
        self.update_GUI()
    
    def update_GUI(self):
        self.img.change_image(self.collection.active.image)
        self.after(500, self.update_GUI)
        
    def force_size(self):
        pass
        
    def add_tabs(self):
        self.scen = ScenarioList(self.leftNotebook, collection = self.collection)
        self.img = ImageTab(self.rightNotebook, collection = self.collection)
        #Empty Tabs
        e1 = EmptyTab(self.leftNotebook)
        e2 = EmptyTab(self.rightNotebook)
        e3 = EmptyTab(self.rightNotebook)
        #Add Tabs to Notebooks
        self.leftNotebook.add(self.scen, text = "Scenarios")
        self.rightNotebook.add(self.img, text = "Personal Information")
        #Empty Tabs
        self.leftNotebook.add(e1, text = "Sample Text")
        self.rightNotebook.add(e2, text = "Sample Text")
        self.rightNotebook.add(e3, text = "Sample Text")

if __name__ == "__main__":
    s = Scenario(name = "Thommy", img = "/Users/arthurgarvin/Downloads/stockphoto.jpg")
    s1 = Scenario(name = "Thomas", img = "/Users/arthurgarvin/Downloads/stockimage2.jpg")
    c = Collection(scenarios = [s, s1])
    
    main = MainApplication(collection = c)
    main.title("ProtoGUI")
    main.geometry("%dx%d" % (main.screen_width, main.screen_height))
    main.mainloop()




    

        




