from sqlalchemy import create_engine
from sqlalchemy import Table, Column, Integer, String, MetaData, ForeignKey
from sqlalchemy.orm import sessionmaker, scoped_session
from sqlalchemy.ext.automap import automap_base
import pprint

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
pp = pprint.PrettyPrinter(depth=6)

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
    global totalDistrictCount
    connectToDB()
    totalDistrictCount = getDistrictCount()
    importSimulation()
    #session.commit()
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

    for item in stateDistricts:
        N = item[2]
        K = 250                  # What is K?
        print item[0]
        actualRepPercent = 0
        actualDemPercent = 0
        simulatedDemVotes = []
        simulatedRepVotes = []

        percent = 0.2

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

            randomDemPercent  = 0
            randomRepPercent  = 0

            simulateDemTotal = " SELECT voteCount "\
                                    " FROM   Votes, Districts, States"\
                                    " WHERE  Votes.DistrictId = Districts.StateId "\
                                    " AND    Votes.Party = \"Democrat\" "\
                                    " AND    Districts.StateId = States.Id"\
                                    " AND    States.Year = " + str(item[1]) + " "\
                                    " ORDER BY RAND() LIMIT " + str(N)

            simulateRepTotal = " SELECT voteCount "\
                                    " FROM   Votes, Districts, States"\
                                    " WHERE  Votes.DistrictId = Districts.StateId "\
                                    " AND    Votes.Party = \"Republican\" "\
                                    " AND    Districts.StateId = States.Id"\
                                    " AND    States.Year = " + str(item[1]) + " "\
                                    " ORDER BY RAND() LIMIT " + str(N)


            for row in session.execute(simulateDemTotal):
                simulateDemTotal = row[0]    # add random districts percent for Democrats

            for row in session.execute(simulateRepTotal):
                simulateRepTotal = row[0]   # add random district percent for Republicans

            simulatedTotal = simulateDemTotal + simulateRepTotal
            if simulatedTotal == 0:
                randomDemPercent = 0
                randomRepPercent = 0
            else:
                randomDemPercent = float(simulateDemTotal) / float(simulatedTotal)
                randomRepPercent = float(simulateRepTotal) / float(simulatedTotal)

            # if any combination of N districts are within 0.2% of the
            # actual total percent votes of the party, save the number of seats won by each party
            if -1 * percent <= float(randomRepPercent) - float(actualRepPercent) <= percent and -1 * percent <= float(randomDemPercent) - float(actualDemPercent) <= percent:
                simulatedDemVotes.append(simulateDemTotal)
                simulatedRepVotes.append(simulateRepTotal)

        # By the end of the simulation, calculate the mean of the seats for each party.
        simulatedDemVotesMean = 0
        for i in simulatedDemVotes:
            simulatedDemVotesMean = simulatedDemVotesMean + i
        simulatedDemVotesMean = simulatedDemVotesMean / len(simulatedDemVotes)

        simulatedRepVotesMean = 0
        for i in simulatedRepVotes:
            simulatedRepVotesMean = simulatedRepVotesMean + i
        simulatedRepVotesMean = simulatedRepVotesMean / len(simulatedRepVotes)

        print "Actual Rep Percent        :" + str(actualRepPercent)
        print "Actual Dem Percent        :" + str(actualDemPercent)
        print "---"
        print "Simulated Rep Mean Percent:" + str(float(simulatedRepVotesMean)/ float(simulatedDemVotesMean + simulatedRepVotesMean))
        print "Simulated Dem Mean Percent:" + str(float(simulatedDemVotesMean) / float(simulatedDemVotesMean + simulatedRepVotesMean))
        print "---"
        print "Simulated Rep Votes Mean  :" + str(simulatedDemVotesMean)
        print "Simulated Dem Votes Mean  :" + str(simulatedRepVotesMean)

        # Save the mean to the Simulations table.
        i = "INSERT Simulations(StateId, meanSeats, Party) " \
            + " VALUES(" \
            + str(item[0]) + " , " + str(simulatedDemVotesMean) + " , " + '\'Democrat\'' \
            + ")"

        session.execute(i)

        i = "INSERT Simulations(StateId, meanSeats, Party) " \
            + " VALUES(" \
            + str(item[0]) + " , " + str(simulatedRepVotesMean) + " , " + '\'Republican\'' \
            + ")"

        session.execute(i)

    return

def getRandomDistricts(state):

    randomDistrictsId = []
    s = " SELECT   Districts.Id " \
                     + " FROM   Districts , States " \
                     + " WHERE  Districts.StateId = States.Id" \
                     + " AND    States.Year = " + str(state[1]) \
                     + " ORDER BY RAND() LIMIT + " + str(state[2])

    for row in session.execute(s):
        randomDistrictsId.append(row[0])  # add random districts

    return randomDistrictsId

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

