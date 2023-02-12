package com.Alex.Forest.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.Alex.Forest.Entity.Moment;

@Repository
public interface MomentRepository extends JpaRepository<Moment, LocalDateTime> {
  List<Moment> findMomentByEntryID(int id);

  @Transactional
  void deleteById(LocalDateTime id);
  
  @Transactional
  void deleteByEntryId(Integer id);

}
