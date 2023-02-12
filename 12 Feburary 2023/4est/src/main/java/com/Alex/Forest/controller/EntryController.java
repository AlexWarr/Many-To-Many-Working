package com.Alex.Forest.controller;

import java.util.List;
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
import com.Alex.Forest.repository.SpeciesRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/4est")
public class EntryController {
  @Autowired
  private SpeciesRepository speciesRepository;

  @Autowired
  private EntryRepository entryRepository;
  
  @Autowired
  private MomentRepository momentRepository;

  @GetMapping("/speciesList/{Plant_Latin_Name}/entries")
  public ResponseEntity<List<Entry>> getAllEntriesBySpeciesName(@PathVariable(value = "Plant_Latin_Name") String name) {
    if (!speciesRepository.existsById(name)) {
      throw new ResourceNotFoundException("Plant_Species", "Name", name);
    }

    List<Entry> entries = entryRepository.findEntryBySpeciesName(name);
    return new ResponseEntity<>(entries, HttpStatus.OK);
  }

  @GetMapping("/entries/{id}")
  public ResponseEntity<Entry> getEntriesBySpeciesName(@PathVariable(value = "id") Integer id) {
    Entry entry = entryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Entry", "ID", id));

    return new ResponseEntity<>(entry, HttpStatus.OK);
  }

  @PostMapping("/speciesList/{Plant_Latin_Name}/entries")
  public ResponseEntity<Entry> createEntry(@PathVariable(value = "Plant_Latin_Name") String name,
      @RequestBody Entry entryRequest) {
    Entry entry = speciesRepository.findById(name).map(species -> {
      entryRequest.setSpecies(species);
      return entryRepository.save(entryRequest);
    }).orElseThrow(() -> new ResourceNotFoundException("Plant_Species", "Name", name));

    return new ResponseEntity<>(entry, HttpStatus.CREATED);
  }

  @PutMapping("/entries/{id}")
  public ResponseEntity<Entry> updateEntry(@PathVariable("id") Integer id, @RequestBody Entry entryRequest) {
    Entry entry = entryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Entry", "ID", id));

    entry.setNotes(entryRequest.getNotes());

    return new ResponseEntity<>(entryRepository.save(entry), HttpStatus.OK);
  }

  @DeleteMapping("/entries/{id}")
  public ResponseEntity<HttpStatus> deleteEntry(@PathVariable("id") Integer id) {
    entryRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @DeleteMapping("/speciesList/{Plant_Latin_Name}/entries")
  public ResponseEntity<List<Entry>> deleteAllEntriesOfSpecies(@PathVariable(value = "Plant_Latin_Name") String name) {
    if (!speciesRepository.existsById(name)) {
      throw new ResourceNotFoundException("Plant_Species", "Name", name);
    }

    entryRepository.deleteEntryBySpeciesName(name);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  
  @DeleteMapping("/entries/{id}")
  public ResponseEntity<HttpStatus> deleteEntrybyMoment(@PathVariable("id") Integer id) {
    List<Moment> eList = momentRepository.findMomentByEntryID(id);
    if (!eList.isEmpty()) {
      momentRepository.deleteByEntryId(id);
    }
    
    entryRepository.deleteById(id);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  
}



