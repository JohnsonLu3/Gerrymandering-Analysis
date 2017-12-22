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
@Table(name = "SuperDistricts")
public class SuperDistrict extends MultiDistrictRegion implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer Id;

    @ManyToOne(targetEntity = CompleteWork.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CompleteWorkId", referencedColumnName = "Id")
    private CompleteWork savedWork;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "BelongsTo",
            joinColumns = @JoinColumn(name = "SuperDistrict_Id", referencedColumnName = "Id"),
            inverseJoinColumns = @JoinColumn(name = "DistrictId", referencedColumnName = "Id"))
    private List<District> districts = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SuperDistrictBoundaries",
            joinColumns = @JoinColumn(name = "SuperDistrictId", referencedColumnName = "Id"),
            inverseJoinColumns = @JoinColumn(name = "BoundaryId", referencedColumnName = "Id"))
    private List<Boundary> boundaries = new ArrayList<>();

    @Override
    public List<Boundary> getBoundaries() {
        return boundaries;
    }

    public void setBoundaries(List<Boundary> boundaries){
        this.boundaries = boundaries;
    }

    @Override
    public Map<Party, Long> getVotes() {
        Map<Party, Long> result = new HashMap<>();
        Arrays.stream(Party.values()).forEach(party -> result.put(party, 0L));
        districts.forEach(district -> {
            district.getVotes().forEach((party, votes) -> {
                Long voteSum = result.get(party);
                voteSum += votes;
                result.put(party, voteSum);
            });
        });
        return result;
    }

    @Override
    public Long getTotalVotes() {
        return districts.stream().mapToLong(district -> district.getTotalVotes()).sum();
    }

    @Override
    public Map<Party, Double> getPercentVotes() {
        return getVotes()
                .entrySet()
                .stream()
                .collect(
                    Collectors
                        .toMap(
                            entry -> entry.getKey(),
                            entry -> entry.getValue() /
                                    new Double(getTotalVotes())
                                    * CommonConstants.PERCENT
                        ));
    }

    @Override
    public Long getPartyVotes(Party party) {
        return districts.stream().mapToLong(district -> district.getPartyVotes(party)).sum();
    }

    @Override
    public Double getPartyPercent(Party party) {
        return districts.stream().mapToDouble(district -> district.getPartyPercent(party)).sum();
    }

    @Override
    public Party getElectedParty() {
        Map<Party, Long> districtElections =
                districts
                        .stream()
                        .collect(
                                Collectors.groupingBy(
                                        District::getElectedParty,
                                        Collectors.counting()
                                )
                        );

        Party electedParty =
                Collections.max(
                        districtElections.entrySet(),
                        Map.Entry.comparingByValue()
                ).getKey();
        return electedParty;
    }

    @Override
    public void addVotes(Map<Party, Votes> votes, Party party, Long numVotes) {
        votes.get(party).addVotes(numVotes);
    }

    @Override
    public Long getTotalArea() {
        return districts
                .stream()
                .mapToLong(district -> district.getTotalArea())
                .sum();
    }

    @Override
    public Map<PopulationGroup, Long> getPopulationGroups() {
        Map<PopulationGroup, Long> result = new HashMap<>();
        List<PopulationGroup> ethnicGroups = Arrays.asList(PopulationGroup.values());

        ethnicGroups.forEach(group -> {
            result.put(group, 0L);
        });

        districts.forEach(district -> {
            ethnicGroups.forEach(group -> {
                Map<PopulationGroup, Long> groups = district.getPopulationGroups();
                Long groupInDist = result.get(group);
                if(groups.size() > 0)
                    groupInDist += groups.get(group);
                result.put(group, groupInDist);
            });
        });
        return result;
    }

    @Override
    public Map<PopulationGroup, Double> getPopulationPercents() {
        Map<PopulationGroup, Long> demographic = getPopulationGroups();
        Long total = getTotalPopulation();

        return demographic
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                p -> p.getKey(),
                                p -> p.getValue()
                                        / new Double(total)
                                        * CommonConstants.PERCENT
                        )
                );
    }

    @Override
    public Long getTotalPopulation() {
        return districts
                .stream()
                .mapToLong(district -> district.getTotalPopulation())
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

    @Override
    public List<District> getDistricts() {
        return districts;
    }

    public CompleteWork getSavedWork() {
        return savedWork;
    }

    public void setSavedWork(CompleteWork savedWork) {
        this.savedWork = savedWork;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
}

