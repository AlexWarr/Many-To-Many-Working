package com.Alex.Forest.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Alex.Forest.Entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {

  List<Location> findLocationBySpeciesName(String name);
}
