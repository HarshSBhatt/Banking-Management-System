package asd.group2.bms.model.term_deposit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @description: This will create term deposits table in the database
 */
@Entity
@Table(name = "term_deposits")
public class TermDeposit {
    @Id
    @NotBlank
    @Size(max = 100)
    private String duration;

    @NotNull
    private float rateOfInterest;

    public TermDeposit() {
    }

    public TermDeposit(String duration, float rateOfInterest) {
        this.duration = duration;
        this.rateOfInterest = rateOfInterest;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public float getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(float rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }
}
