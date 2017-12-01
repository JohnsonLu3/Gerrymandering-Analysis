package gerrymandering.model;

import gerrymandering.common.CommonConstants;
import gerrymandering.common.Party;
import gerrymandering.common.PopulationGroup;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yisuo on 11/7/17.
 */

@Entity
@Table(name = "Districts")
@AttributeOverride(name = "area", column = @Column(name = "Area"))
public class District extends BipartisanRegion implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer Id;
    @Column(name = "DistrictId")
    private Integer districtNo;
    @Column(name = "Area")
    private Long area;
    @Column(name = "clickCount")
    private Integer clickCount;
    @ManyToOne(targetEntity = State.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "StateId", referencedColumnName = "Id")
    private State state;
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "Party")
    private Map<Party, Votes> votes = new HashMap<>();
    @ElementCollection()
    @CollectionTable(name = "Population", joinColumns = @JoinColumn(name = "DistrictId"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "Name")
    @Column(name = "Population")
    private Map<PopulationGroup, Long> population = new HashMap<>();
    @ManyToMany(cascade = CascadeType.ALL, targetEntity = Boundary.class)
    @JoinTable(name = "DistrictBoundaries",
            joinColumns = @JoinColumn(name = "DistrictId", referencedColumnName = "Id"),
            inverseJoinColumns = @JoinColumn(name = "BoundaryId", referencedColumnName = "Id"))
    private List<Boundary> boundaries = new ArrayList<>();

    @Override
    public List<Boundary> getBoundaries() {
        return boundaries;
    }

    @Override
    public Map<Party, Long> getVotes() {
        return votes
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                    entry -> entry.getKey(),
                    entry -> entry.getValue().getVoteCount()
                ));
    }

    @Override
    public Long getTotalVotes() {
        return votes
                .values()
                .stream()
                .mapToLong(votes -> votes.getVoteCount())
                .sum();
    }

    @Override
    public Map<Party, Double> getPercentVotes() {
        Long totalVotes = getTotalVotes();

        return votes
                .entrySet()
                .stream()
                .collect(
                    Collectors.toMap(
                        p -> p.getKey(),
                        p -> p.getValue().getVoteCount()
                                / new Double(totalVotes)
                                * CommonConstants.PERCENT
                    )
                );
    }

    @Override
    public Long getPartyVotes(Party party) {
        return votes.get(party).getVoteCount();
    }

    @Override
    public Double getPartyPercent(Party party) {
        return getPercentVotes().get(party);
    }

    @Override
    public Party getElectedParty() {
        Party elected = votes
                        .entrySet()
                        .stream()
                        .filter(entry -> {return entry.getValue().getVoteCount() == -1;})
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElse(null);
        if(elected == null){
            elected = Collections.max(
                    getVotes().entrySet(),
                    (a, b) ->
                            a.getValue() > b.getValue() ? 1 : -1
            ).getKey();
        }
        return elected;
    }

    @Override
    public void addVotes(Map<Party, Votes> votes, Party party, Long numVotes) {
        votes.get(party).addVotes(numVotes);
    }

    @Override
    public Long getTotalArea() {
        return area;
    }

    @Override
    public Map<PopulationGroup, Long> getPopulationGroups() {
        return population;
    }

    @Override
    public Map<PopulationGroup, Double> getPopulationPercents() {
        Long total = getTotalPopulation();
        return population
            .entrySet()
            .stream()
            .collect(
                Collectors.toMap(
                    p -> p.getKey(),
                    p -> p.getValue() / new Double(total)
                        * CommonConstants.PERCENT
                )
            );
    }

    @Override
    public Long getTotalPopulation() {
        return population
                .values()
                .stream()
                .mapToLong(p -> p)
                .sum();
    }

    @Override
    public Long getPopulation(PopulationGroup group) {
        return getPopulationGroups().get(group);
    }

    @Override
    public Double getPercentPopulation(PopulationGroup group) {
        return getPopulationPercents().get(group);
    }

    public State getState(){
        return state;
    }

    public Integer getDistrictNo(){
        return districtNo;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
