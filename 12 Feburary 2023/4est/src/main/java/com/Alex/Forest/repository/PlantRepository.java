package com.Alex.Forest.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.Alex.Forest.Entity.Plant;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {

  List<Plant> findPlantByEntryID(int id);
  List<Plant> findPlantBySpeciesName(String name);
  List<Plant> findPlantByLocationName(String name);
  
  @Transactional
  void deletePlantByEntryID(int id);
  
  @Transactional
  void deletePlantBySpeciesName(String name);
  
  @Transactional
  void deletePlantByLocationName(String name);
}
