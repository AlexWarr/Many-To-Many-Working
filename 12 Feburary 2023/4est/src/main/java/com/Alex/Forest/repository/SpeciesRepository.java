package com.Alex.Forest.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Alex.Forest.Entity.Plant_Species;

@Repository
public interface SpeciesRepository extends JpaRepository<Plant_Species, String> {

  List<Plant_Species> findSpeciesByLocationName(String name);
  
  List<Plant_Species> findSpeciesByEntryId(int id);

  
  
}
