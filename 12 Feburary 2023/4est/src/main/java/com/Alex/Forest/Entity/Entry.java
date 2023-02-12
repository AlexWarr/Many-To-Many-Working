package com.Alex.Forest.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.rest.core.annotation.RestResource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@Data
@Entity
@Table(name="Entry")
public class Entry {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int Entry_Number;
  
  @Column(value= "Notes")
  private String notes;

  //Foreign Keys
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name= "Location_Name", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Location Location;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name= "Plant_ID", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Plant plant;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name= "Plant_Latin_Name", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private Plant_Species species;
  
  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name= "DTG", nullable = false)
  @RestResource(path = "EntryDate", rel="Moment")
  private Moment moment;
  
  
  public Entry() {
    
  }

  public Entry(String notes) {
    this.notes = notes;
  }
  
  
}