package com.parkinglot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.parkinglot.dto.ParkinglotDto;
import com.parkinglot.entity.Parkinglot;
import com.parkinglot.entity.Role;
import com.parkinglot.entity.UserInfo;
import com.parkinglot.exception.AlreadyExistsException;
import com.parkinglot.exception.NotFoundException;
import com.parkinglot.mqtt.MqttService;
import com.parkinglot.repository.ParkinglotRepository;
import com.parkinglot.repository.UsersRepository;

@Service
public class ParkinglotService {

	@Autowired
	ParkinglotRepository parkinglotRepo;

	@Autowired
	UsersRepository usersRepo;

	@Autowired
	MqttService mqttService;

	@Value("${parkinglot.mqtt.entry}")
	private String entry;

	@Value("${parkinglot.mqtt.exit}")
	private String exit;

	@Value("${parkinglot.mqtt.cash}")
	private String cash;



	//create by admin
	public Parkinglot createParkinglot(ParkinglotDto dto) {

		try {

			Optional<UserInfo> user = usersRepo.findById(dto.getUserId());

			if (user != null && user.isPresent()) {
				Optional<Role> userWithAdminRole = user.get().getRoles().stream()
						.filter(e -> e.getName().equalsIgnoreCase("ROLE_ADMIN")).findFirst();
				if (userWithAdminRole.isPresent()) {
					Parkinglot parkinglot = new Parkinglot();
					parkinglot.setQuarter(dto.getQuarter());
					parkinglot.setVia(dto.getVia());
					parkinglot.setTotalPlaces(dto.getTotalPlaces());
					parkinglot.setCurrentState("INACTIVE");
					parkinglot.setFreeSeats(dto.getFreeSeats());

					parkinglot.setAddedBy(user.get());

					return parkinglotRepo.save(parkinglot);
				} else {
					throw new NotFoundException("Error : User don't have a permission to add a parkinglot");
				}
			} else {
				throw new NotFoundException("Error : User id not found");
			}
		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	//update by admin
	public Parkinglot updateParkinglot(long parkId, ParkinglotDto dto) {
		try {

			Optional<UserInfo> user = usersRepo.findById(dto.getUserId());

			if (user != null && user.isPresent()) {
				Optional<Role> userWithAdminRole = user.get().getRoles().stream()
						.filter(e -> e.getName().equalsIgnoreCase("ROLE_ADMIN")).findFirst();
				if (userWithAdminRole.isPresent()) {
					Parkinglot parkinglot = parkinglotRepo.findById(parkId)
							.orElseThrow(() -> new NotFoundException("Error : Parkinglot id not found"));
					parkinglot.setQuarter(dto.getQuarter());
					parkinglot.setVia(dto.getVia());
					parkinglot.setTotalPlaces(dto.getTotalPlaces());
					if (dto.getCurrentState() != null && !dto.getCurrentState().equals("")) {
						parkinglot.setCurrentState(dto.getCurrentState().toUpperCase());
					}
					parkinglot.setFreeSeats(dto.getFreeSeats());

					parkinglot.setAddedBy(user.get());

					return parkinglotRepo.save(parkinglot);
				} else {
					throw new NotFoundException("Error : User don't have a permission to update a parkinglot");
				}
			} else {
				throw new NotFoundException("Error : User id not found");
			}

		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	//Get a parking by user
	public Parkinglot getParkinglot(long parkId) {

		try {

			Optional<Parkinglot> parkinglot = parkinglotRepo.findById(parkId);

			if (parkinglot != null && parkinglot.isPresent()) {
				return parkinglotRepo.findById(parkId).get();
			} else {
				throw new NotFoundException("Error : Parkinglot id Not found");
			}

		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	//get all active parking by user
	public List<Parkinglot> getAllParkinglot() {
		try {
			return parkinglotRepo.findAllByCurrentState("ACTIVE");
		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	//delete a parking by admin
	public void deleteParkinglot(long parkId, long userId) {
		try {
			Optional<UserInfo> user = usersRepo.findById(userId);

			if (user != null && user.isPresent()) {
				Optional<Role> userWithAdminRole = user.get().getRoles().stream()
						.filter(e -> e.getName().equalsIgnoreCase("ROLE_ADMIN")).findFirst();
				if (userWithAdminRole.isPresent()) {

					Parkinglot parkinglot = parkinglotRepo.findById(parkId).get();

					if (parkinglot != null) {
						parkinglotRepo.deleteById(parkId);
					} else {
						throw new NotFoundException("Error : Parkinglot id Not found");
					}
				} else {
					throw new NotFoundException("Error : User don't have a permission to delete a parkinglot id");
				}
			} else {
				throw new NotFoundException("Error : User id not found");
			}
		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	
	//status check by admin
	public List<Parkinglot> statusCheck(long userId) {

		try {

			Optional<UserInfo> user = usersRepo.findById(userId);

			if (user != null && user.isPresent()) {
				Optional<Role> userWithAdminRole = user.get().getRoles().stream()
						.filter(e -> e.getName().equalsIgnoreCase("ROLE_ADMIN")).findFirst();
				if (userWithAdminRole.isPresent()) {

					List<Parkinglot> parkinglot = parkinglotRepo.findAll();
					return parkinglot;
				} else {
					throw new NotFoundException("Error : User don't have a permission to status check a parkinglot id");
				}
			} else {
				throw new NotFoundException("Error : User id not found");
			}

		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	//change status by admin
	public Parkinglot statusChange(long userId, long parkinglotId, String status) {
		try {

			Optional<UserInfo> user = usersRepo.findById(userId);
			if (user != null && user.isPresent()) {
				Optional<Role> userWithAdminRole = user.get().getRoles().stream()
						.filter(e -> e.getName().equalsIgnoreCase("ROLE_ADMIN")).findFirst();
				if (userWithAdminRole.isPresent()) {

					Parkinglot parkinglot = parkinglotRepo.findById(parkinglotId)
							.orElseThrow(() -> new NotFoundException("Error : Parkinglot id not found"));

					parkinglot.setCurrentState(status.toUpperCase());

					return parkinglotRepo.save(parkinglot);
				} else {
					throw new NotFoundException(
							"Error : User don't have a permission to change the status of parkinglot");
				}
			} else {
				throw new NotFoundException("Error : User id not found");
			}

		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	//entry by user
	public Parkinglot entry(long parkId, long userId) {

		try {

			Parkinglot parkinglot = parkinglotRepo.findByIdAndCurrentState(parkId, "ACTIVE");

			Optional<UserInfo> user = usersRepo.findById(userId);
			if (user != null && user.isPresent()) {
				Optional<Role> userWithUserRole = user.get().getRoles().stream()
						.filter(e -> !e.getName().equalsIgnoreCase("ROLE_ADMIN")).findFirst();
				if (userWithUserRole.isPresent()) {

					if (parkinglot != null) {
						if (user.get().getCurrentParkinglotId()==null && !parkinglot.getUsersInside().contains(user.get())){ 
												
							user.get().setCurrentParkinglotId(parkId);
							parkinglot.getUsersInside().add(user.get());
							parkinglot.setFreeSeats(parkinglot.getFreeSeats() - 1);
							
							String message = "User " + userId + ":" + " Parking " + parkId + ":" + " Free Seats " + parkinglot.getFreeSeats();
							mqttService.publish(entry, message);
							
						} else {
                        	throw new AlreadyExistsException("Error: User is already inside other parking lot");
						}		
					} else {
						throw new NotFoundException("Error : Parkinglot id Not found");
					}
					return parkinglotRepo.save(parkinglot);
				} else {
					throw new NotFoundException("Error : User don't have a permission to entry parkinglot");
				}
			} else {
				throw new NotFoundException("Error : User id not found");
			}

		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
	}



	//exit by user
	public Parkinglot exit(long parkId, long userId) {

		try {

			Parkinglot parkinglot = parkinglotRepo.findByIdAndCurrentState(parkId, "ACTIVE");		

			Optional<UserInfo> user = usersRepo.findById(userId);
			if (user != null && user.isPresent()) {
				Optional<Role> userWithUserRole = user.get().getRoles().stream()
						.filter(e -> !e.getName().equalsIgnoreCase("ROLE_ADMIN")).findFirst();
				if (userWithUserRole.isPresent()) {

					if (parkinglot != null) {
						if(user.get().getCurrentParkinglotId()==parkId && (parkinglot.getUsersInside().contains(user.get())) && user.get().getId()==userId){
													
							parkinglot.getUsersInside().remove(user.get());
							parkinglot.setFreeSeats(parkinglot.getFreeSeats() + 1);
							user.get().setCurrentParkinglotId(null);

							// Payment
							String messageForCashDevice = "User " + userId + ":" + " paid in the parking " + parkId;
							String topic = cash;
							mqttService.publish(topic, messageForCashDevice);

							// Exit device
							String messageForExitTopic = "User " + userId + ":" + " Parking " + parkId + ":" + " Free Seats " + parkinglot.getFreeSeats();
							String topic1 = exit;
							mqttService.publish(topic1, messageForExitTopic); 
							
						} else {
							throw new NotFoundException("Error : You are not inside the parking");
						}
					} else {
						throw new NotFoundException("Error : Parkinglot id Not found");
					}
					return parkinglotRepo.save(parkinglot);
				} else {
					throw new NotFoundException("Error : User don't have a permission to exit parkinglot");
				}
			} else {
				throw new NotFoundException("Error : User id not found");
			}

		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
	}
}
