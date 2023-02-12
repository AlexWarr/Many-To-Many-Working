package com.Alex.Forest.Entity;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.springframework.data.relational.core.mapping.Column;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;


@Entity
@Table(name="Plant_Species")
public class Plant_Species {
  
  @Id
  @Column(value = "Plant_Latin_Name")
  private String Plant_Latin_Name;
  
  @Column(value = "Plant_Common_Name")
  private String Plant_Common_Name;
  
  @Column(value = "Plant_Phylum")
  private String Plant_Phylum;
  
  @Column(value = "Body_Trunk_Stem_Description")
  private String Body_Trunk_Stem_Description;
  
  @Column(value = "Branch_Leaf_Pattern_Description")
  private String Branch_Leaf_Pattern_Description;
  
  @Column(value = "Flower_Fruit_Description")
  private String Flower_Fruit_Description;
  
  @Column(value = "Toxic")
  private Boolean Toxic;
  
  @Column(value = "Edible")
  private Boolean Edible;
  
  @Column(value = "Medicinal")
  private Boolean Medicinal;
  
  @Column(value = "Plant_Description")
  private String Plant_Description;
  
  
  @OneToMany(mappedBy = "Plant_Species")
  private List<Plant> plants =  new ArrayList<Plant>();
  
  @OneToMany(mappedBy = "Plant_Species")
  private List<Entry> entries =  new ArrayList<Entry>();
  
  
  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  }, mappedBy = "Location")
  @JsonIgnore
  private List<Location> locations = new ArrayList<Location>();
  
  public Plant_Species() {
    
  }

  public String getPlant_Latin_Name() {
    return Plant_Latin_Name;
  }

  public void setPlant_Latin_Name(String plant_Latin_Name) {
    Plant_Latin_Name = plant_Latin_Name;
  }

  public String getPlant_Common_Name() {
    return Plant_Common_Name;
  }

  public void setPlant_Common_Name(String plant_Common_Name) {
    Plant_Common_Name = plant_Common_Name;
  }

  public String getPlant_Phylum() {
    return Plant_Phylum;
  }

  public void setPlant_Phylum(String plant_Phylum) {
    Plant_Phylum = plant_Phylum;
  }

  public String getBody_Trunk_Stem_Description() {
    return Body_Trunk_Stem_Description;
  }

  public void setBody_Trunk_Stem_Description(String body_Trunk_Stem_Description) {
    Body_Trunk_Stem_Description = body_Trunk_Stem_Description;
  }

  public String getBranch_Leaf_Pattern_Description() {
    return Branch_Leaf_Pattern_Description;
  }

  public void setBranch_Leaf_Pattern_Description(String branch_Leaf_Pattern_Description) {
    Branch_Leaf_Pattern_Description = branch_Leaf_Pattern_Description;
  }

  public String getFlower_Fruit_Description() {
    return Flower_Fruit_Description;
  }

  public void setFlower_Fruit_Description(String flower_Fruit_Description) {
    Flower_Fruit_Description = flower_Fruit_Description;
  }

  public Boolean getToxic() {
    return Toxic;
  }

  public void setToxic(Boolean toxic) {
    Toxic = toxic;
  }

  public Boolean getEdible() {
    return Edible;
  }

  public void setEdible(Boolean edible) {
    Edible = edible;
  }

  public Boolean getMedicinal() {
    return Medicinal;
  }

  public void setMedicinal(Boolean medicinal) {
    Medicinal = medicinal;
  }

  public String getPlant_Description() {
    return Plant_Description;
  }

  public void setPlant_Description(String plant_Description) {
    Plant_Description = plant_Description;
  }
  
  public List<Location> getLocations(){
    return locations;
  }
  public void setLocations(List<Location> locations){
    this.locations = locations;
  }
  
  
/*
 *   @OneToMany(mappedBy = "Plant_Species")
  private List<Plant> plants =  new ArrayList<Plant>();
  
  @OneToMany(mappedBy = "Plant_Species")
  private List<Entry> entries =  new ArrayList<Entry>();
  
  
  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  }, mappedBy = "Location")
  @JsonIgnore
  private List<Plant_Species> locations = new ArrayList<Plant_Species>();
 */
}
