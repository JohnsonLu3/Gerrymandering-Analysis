package gerrymandering.model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        @Column(name = "username")
        private String username;
        @Column(name = "password")
        private String password;
        @Column(name = "enabled")
        private boolean enabled;
        @Column(name = "activationKey")
        private String activationKey;
        @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
        private List<CompleteWork> savedWorks = new ArrayList<>();
        @Column(name = "compactnessThreshold")
        private Double compactnessThreshold;
        @Column(name = "pValue")
        private Double pValue;
        @Column(name = "EfficiencyGap")
        private Double EfficiencyGap;

        @Transient
        private String role;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean getEnabled(){
            return enabled;
        }

        public void setEnabled(boolean enabled){
            this.enabled = enabled;
        }

        public String getActivationKey(){
            return activationKey;
        }

        public void setActivationKey(String key){
            this.activationKey = key;
        }

        public String getRole(){
            return role;
        }

        public void setRole(String role){
            this.role = role;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public List<CompleteWork> getSavedWorks() {
            return savedWorks;
        }

        public void setSavedWorks(List<CompleteWork> savedWorks) {
            this.savedWorks = savedWorks;
        }

        public Double getCompactnessThreshold(){
            return compactnessThreshold;
        }

        public void setCompactnessThreshold(Double compactnessThreshold){
            this.compactnessThreshold = compactnessThreshold;
        }

        public Double getPValue(){
            return pValue;
        }

        public void setPValue(Double pValue){
            this.pValue = pValue;
        }

        public Double getEfficiencyGap(){
            return EfficiencyGap;
        }

        public void setEfficiencyGap(Double EfficienctGap){
            this.EfficiencyGap = EfficienctGap;
        }
}
