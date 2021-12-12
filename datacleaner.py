import csv
import random

with open('city_temperature.csv', 'r') as fin, open('fout.csv', 'w') as fout:

    # define reader and writer objects
    reader = csv.reader(fin, skipinitialspace=True)
    writer = csv.writer(fout, delimiter=',')

    # write headers
    h = next(reader)
    del h[2]
    print(h)
    
    for i in reader:
        if float(i[-1]) != float("-99"):
          del i[2]
          writer.writerow(i)
       
