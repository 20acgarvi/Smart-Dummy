#takes an array of Scenarios
class Collection:
    def __init__(self, *args, scenarios = None, **kwargs):
        self.scenarios = scenarios
        self.active = self.scenarios[0]

    def change_active(self, i):
        self.active = self.scenarios[i]
    
class Scenario:
    def __init__(self, *args, **kwargs):
        if "name" in kwargs:
            self.name = kwargs["name"]
        if "img" in kwargs:
            self.image = kwargs["img"]

    def qfprint(self):
        #For debugging
        print("Name:" + self.name + " Link:" + self.image)

if __name__ == "__main__":
    s = Scenario(name = "Tommy", img = "sampletext")
    s1 = Scenario(name = "Thomas", img = "nulltext")
    c = Collection(scenarios = [s, s1])
        
