package gerrymandering.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yisuo on 12/6/17.
 */
@Entity
@Table(name = "CompleteWorks")
public class CompleteWork implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Name")
    private String name;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CreatorId", referencedColumnName = "Id")
    private User owner;

    @OneToMany(mappedBy = "savedWork", cascade = CascadeType.ALL)
    private List<SuperDistrict> superDistricts = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<SuperDistrict> getSuperDistricts() {
        return superDistricts;
    }

    public void setSuperDistricts(List<SuperDistrict> superDistricts) {
        this.superDistricts = superDistricts;
    }
}
