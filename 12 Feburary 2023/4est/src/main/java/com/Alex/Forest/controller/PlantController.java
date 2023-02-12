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
import com.Alex.Forest.Entity.Plant;
import com.Alex.Forest.exception.ResourceNotFoundException;
import com.Alex.Forest.repository.PlantRepository;
import com.Alex.Forest.repository.SpeciesRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/4est")
public class PlantController {
  @Autowired
  private SpeciesRepository speciesRepository;

  @Autowired
  private PlantRepository plantRepository;

  @GetMapping("/speciesList/{Plant_Latin_Name}/plants")
  public ResponseEntity<List<Plant>> getAllPlantsBySpeciesName(@PathVariable(value = "Plant_Latin_Name") String name) {
    if (!speciesRepository.existsById(name)) {
      throw new ResourceNotFoundException("Plant_Species", "Name", name);
    }

    List<Plant> plants = plantRepository.findPlantBySpeciesName(name);
    return new ResponseEntity<>(plants, HttpStatus.OK);
  }

  @GetMapping("/plants/{id}")
  public ResponseEntity<Plant> getPlantsBySpeciesName(@PathVariable(value = "id") Long id) {
    Plant plant = plantRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Plant", "ID", id));

    return new ResponseEntity<>(plant, HttpStatus.OK);
  }

  @PostMapping("/speciesList/{Plant_Latin_Name}/plants")
  public ResponseEntity<Plant> createPlant(@PathVariable(value = "Plant_Latin_Name") String name,
      @RequestBody Plant plantRequest) {
    Plant plant = speciesRepository.findById(name).map(species -> {
      plantRequest.setSpecies(species);
      return plantRepository.save(plantRequest);
    }).orElseThrow(() -> new ResourceNotFoundException("Plant_Species", "Name", name));

    return new ResponseEntity<>(plant, HttpStatus.CREATED);
  }

  @PutMapping("/plants/{id}")
  public ResponseEntity<Plant> updatePlant(@PathVariable("id") long id, @RequestBody Plant plantRequest) {
    Plant plant = plantRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Plant", "ID", id));
    plant.setBody_Trunk_Stem_Description(
        plantRequest.getBody_Trunk_Stem_Description());
   plant.setBranch_Leaf_Pattern_Description(
       plantRequest.getBranch_Leaf_Pattern_Description());
    plant.setFlower_Fruit_Description(plantRequest.getFlower_Fruit_Description());
    plant.setPlant_Notes(plantRequest.getPlant_Notes());

    return new ResponseEntity<>(plantRepository.save(plant), HttpStatus.OK);
  }

  @DeleteMapping("/plants/{id}")
  public ResponseEntity<HttpStatus> deletePlant(@PathVariable("id") long id) {
    plantRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @DeleteMapping("/speciesList/{Plant_Latin_Name}/plants")
  public ResponseEntity<List<Plant>> deleteAllPlantsOfTutorial(@PathVariable(value = "Plant_Latin_Name") String name) {
    if (!speciesRepository.existsById(name)) {
      throw new ResourceNotFoundException("Plant_Species", "Name", name);
    }

    plantRepository.deletePlantBySpeciesName(name);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}


