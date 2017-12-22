from sqlalchemy import create_engine
from sqlalchemy import Table, Column, Integer, String, MetaData, ForeignKey
from sqlalchemy.orm import sessionmaker, scoped_session
from sqlalchemy.ext.automap import automap_base
import numpy
import random

connection_string = ''
engine = None
Session = None
session = None
conn = None
metadata = None
Base = None

Boundaries = None
States = None
Districts = None
Population = None

stateFPs = {}
Neighbors = []

def connectToDB():
    global Session
    global session
    global engine
    global Boundaries
    global metadata
    global Base
    global States
    global Districts
    global Population

    conURL = open('../Connection', 'r')
    connection_string = conURL.readline()
    engine = create_engine(connection_string, echo=False)
    Session = sessionmaker(bind=engine)
    session = Session()
    conn = engine.connect()
    metadata = MetaData()
    metadata.reflect(bind=engine)
    Base = automap_base()
    Base.prepare(engine, reflect=True)

    Boundaries = Base.classes.Boundaries
    States = Base.classes.States
    Districts = Base.classes.Districts
    Population = Base.classes.Population

    totalDistrictCount = -1

def main():
    connectToDB()

    importSimulation()
    session.commit()
    return

def getDistrictCount():
    s = "SELECT COUNT(id) FROM Districts"
    for row in session.execute(s):
        return row[0]

# 1. Given a state's Id, find its total number of districts (seats) as N.
#
# 2. Randomly select N districts from the Districts table for that year K times.
#    During the iteration, if any combination of N districts are within 0.2% of the
#    actual total percent votes of the party, save the number of seats won by each party.
#
# 3. By the end of the simulation, calculate the mean of the seats for each party.
#    Save the mean to the Simulations table.
def importSimulation():
    stateDistricts = buildStateDistrictTuples()     # (stateId, year, districtCount)
    demVotes = getAllPartyData('Democrat')
    repVotes = getAllPartyData('Republican')
    K = 10000
    percent = 0.2

    for item in stateDistricts:
        N = item[2]
                         # What is K?
        print str(item[0]) + " : " + str(item[1])
        # Reset Vars
        actualRepPercent  = 0
        actualDemPercent  = 0
        simulatedDemCount = []
        simulatedRepCount = []

        # get actual vote count for that state and year
        repVotePercent = "SELECT sum(voteCount) /" \
                         " (SELECT sum(voteCount) " \
                         " FROM Votes, Districts, States " \
                         " WHERE Votes.DistrictId = Districts.Id " \
                         " AND Districts.StateId = " + str(item[0]) + ""\
                         " AND States.Id = " + str(item[0])+ ") "\
                         " FROM Votes, Districts " \
                         " WHERE Votes.DistrictId = Districts.Id " \
                         " AND Districts.StateId = " + str(item[0]) + "" \
                         " AND Votes.Party = \"Republican\""

        demVotePercent = "SELECT sum(voteCount) /" \
                         " (SELECT sum(voteCount) " \
                         " FROM Votes, Districts, States " \
                         " WHERE Votes.DistrictId = Districts.Id " \
                         " AND Districts.StateId = " + str(item[0]) + ""\
                         " AND States.Id = " + str(item[0]) + ") "\
                         " FROM Votes, Districts " \
                         " WHERE Votes.DistrictId = Districts.Id " \
                         " AND Districts.StateId = " + str(item[0]) + ""\
                         " AND Votes.Party = \"Democrat\""

        for row in session.execute(repVotePercent):
            actualRepPercent = float(row[0])

        for row in session.execute(demVotePercent):
            actualDemPercent = float(row[0])

        for i in range(K):                          # Randomly select N districts from the district table for that year K times
            simulatedDemRepVotes = []
            randomDemVotes       = 0
            randomRepVotes       = 0
            randomDemPercent     = 0
            randomRepPercent     = 0

            while len(simulatedDemRepVotes) != N:
                randomDemVotes = random.choice(demVotes)
                randomRepVotes = random.choice(repVotes)

                simulateDemTotal = randomDemVotes/N                    # get random dem votes

                simulateRepTotal = randomRepVotes/N                    # get random Rep votes

                simulatedTotal = simulateDemTotal + simulateRepTotal    # find mean of simulated votes

                if simulatedTotal == 0:
                    randomDemPercent = 0
                    randomRepPercent = 0
                else:
                    randomDemPercent = float(simulateDemTotal) / float(simulatedTotal)
                    randomRepPercent = float(simulateRepTotal) / float(simulatedTotal)

                # if any combination of N districts are within 0.2% of the
                # actual total percent votes of the party, save the number of seats won by each party
                if -1 * percent <= float(randomRepPercent) - float(actualRepPercent) <= percent and -1 * percent <= float(randomDemPercent) - float(actualDemPercent) <= percent:
                    simulatedDemRepVotes.append((simulateDemTotal, simulateRepTotal))

            demWins = 0
            repWins = 0
            for district in simulatedDemRepVotes:
                if district[0] > district[1]:
                    demWins = demWins + 1
                else:
                    repWins = repWins +1

            simulatedDemCount.append(demWins)
            simulatedRepCount.append(repWins)


        # By the end of the simulation, calculate the mean of the seats for each party.
        simulatedDemVotesMean = numpy.mean(simulatedDemCount, axis=0)
        standardDeviationDem = numpy.std(simulatedDemCount,axis=0)

        simulatedRepVotesMean = numpy.mean(simulatedRepCount, axis=0)
        standardDeviationRep = numpy.std(simulatedRepCount, axis=0)

        print "Total district: " + str(N)
        print "Dem Seat Mean : " + str(simulatedDemVotesMean)
        print "Rep Seat Mean : " + str(simulatedRepVotesMean)
        if simulatedDemVotesMean > simulatedRepVotesMean:
            # Save the mean to the Simulations table.
            i = "INSERT Simulations(StateId, meanSeats, Party, standardDeviation) " \
                + " VALUES(" \
                + str(item[0]) + " , " + str(simulatedDemVotesMean) + " , " + '\'Democrat\'' + " ," +  str(standardDeviationDem)\
                + ")"

            session.execute(i)
        else:
            i = "INSERT Simulations(StateId, meanSeats, Party, standardDeviation) " \
                + " VALUES(" \
                + str(item[0]) + " , " + str(simulatedRepVotesMean) + " , " + '\'Republican\'' + " ," + str(standardDeviationRep)\
                + ")"

            session.execute(i)

    return

def getAllPartyData(party):

    partyVotes = []
    getData = " SELECT Votes.voteCount "\
            " FROM   Votes, Districts " \
            " WHERE  Votes.DistrictId = Districts.Id "\
            " AND    Votes.Party = \'" + party +"\'"\

    for row in session.execute(getData):
        partyVotes.append(row[0])

    return partyVotes


def buildStateDistrictTuples():
    s = "SELECT id, year FROM States"
    stateAndDistricts = []

    for row in session.execute(s):
        stateDistrict = ()
        state = row[0]
        year  = row[1]
        s2 = "SELECT COUNT(Id) FROM Districts WHERE Districts.StateId = " + str(state)

        for row2 in session.execute(s2):
            districtCount = row2[0]
            stateAndDistricts.append((state, year , districtCount))

    return stateAndDistricts

if __name__ == "__main__":
    main()

