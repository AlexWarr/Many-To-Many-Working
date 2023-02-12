package com.Alex.Forest.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.Alex.Forest.Entity.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Integer> {

  List<Entry> findEntryByPlantID(int id);
  List<Entry> findEntryBySpeciesName(String name);
  List<Entry> findEntryByLocationName(String name);
  
  @Transactional
  void deleteEntryByPlantID(int id);
  
  @Transactional
  void deleteEntryBySpeciesName(String name);
  
  @Transactional
  void deleteEntryByLocationName(String name);
}
