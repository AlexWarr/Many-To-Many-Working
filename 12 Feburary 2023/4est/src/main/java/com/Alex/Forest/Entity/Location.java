package com.Alex.Forest.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.springframework.data.relational.core.mapping.Column;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;



@Entity
@Table(name="Location")
public class Location {
  
  @Id
  @Column(value = "Location_Name")
  private String Location_Name;
  
  @Column(value = "Geography")
  private String Geography;
  
  @Column(value = "Vegetative_Zone")
  private String Vegetative_Zone;
  
  @Column(value = "Growing_Zone")
  private String Growing_Zone;
  
  @Column(value = "Elevation")
  private int Elevation;
  
  @Column(value = "Location_Notes")
  private String Location_Notes;
  
  @OneToMany(mappedBy = "Location_Name")
  private List<Entry> entries =  new ArrayList<Entry>();
  
  @OneToMany(mappedBy = "Location_Name")
  private List<Plant> plants =  new ArrayList<Plant>();
  
  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  })
  @JoinTable(name = "Location_has_Plant_Species",
      joinColumns = @JoinColumn(name = "Location_Name"),
      inverseJoinColumns = @JoinColumn(name = "Plant_Latin_Name")
  )
  private List<Plant_Species> Plant_Species_List;

  
  public Location() {
    
  }
  public Location(String Location_Name, String Geography, String Vegetative_Zone, String Growing_Zone, int Elevation, String Location_Notes) {
    this.Location_Name = Location_Name;
    this.Geography = Geography;
    this.Vegetative_Zone = Vegetative_Zone;
    this.Elevation = Elevation;
    this.Location_Notes = Location_Notes;
  }
  
  public String getLocation_Name() {
    return Location_Name;
  }

  public void setLocation_Name(String location_Name) {
    Location_Name = location_Name;
  }

  public String getGeography() {
    return Geography;
  }

  public void setGeography(String geography) {
    Geography = geography;
  }

  public String getVegetative_Zone() {
    return Vegetative_Zone;
  }

  public void setVegetative_Zone(String vegetative_Zone) {
    Vegetative_Zone = vegetative_Zone;
  }

  public String getGrowing_Zone() {
    return Growing_Zone;
  }

  public void setGrowing_Zone(String growing_Zone) {
    Growing_Zone = growing_Zone;
  }

  public int getElevation() {
    return Elevation;
  }

  public void setElevation(int elevation) {
    Elevation = elevation;
  }

  public String getLocation_Notes() {
    return Location_Notes;
  }

  public void setLocation_Notes(String location_Notes) {
    Location_Notes = location_Notes;
  }

  //may need adds for entry and plant
  
  
  public void addPlant_Species(Plant_Species species) {
    this.Plant_Species_List.add(species);
    species.getLocations().add(this);
  }
  
  @SuppressWarnings("unlikely-arg-type")
  public void removePlant_Species(String speciesLatin) {
    Plant_Species species = this.Plant_Species_List.stream().filter(t -> t.getPlant_Latin_Name().equals(speciesLatin)).findFirst().orElse(null);
    if(species != null) {
    this.Plant_Species_List.remove(this);
    }
  }
  


  

}
