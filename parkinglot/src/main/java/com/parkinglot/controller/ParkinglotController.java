package com.parkinglot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.dto.ParkinglotDto;
import com.parkinglot.entity.Parkinglot;
import com.parkinglot.service.ParkinglotService;

@RestController
@RequestMapping("/api/parkinglot")
public class ParkinglotController {
 
	@Autowired
	ParkinglotService parkinglotService;


	//create
	@PostMapping
	public ResponseEntity<Parkinglot> createParkinglot(@RequestBody ParkinglotDto dto) {
		Parkinglot parkinglot = parkinglotService.createParkinglot(dto);
		return new ResponseEntity<Parkinglot>(parkinglot, HttpStatus.CREATED);
	}
 
	//update
	@PutMapping("/{parkid}")
	public ResponseEntity<Parkinglot> updateParkinglot(@PathVariable("parkid") long parkId,@RequestBody ParkinglotDto dto) {
		Parkinglot parkinglot = parkinglotService.updateParkinglot(parkId, dto);
		return new ResponseEntity<Parkinglot>(parkinglot, HttpStatus.OK);
	}

	//get a parking
	@GetMapping("/{parkid}")
	public ResponseEntity<Parkinglot> getParkinglot(@PathVariable("parkid") long parkId) {
		Parkinglot parkinglot = parkinglotService.getParkinglot(parkId);
		return new ResponseEntity<Parkinglot>(parkinglot, HttpStatus.OK);
	}

	//get all parkings
	@GetMapping
	public ResponseEntity<List<Parkinglot>> getAllParkinglot() {
		List<Parkinglot> parkinglot = parkinglotService.getAllParkinglot();
		return new ResponseEntity<List<Parkinglot>>(parkinglot, HttpStatus.OK);
	}

	//delete a parking by userid
	@DeleteMapping("/{parkid}/{userid}")
	public ResponseEntity<?> deleteParkinglot(@PathVariable("parkid") long parkId,
			@PathVariable("userid") long userId) {
		parkinglotService.deleteParkinglot(parkId, userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	//get all parking status by userid
	@GetMapping("/status-check/{userid}")
	public ResponseEntity<List<Parkinglot>> statusCheck(@PathVariable("userid") long userId) {
		List<Parkinglot> parkinglot = parkinglotService.statusCheck(userId);
		return new ResponseEntity<List<Parkinglot>>(parkinglot, HttpStatus.OK);
	}

	//update status parking 
	@PutMapping("/{userid}/{parkid}/{status}")
	public ResponseEntity<Parkinglot> updateStatusInParkinglot(@PathVariable("userid") long userId,
			@PathVariable("parkid") long parkId, @PathVariable("status") String status) {
		Parkinglot parkinglot = parkinglotService.statusChange(userId, parkId, status);
		return new ResponseEntity<Parkinglot>(parkinglot, HttpStatus.OK);
	}

	// Entry
	@PostMapping("/entry/{parkid}/{userid}")
	public ResponseEntity<Parkinglot> entry(@PathVariable("parkid") long parkId, @PathVariable("userid") long userId) {
		Parkinglot parkinglot = parkinglotService.entry(parkId, userId);
		return new ResponseEntity<Parkinglot>(parkinglot, HttpStatus.OK);
	}

	// Exit
	@PostMapping("/exit/{parkid}/{userid}")
	public ResponseEntity<Parkinglot> exit(@PathVariable("parkid") long parkId, @PathVariable("userid") long userId) {
		Parkinglot parkinglot = parkinglotService.exit(parkId, userId);
		return new ResponseEntity<Parkinglot>(parkinglot, HttpStatus.OK);
	}

}
