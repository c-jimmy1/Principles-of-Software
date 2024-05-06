import csv
import random

# Detailed Lego parts
lego_parts = [
    'Brick - Red - Plastic - Basic', 
    'Plate - Blue - Plastic - Basic', 
    'Tile - Yellow - Plastic - Decorative', 
    'Minifigure - Green - Plastic - Character', 
    'Technic - Black - Plastic - Functional', 
    'Slope - White - Plastic - Structural', 
    'Wheel - Gray - Plastic - Vehicle', 
    'Axle - Brown - Plastic - Mechanical', 
    'Beam - Tan - Plastic - Building', 
    'Panel - Orange - Plastic - Wall', 
    'Window - Transparent - Plastic - Accessory', 
    'Door - Translucent - Plastic - Entry', 
    'Roof Tile - Silver - Plastic - Roofing', 
    'Fence - Gold - Plastic - Barrier', 
    'Flag - Bronze - Fabric - Decoration', 
    'Plant - Green - Plastic - Landscape', 
    'Animal - Brown - Plastic - Creature', 
    'Vehicle - Blue - Plastic - Transportation', 
    'Weapon - Gray - Plastic - Combat', 
    'Tool - Black - Plastic - Utility'
]

# Detailed Lego sets
lego_sets = [
    'Star Wars', 'City', 'Creator', 'Harry Potter', 'Marvel Super Heroes', 'Ninjago', 
    'Friends', 'Architecture', 'Ideas', 'Classic', 'Disney', 'Pirates', 'Space', 
    'Jurassic World', 'DC Super Heroes', 'Speed Champions', 'Technic', 'Minecraft', 'Education', 'Dots'
]

# Function to generate random Lego parts and sets
def generate_lego_data(num_rows):
    data = []
    for _ in range(num_rows):
        part = random.choice(lego_parts)
        lego_set = random.choice(lego_sets)
        data.append((part, lego_set))

    return data

# Function to write data to CSV file
def write_to_csv(data, filename):
    with open(filename, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile, delimiter=',', quoting=csv.QUOTE_ALL)
        writer.writerow(['Lego Part', 'Lego Set'])
        writer.writerows(data)

# Generate Lego data
num_rows = 500  # Change this to adjust the number of rows in the CSV
lego_data = generate_lego_data(num_rows)

# Write data to CSV
csv_filename = 'lego_small.csv'
write_to_csv(lego_data, csv_filename)
print(f"CSV file '{csv_filename}' created successfully.")
