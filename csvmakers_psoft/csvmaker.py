from faker import Faker
import csv
import random

def generate_csv(filename, num_entries):
    fake = Faker()
    subjects = ["CS", "MT", "ENG", "BIO", "PHYS", "CHEM", "HIST", "ART", "MUS", "LANG", "MED", "BTECH"]
    courses = {}
    names = []
    
    for i in range(len(subjects)):
        course = subjects[i] + "-" + str(random.randrange(0, 6100, 100))
        if course in courses:
            courses[subjects[i]].append(course)
        else:
            courses[subjects[i]] = [course]
        
    
    # Generates a list of X random names
    for _ in range(1000):
        names.append(fake.name())

    with open(filename, 'w', newline='') as csvfile:
        csv_writer = csv.writer(csvfile, quoting=csv.QUOTE_ALL)
        for _ in range(num_entries):
            name = random.choice(names)
            subject = random.choice(list(courses.keys()))
            course = random.choice(courses[subject])
            csv_writer.writerow([name, course])

if __name__ == "__main__":
    filename = 'lesscourses_morenames.csv'
    
    num_entries = 150 # You can adjust this number as needed
    generate_csv(filename, num_entries)
    print(f"CSV file '{filename}' generated successfully with {num_entries} entries.")
