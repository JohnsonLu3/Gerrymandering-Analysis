package gerrymandering.model;

import gerrymandering.common.Party;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yisuo on 12/6/17.
 */
@Entity
@Table(name = "Simulations")
public class MonteCarloSeats implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "StateId")
    private Integer stateId;

    @Column(name = "meanSeats")
    private Double mean;

    @Enumerated(EnumType.STRING)
    @Column(name = "party")
    private Party party;

    @Column(name = "standardDeviation")
    private Double standardDeviation;

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Double getMean() {
        return mean;
    }

    public void setMean(Double mean) {
        this.mean = mean;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(Double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
}
