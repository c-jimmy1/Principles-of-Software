import csv
import random

# Random Lego colors
lego_colors = ['Red', 'Blue', 'Yellow', 'Green', 'Black', 'White', 'Gray', 'Brown', 'Tan', 'Orange', 'Transparent', 'Translucent', 'Silver', 'Gold', 'Bronze']

# Random Lego types
lego_types = ['Brick', 'Plate', 'Tile', 'Minifigure', 'Technic', 'Slope', 'Wheel', 'Axle', 'Beam', 'Panel', 'Window', 'Door', 'Roof Tile', 'Fence', 'Flag', 'Plant', 'Animal', 'Vehicle', 'Weapon', 'Tool']

# Random Lego categories
lego_categories = ['Plastic', 'Fabric']

# Random Lego functions
lego_functions = ['Basic', 'Decorative', 'Character', 'Functional', 'Structural', 'Vehicle', 'Mechanical', 'Building', 'Wall', 'Accessory', 'Entry', 'Roofing', 'Barrier', 'Decoration', 'Landscape', 'Creature', 'Transportation', 'Combat', 'Utility']

# Random Lego sets
lego_sets = [
    'Star Wars', 'City', 'Creator', 'Harry Potter', 'Marvel Super Heroes', 'Ninjago', 
    'Friends', 'Architecture', 'Ideas', 'Classic', 'Disney', 'Pirates', 'Space', 
    'Jurassic World', 'DC Super Heroes', 'Speed Champions', 'Technic', 'Minecraft', 'Education', 'Dots',
    'Bionicle', 'LEGO Ideas', 'LEGO Elves', 'LEGO Friends', 'LEGO Creator Expert', 'LEGO Super Mario', 
    'LEGO Architecture', 'LEGO Education', 'LEGO Hidden Side', 'LEGO Minecraft', 'LEGO Ninjago', 
    'LEGO Speed Champions', 'LEGO Star Wars', 'LEGO Technic', 'LEGO City', 'LEGO Harry Potter', 
    'LEGO Marvel Super Heroes', 'LEGO Disney Princess', 'LEGO Jurassic World', 'LEGO DC Super Heroes', 
    'LEGO Classic', 'LEGO Pirates', 'LEGO Space', 'LEGO DOTS', 'LEGO Mindstorms', 'LEGO Ideas', 
    'LEGO Minions', 'LEGO Monkie Kid', 'LEGO Overwatch', 'LEGO Speed Racer', 'LEGO Super Heroes', 
    'LEGO Trolls World Tour', 'LEGO Video Games', 'LEGO Creator 3-in-1', 'LEGO Architecture Skyline',
    'LEGO Disney', 'LEGO Friends: The Television Series', 'LEGO Ideas 21318 Tree House', 'LEGO Star Wars Millennium Falcon',
    'LEGO Technic Bugatti Chiron', 'LEGO Ideas 21319 Central Perk', 'LEGO Creator Expert Roller Coaster'
]

# Function to generate random Lego parts and sets
def generate_lego_data(num_rows):
    data = []
    for _ in range(num_rows):
        color = random.choice(lego_colors)
        lego_type = random.choice(lego_types)
        category = random.choice(lego_categories)
        function = random.choice(lego_functions)
        lego_set = random.choice(lego_sets)
        part = f"{lego_type} - {color} - {category} - {function}"
        data.append((part, lego_set))

    return data

# Function to write data to CSV file
def write_to_csv(data, filename):
    with open(filename, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile, delimiter=',', quoting=csv.QUOTE_ALL)
        writer.writerow(['Lego Part', 'Lego Set'])
        writer.writerows(data)

# Generate Lego data
num_rows = 1500  # Change this to adjust the number of rows in the CSV
lego_data = generate_lego_data(num_rows)

# Write data to CSV
csv_filename = 'lego_data.csv'
write_to_csv(lego_data, csv_filename)
print(f"CSV file '{csv_filename}' created successfully.")
