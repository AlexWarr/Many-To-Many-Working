package com.Alex.Forest.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.relational.core.mapping.Column;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import lombok.Data;

@Data
@Entity
@Table(name="Plant")
public class Plant {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int Plant_ID;
  
  @Column(value= "Body_Trunk_Stem_Description")
  private String Body_Trunk_Stem_Description;
  @Column(value= "Branch_Leaf_Pattern_Description")
  private String Branch_Leaf_Pattern_Description;
  @Column(value= "Flower_Fruit_Description")
  private String Flower_Fruit_Description;
  @Column(value= "Plant_Notes")
  private String Plant_Notes;

  //Foreign Keys

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name= "Plant_Latin_Name", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Plant_Species species;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name= "Location_Name", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Location location;

  
  @OneToMany(mappedBy = "Plant_ID")
  private List<Entry> entries =  new ArrayList<Entry>();
  
}
