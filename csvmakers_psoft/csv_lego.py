import csv

# Dictionary with LEGO parts and the sets they belong to
lego_parts = {
    'Brick 1x1': ['Set A', 'Set B', 'Set C'],
    'Brick 2x2': ['Set A', 'Set B', 'Set D'],
    'Plate 1x2': ['Set B', 'Set D', 'Set E'],
    'Tile 2x2': ['Set C', 'Set E', 'Set F'],
    'Plate 2x4': ['Set A', 'Set C', 'Set E'],
    'Brick 1x2': ['Set C', 'Set D', 'Set F'],
    'Tile 1x1': ['Set A', 'Set E', 'Set F'],
    'Plate 2x2': ['Set B', 'Set D', 'Set F']
}

# Function to write the dictionary to a CSV file
def create_csv(lego_parts, filename='lego_parts.csv'):
    with open(filename, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile, quoting=csv.QUOTE_ALL)
        writer.writerow(['LEGO Part', 'LEGO Set'])
        for part, sets in lego_parts.items():
            for lego_set in sets:
                writer.writerow([part, lego_set])

# Create the CSV file
create_csv(lego_parts)
print("CSV file created successfully!")
