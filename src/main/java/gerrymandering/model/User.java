package gerrymandering.model;
import javax.persistence.*;

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
        @Column(name = "compactnessThreshold")
        private Double compactnessThreshold;
        @Column(name = "pValue")
        private Double pValue;
        @Column(name = "EfficienctGap")
        private Double EfficienctGap;

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

        public Double getEfficienctGap(){
            return EfficienctGap;
        }

        public void setEfficienctGap(Double EfficienctGap){
            this.EfficienctGap = EfficienctGap;
        }
}
