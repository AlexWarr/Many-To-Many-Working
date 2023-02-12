package com.Alex.Forest.controller;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Alex.Forest.Entity.Entry;
import com.Alex.Forest.Entity.Moment;
import com.Alex.Forest.exception.ResourceNotFoundException;
import com.Alex.Forest.repository.EntryRepository;
import com.Alex.Forest.repository.MomentRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/4est")
public class MomentController {
  @Autowired
  private MomentRepository momentRepository;

  @Autowired
  private EntryRepository entryRepository;

  @GetMapping({ "/Moment/{id}", "/entries/{DTG}/Moment" })
  public ResponseEntity<Moment> getMomentByDTG(@PathVariable(value = "id") LocalDateTime dtg) {
    Moment Moment = momentRepository.findById(dtg)
        .orElseThrow(() -> new ResourceNotFoundException("Moment", "DTG", dtg));

    return new ResponseEntity<>(Moment, HttpStatus.OK);
  }

  @PostMapping("/entries/{entryId}/Moment")
  public ResponseEntity<Moment> createMoment(@PathVariable(value = "entryId") Integer entryId,
      @RequestBody Moment momentRequest) {
    Entry entry = entryRepository.findById(entryId)
        .orElseThrow(() -> new ResourceNotFoundException("Entry", "ID", entryId));
    momentRequest.setEntry(entry);
    Moment Moment = momentRepository.save(momentRequest);

    return new ResponseEntity<>(Moment, HttpStatus.CREATED);
  }

  @PutMapping("/Moment/{id}")
  public ResponseEntity<Moment> updateMoment(@PathVariable("id") LocalDateTime dtg,
      @RequestBody Moment MomentRequest) {
    Moment Moment = momentRepository.findById(dtg)
        .orElseThrow(() -> new ResourceNotFoundException("Moment", "DTG", dtg));

    Moment.setSeason(MomentRequest.getSeason());
    Moment.setPlant_growth_phase(MomentRequest.getPlant_growth_phase());
    Moment.setWeather(MomentRequest.getWeather());

    return new ResponseEntity<>(momentRepository.save(Moment), HttpStatus.OK);
  }

  @DeleteMapping("/Moment/{id}")
  public ResponseEntity<HttpStatus> deleteMoment(@PathVariable("id") LocalDateTime dtg) {
    momentRepository.deleteById(dtg);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/entries/{entryId}/Moment")
  public ResponseEntity<Moment> deleteMomentOfEntry(@PathVariable(value = "entryId") Integer entryId) {
    if (!entryRepository.existsById(entryId)) {
      throw new ResourceNotFoundException("Entry", "ID", entryId);
    }

    momentRepository.deleteByEntryId(entryId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
