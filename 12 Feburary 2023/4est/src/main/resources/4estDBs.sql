DROP SCHEMA IF EXISTS 4est ;
CREATE SCHEMA IF NOT EXISTS 4est DEFAULT CHARACTER SET utf8 ;
USE 4est ;

DROP TABLE IF EXISTS 4est.Location_has_Plant_Species ;
DROP TABLE IF EXISTS 4est.Entry ;
DROP TABLE IF EXISTS 4est.Plant ;
DROP TABLE IF EXISTS 4est.Location ;
DROP TABLE IF EXISTS 4est.Plant_Species ;
DROP TABLE IF EXISTS 4est.Moment ;

CREATE TABLE Moment (
  Date DATETIME NOT NULL,
  Season VARCHAR(45) NOT NULL,
  Plant_Growth_Phase VARCHAR(45) NOT NULL,
  Weather VARCHAR(45) NOT NULL,
  PRIMARY KEY (Date)
);

CREATE TABLE Plant_Species (
  Plant_Latin_Name VARCHAR(100) NOT NULL,
  Plant_Common_Name VARCHAR(50) NOT NULL,
  Plant_Phylum VARCHAR(45) NOT NULL,
  Body_Trunk_Stem_Description LONGTEXT NOT NULL,
  Branch_Leaf_Pattern_Description LONGTEXT NOT NULL,
  Flower_Fruit_Description LONGTEXT NOT NULL,
  Toxic TINYINT NOT NULL,
  Edible TINYINT NOT NULL,
  Medicinal TINYINT NOT NULL,
  Plant_Description LONGTEXT NOT NULL,
  PRIMARY KEY (Plant_Latin_Name)
);


CREATE TABLE Location (
  Location_Name VARCHAR(60) NOT NULL,
  Geography VARCHAR(45) NOT NULL,
  Vegetative_Zone VARCHAR(45) NOT NULL,
  Growing_Zone VARCHAR(10) NULL,
  Elevation INT NULL,
  Location_Notes LONGTEXT NOT NULL,
  PRIMARY KEY (Location_Name)
);



CREATE TABLE Plant (
  Plant_ID INT AUTO_INCREMENT NOT NULL,
  Body_Trunk_Stem_Description LONGTEXT NULL,
  Branch_Leaf_Pattern_Description LONGTEXT NULL,
  Flower_Fruit_Description LONGTEXT NULL,
  Plant_Notes LONGTEXT NOT NULL,
  Plant_Latin_Name VARCHAR(100) NOT NULL,
  Location_Name VARCHAR(60) NOT NULL,
  PRIMARY KEY (Plant_ID),
  FOREIGN KEY (Plant_Latin_Name) REFERENCES Plant_Species (Plant_Latin_Name)  ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (Location_Name) REFERENCES Location (Location_Name) ON DELETE CASCADE ON UPDATE CASCADE
);




CREATE TABLE Entry (
  Entry_Number INT AUTO_INCREMENT NOT NULL,
  Notes LONGTEXT NOT NULL,
  Location_Name VARCHAR(60) NOT NULL,
  Plant_ID INT NOT NULL,
  Plant_Latin_Name VARCHAR(100) NOT NULL,
  Date DATETIME NOT NULL,
  PRIMARY KEY (Entry_Number, Date),
  FOREIGN KEY (Location_Name) REFERENCES Location (Location_Name) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (Plant_ID) REFERENCES Plant (Plant_ID) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (Plant_Latin_Name) REFERENCES Plant (Plant_Latin_Name) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (Date) REFERENCES Moment (Date) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE Location_has_Plant_Species (
  Location_Name VARCHAR(60) NOT NULL,
  Plant_Latin_Name VARCHAR(100) NOT NULL,
  FOREIGN KEY (Location_Name) REFERENCES Location (Location_Name) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (Plant_Latin_Name) REFERENCES Plant_Species (Plant_Latin_Name) ON DELETE CASCADE ON UPDATE CASCADE,
  UNIQUE KEY (Location_Name, Plant_Latin_Name)
);
