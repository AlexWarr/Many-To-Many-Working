package com.Alex.Forest.Entity;

import java.time.LocalDateTime;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.springframework.data.relational.core.mapping.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import lombok.Data;

@Data //probs needs new getters
@Entity
@Table(name="Moment")
public class Moment {
  
  @Id
  private LocalDateTime DTG;
  
  @Column(value= "Season")
  private String season;

  @Column(value= "Plant_Growth_Phase")
  private String plant_growth_phase;
  
  @Column(value= "Weather")
  private String weather;
  
  @OneToOne(mappedBy = "moment")
  private Entry entry;
  
  
  public Moment(){
    
  }
  
  public Moment(String season, String gowthPhase, String weather){
    this.season = season;
    this.plant_growth_phase = gowthPhase;
    this.weather = weather;
  }
  
  
}
