package com.Alex.Forest.controller;

import java.util.ArrayList;
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
import com.Alex.Forest.Entity.Location;
import com.Alex.Forest.Entity.Plant_Species;
import com.Alex.Forest.exception.ResourceNotFoundException;
import com.Alex.Forest.repository.LocationRepository;
import com.Alex.Forest.repository.SpeciesRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/4est")
public class SpeciesController {
  @Autowired
  private LocationRepository locationRepository;

  @Autowired
  private SpeciesRepository speciesRepository;

  @GetMapping("/Plant_Species_List")
  public ResponseEntity<List<Plant_Species>> getAllSpecies() {
    List<Plant_Species> speciesList = new ArrayList<Plant_Species>();

    speciesRepository.findAll().forEach(speciesList::add);

    if (speciesList.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(speciesList, HttpStatus.OK);
  }
  
  @GetMapping("/locations/{Location_Name}/speciesList")
  public ResponseEntity<List<Plant_Species>> getAllSpeciesByLocationName(@PathVariable(value = "Location_Name") String name) {
    if (!locationRepository.existsById(name)) {
      throw new ResourceNotFoundException("Location", "Name", name);
    }

    List<Plant_Species> speciesList = speciesRepository.findSpeciesByLocationName(name);
    return new ResponseEntity<>(speciesList, HttpStatus.OK);
  }

  @GetMapping("/Plant_Species_List/{name}")
  public ResponseEntity<Plant_Species> getSpeciesByName(@PathVariable(value = "Plant_Latin_Name") String name) {
    Plant_Species species = speciesRepository.findById(name)
        .orElseThrow(() -> new ResourceNotFoundException("Species", "Name", name));

    return new ResponseEntity<>(species, HttpStatus.OK);
  }
  
  @GetMapping("/Plant_Species_List/{Plant_Latin_Name}/locations")
  public ResponseEntity<List<Location>> getAllLocationsBySpeciesName(@PathVariable(value = "Plant_Latin_Name") String name) {
    if (!speciesRepository.existsById(name)) {
      throw new ResourceNotFoundException("Species", "Name", name);
    }

    List<Location> locations = locationRepository.findLocationBySpeciesName(name);
    return new ResponseEntity<>(locations, HttpStatus.OK);
  }

  @PostMapping("/locations/{Location_Name}/Plant_Species_List")
  public ResponseEntity<Plant_Species> addSpecies(@PathVariable(value = "Location_Name") String locationName, @RequestBody Plant_Species speciesRequest) {
    Plant_Species species = locationRepository.findById(locationName).map(location -> {
      String name = speciesRequest.getPlant_Latin_Name();
      
      // species is existed
      if (!name.isBlank()) {
        Plant_Species _species = speciesRepository.findById(name)
            .orElseThrow(() -> new ResourceNotFoundException("Species", "Name", name));
        location.addPlant_Species(_species);
        locationRepository.save(location);
        return _species;
      }
      
      // add and create new Plant_Species
      location.addPlant_Species(speciesRequest);
      return speciesRepository.save(speciesRequest);
    }).orElseThrow(() -> new ResourceNotFoundException("Species", "Name", locationName));

    return new ResponseEntity<>(species, HttpStatus.CREATED);
  }

  @PutMapping("/Plant_Species_List/{Plant_Latin_Name}")
  public ResponseEntity<Plant_Species> updateSpecies(@PathVariable("Plant_Latin_Name") String name, @RequestBody Plant_Species speciesRequest) {
    Plant_Species species = speciesRepository.findById(name)
        .orElseThrow(() -> new ResourceNotFoundException("Species", "Name", name));

    species.setPlant_Latin_Name(speciesRequest.getPlant_Latin_Name());

    return new ResponseEntity<>(speciesRepository.save(species), HttpStatus.OK);
  }
 
  @DeleteMapping("/locations/{Location_Name}/Plant_Species_List/{Plant_Latin_Name}")
  public ResponseEntity<HttpStatus> deleteSpeciesFromLocation(@PathVariable(value = "Location_Name") String locationName, @PathVariable(value = "Plant_Latin_Name") String name) {
    Location location = locationRepository.findById(locationName)
        .orElseThrow(() -> new ResourceNotFoundException("Location", "Name", locationName));
    
    location.removePlant_Species(name);
    locationRepository.save(location);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @DeleteMapping("/Plant_Species_List/{Plant_Latin_Name}")
  public ResponseEntity<HttpStatus> deleteSpecies(@PathVariable("Plant_Latin_Name") String name) {
    speciesRepository.deleteById(name);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /*
  @Autowired
  private SpeciesService speciesService;
  
  private SpeciesController(SpeciesService speciesService) {
    super();
    this.speciesService = speciesService;
  }
  
  //create a species API
  @PostMapping()
  public ResponseEntity<Plant_Species> saveSpecies(@RequestBody Plant_Species species){
    return new ResponseEntity<Plant_Species>(speciesService.saveSpecies(species),HttpStatus.CREATED); 
  }
  
  //Read all species API
  @GetMapping
  public List<Plant_Species> getAllSpecies(){
    return speciesService.getAllSpecies();
  }
  
  //Get Species by Id
  @GetMapping("{Plant_Latin_Name}")
  public ResponseEntity<Plant_Species> getSpeciesByLatin(@PathVariable("Plant_Latin_Name") String Latin){
    return new ResponseEntity<Plant_Species>(speciesService.getSpeciesByLatin(Latin), HttpStatus.OK);
  }
  
  //updating or putting plant instance by id
  @PutMapping("{Plant_Latin_Name}")
  public ResponseEntity<Plant_Species> updateSpecies(@PathVariable("Plant_Latin_Name") String Latin, @RequestBody Plant_Species species){
    return new ResponseEntity<Plant_Species>(speciesService.UpdateSpecies(species, Latin), HttpStatus.OK);
  }
  
  //Deleting plant instance by id
  @DeleteMapping("{Plant_Latin_Name}")
  public ResponseEntity<String> deletePlant(@PathVariable("Plant_Latin_Name") String Latin){
    speciesService.deleteSpecies(Latin);
    return new ResponseEntity<String>("Plant Instance Deleted", HttpStatus.OK);
  }
*/
}
