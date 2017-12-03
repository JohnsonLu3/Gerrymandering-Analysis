package gerrymandering.model;

import javax.persistence.*;

/**
 * Created by yisuo on 12/3/17.
 */
@Entity
@Table(name = "Neighbors")
public class Neighbor {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer Id;

    @OneToOne
    @JoinColumn(name = "DistrictAId")
    private District districtA;

    @OneToOne
    @JoinColumn(name = "DistrictBId")
    private District districtB;

    public District getDistrictA() {
        return districtA;
    }

    public District getDistrictB() {
        return districtB;
    }
}
