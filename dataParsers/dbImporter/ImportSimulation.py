from sqlalchemy import create_engine
from sqlalchemy import Table, Column, Integer, String, MetaData, ForeignKey
from sqlalchemy.orm import sessionmaker, scoped_session
from sqlalchemy.ext.automap import automap_base
from geoalchemy2 import *
import random
import pprint

connection_string = ''
engine = None
Session = None
session = None
#conn = None
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
    #conn = engine.connect()
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

    for item in stateDistricts:
        N = item[2]
        K = -1                  # What is K?

        percent = 0.2

        for i in range(K):                          # Randomly select N districts from the district table for that year K times
            randomDistrictsId = []
            s = " SELECT   Districts.Id " \
                + " FROM   Districts , States " \
                + " WHERE  Districts.StateId = States.Id"\
                + " AND    States.Year = " + str(item[1])\
                + " ORDER BY RAND() LIMIT + " + str(N)
            for row in session.execute(s):
                randomDistrictsId.append(row[0])    # add random districts

            # if any combination of N districts are within 0.2% of the
            # actual total percent votes of the party, save the number of seats won by each party.

    # By the end of the simulation, calculate the mean of the seats for each party.
    # Save the mean to the Simulations table.



    return

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

