# This script is for parsing the US house election data from 1972 to 2016
# This will scrub out all the useless data and leave only the import data
# Important data to retain; State, Year, District, RepVotes, DemoVotes
input = open("US_House_elections_1972_to_2016_clean.csv", 'r')
output = open("parsedFiles/votingData.csv", 'w')

COMMA = ','
NEWLINE = '\n'

for line in input:
    line = line.split(",")
    state    = line[0].replace('"','')
    year     = line[1].replace('"','')
    district = line[2].replace('"','')
    repVotes = line[3].replace('"','')
    demVotes = line[5].replace('"','')

<<<<<<< HEAD
    state    = line[0].replace('"','')
    year     = line[1].replace('"','')
    district = line[2].replace('"','')
    repVotes = line[3].replace('"','')
    demVotes = line[5].replace('"','')

    try:
        if int(year) > 1999:
            newLine = state + COMMMA + year + COMMMA + district + COMMMA + repVotes + COMMMA + demVotes + NEWLINE

            output.write(newLine)
    except:
        pass

=======
    try:
        if int(year) > 1999:
            newLine = state + COMMA + year + COMMA + district + COMMA + repVotes + COMMA + demVotes + NEWLINE
            output.write(newLine)
    except:
        pass
>>>>>>> build-2
