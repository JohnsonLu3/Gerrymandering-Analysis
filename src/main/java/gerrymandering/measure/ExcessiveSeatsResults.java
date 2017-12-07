package gerrymandering.measure;

import gerrymandering.common.TestType;
import gerrymandering.model.MonteCarloSeats;

/**
 * Created by yisuo on 12/6/17.
 */
public class ExcessiveSeatsResults extends MeasureResults {
    private Double simulatedMean;
    private Double simulatedStandardDeviation;
    private Long actualSeats;

    public ExcessiveSeatsResults(Double mean, Double sd, Long actual){
        super();
        testPerformed = TestType.ExcessiveSeats;
        simulatedMean = mean;
        simulatedStandardDeviation = sd;
        actualSeats = actual;
    }

    public ExcessiveSeatsResults(MonteCarloSeats simulation, Long actual){
        super();
        testPerformed = TestType.ExcessiveSeats;
        simulatedMean = simulation.getMean();
        simulatedStandardDeviation = simulation.getStandardDeviation();
        actualSeats = actual;
    }

    public Double getSimulatedMean() {
        return simulatedMean;
    }

    public void setSimulatedMean(Double simulatedMean) {
        this.simulatedMean = simulatedMean;
    }

    public Double getSimulatedStandardDeviation() {
        return simulatedStandardDeviation;
    }

    public void setSimulatedStandardDeviation(Double simulatedStandardDeviation) {
        this.simulatedStandardDeviation = simulatedStandardDeviation;
    }

    public Long getActualSeats() {
        return actualSeats;
    }

    public void setActualSeats(Long actualSeats) {
        this.actualSeats = actualSeats;
    }
}
