/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author hcadavid
 */
@RestController
public class BlueprintAPIController {

    @Autowired
    BlueprintsServices blueprintsServices;

    @RequestMapping(value = "/blueprints",method = RequestMethod.GET)
    public ResponseEntity<?> getBluePrints() {
        Set<Blueprint> blueprintSet = null;
        try {
            blueprintSet = blueprintsServices.getAllBlueprints();
            return new ResponseEntity<>(blueprintSet, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error" + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/blueprints/{Author}",method = RequestMethod.GET)
    public ResponseEntity<?> getBluePrintsByAuthor(@PathVariable String Author) {
        try {
            Set<Blueprint> blueprintSet = blueprintsServices.getBlueprintsByAuthor(Author);
            if(blueprintSet.isEmpty()) {
                return new ResponseEntity<>("404 Not Found", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(blueprintSet, HttpStatus.ACCEPTED);
            }
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error" + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/blueprints/{Author}/{bpname}",method = RequestMethod.GET)
    public ResponseEntity<?> getBluePrintsByAuthor(@PathVariable String Author, @PathVariable String bpname) {
        try {
            Blueprint blueprint = blueprintsServices.getBlueprint(Author,bpname);
            if(blueprint == null) {
                return new ResponseEntity<>("404 Not Found", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(blueprint, HttpStatus.ACCEPTED);
            }
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error" + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/blueprints/agregar",method = RequestMethod.POST)
    public ResponseEntity<?> createNewBluePrints(@RequestBody Blueprint bp) {
        try {
            blueprintsServices.addNewBlueprint(bp);
            return new ResponseEntity<>("El plano se agrego correctamente",HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error" + ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/blueprints/{Author}/{bpname}",method = RequestMethod.PUT)
    public ResponseEntity<?> updateBluePrint(@PathVariable String author, @PathVariable String bpname, @RequestBody List<Point> points) {
        try {
            blueprintsServices.updateBluePrint(author,bpname,points);
            return new ResponseEntity<>("El plano se actualizo correctamente",HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error" + ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }


}

