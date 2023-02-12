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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.Alex.Forest.Entity.Location;
import com.Alex.Forest.exception.ResourceNotFoundException;
import com.Alex.Forest.repository.LocationRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/4est")
public class LocationController {
  @Autowired
  private LocationRepository locationRepository;
 
  //Read all Locations API
  @GetMapping("/locations")
  public ResponseEntity<List<Location>> getAllLocations(@RequestParam(required = false) String name) { 
    List<Location> locations = new ArrayList<Location>();
    
    if (name == null)
      locationRepository.findAll().forEach(locations::add);
    else
      locationRepository.findLocationBySpeciesName(name).forEach(locations::add);

    if (locations.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(locations, HttpStatus.OK);
  }
   
  @GetMapping("/locations/{Name}")
  public ResponseEntity<Location> getLocationByName(@PathVariable("Location Name") String name) {
    Location location = locationRepository.findById(name)
        .orElseThrow(() -> new ResourceNotFoundException("Location", "Name", name));

    return new ResponseEntity<>(location, HttpStatus.OK);
  }
 
  @PostMapping("/locations")
  public ResponseEntity<Location> createLocation(@RequestBody Location location) {
    Location _location = locationRepository.save(new Location(location.getLocation_Name(), location.getGeography(), location.getVegetative_Zone(), location.getGrowing_Zone(), location.getElevation(), location.getLocation_Notes()));
    return new ResponseEntity<>(_location, HttpStatus.CREATED);
  }
  
  @PutMapping("/locations/{name}")
  public ResponseEntity<Location> updateTutorial(@PathVariable("Location_Name") String name, @RequestBody Location location) {
    Location _location = locationRepository.findById(name)
        .orElseThrow(() -> new ResourceNotFoundException("Location", "Name", name));

    _location.setLocation_Name(location.getLocation_Name());
    _location.setGeography(location.getGeography());
    _location.setVegetative_Zone(location.getVegetative_Zone());
    _location.setGrowing_Zone(location.getGrowing_Zone());
    _location.setElevation(location.getElevation());
    _location.setLocation_Notes(location.getLocation_Notes());
    
    return new ResponseEntity<>(locationRepository.save(_location), HttpStatus.OK);
  }
  
  @DeleteMapping("/locations/{name}")
  public ResponseEntity<Location> deleteLocation(@PathVariable("Location_Name") String name) {
    locationRepository.deleteById(name);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/locations/published")
  public ResponseEntity<List<Location>> findLocationBySpeciesName(@PathVariable("Plant_Latin_Name") String name) {
    List<Location> locations = locationRepository.findLocationBySpeciesName(name);

    if (locations.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    return new ResponseEntity<>(locations, HttpStatus.OK);
  }
/*
  private LocationService locationService;
  
  private LocationController(LocationService locationService) {
    super();
    this.locationService = locationService;
  }
  
  //create a Location API
  @PostMapping()
  public ResponseEntity<Location> saveLocation(@RequestBody Location location){
    return new ResponseEntity<Location>(locationService.saveLocation(location),HttpStatus.CREATED); 
  }
  

  
  //Get Species by Id
  @GetMapping("{Location_Name}")
  public ResponseEntity<Location> getLocationByName(@PathVariable("Location_Name") String name){
    return new ResponseEntity<Location>(locationService.getLocationByName(name), HttpStatus.OK);
  }
  
  //updating or putting plant instance by id
  @PutMapping("{Location_Name}")
  public ResponseEntity<Location> updateLocation(@PathVariable("Location_Name") String name, @RequestBody Location location){
    return new ResponseEntity<Location>(locationService.UpdateLocation(location, name), HttpStatus.OK);
  }
  
  //Deleting plant instance by id
  @DeleteMapping("{Location_Name}")
  public ResponseEntity<String> deleteLocation(@PathVariable("Location_Name") String name){
    locationService.deleteLocation(name);
    return new ResponseEntity<String>("Plant Instance Deleted", HttpStatus.OK);
  }
  */
}
