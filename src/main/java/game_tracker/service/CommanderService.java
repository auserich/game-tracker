package game_tracker.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import game_tracker.exception.ResourceAlreadyExistsException;
import game_tracker.exception.ResourceNotFoundException;
import game_tracker.exception.UsernameTakenException;
import game_tracker.exception.ValidationException;
import game_tracker.model.Commander;
import game_tracker.repository.CommanderRepository;

@Service
public class CommanderService {
	
	@Autowired
	CommanderRepository repo;
	
	public List<Commander> getAllCommanders() {
		return repo.findAll();
	}
	
	public Commander getCommanderById(int id) throws ResourceNotFoundException {
		Optional<Commander> found = repo.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Commander", id);
		}
		return found.get();
	}
	
	// TODO: Figure out better solution that passing -1 as an id to exception
	public Commander getCommanderByName(String name) throws ResourceNotFoundException {
		Optional<Commander> found = repo.findByName(name);
		if (found.isEmpty()) {
			// If not found in database, throw
			throw new ResourceNotFoundException("Commander", -1);
		}
		return found.get();
	}
	
	// TODO: Probably make a CommanderNameTakenException
	public Commander createCommander(Commander commander) throws UsernameTakenException, ValidationException {
		if (commander.getName() == null || commander.getColorIdentity() == null) {
			throw new ValidationException();
		}
		
		Optional<Commander> exists = repo.findByName(commander.getName());
		if (!exists.isEmpty()) {
			throw new UsernameTakenException(commander.getName());
		}
		commander.setId(null);
		Commander created = repo.save(commander);
		return created;
	}
		
	private Commander parseApiResponseAndSave(String apiResponse) {
		ObjectMapper objectMapper = new ObjectMapper(); // You may need to configure this based on your JSON structure
        try {
            // Parse the API response into a Map to extract relevant information
            Map<String, Object> apiResponseMap = objectMapper.readValue(apiResponse, new TypeReference<Map<String, Object>>() {});

            // Extract the first card from the "cards" array
            List<Map<String, Object>> cards = (List<Map<String, Object>>) apiResponseMap.get("cards");
            if (cards != null && !cards.isEmpty()) {
                Map<String, Object> firstCard = cards.get(0);

                // Extract name and colorIdentity from the card
                String commanderName = (String) firstCard.get("name");
                List<String> colorIdentityList = (List<String>) firstCard.get("colorIdentity");

                // Convert colorIdentityList to a single string
                String colorIdentity = String.join("", colorIdentityList);

                // Check if the commander with the same name already exists in the database
                Optional<Commander> existingCommander = repo.findByName(commanderName);

                // If the commander exists, update its colorIdentity; otherwise, create a new commander
                Commander commander;
                if (existingCommander.isPresent()) {
                    commander = existingCommander.get();
                    commander.setColorIdentity(colorIdentity);
                } else {
                    commander = new Commander(null, commanderName, null, colorIdentity);
                }

                // Save the commander to the database
                return repo.save(commander);
            }
        } catch (JsonProcessingException e) {
            // Handle parsing exception if needed
            e.printStackTrace();
            // You might want to throw a custom exception or handle the error appropriately
        }

        // Return null if parsing fails or no cards are found
        return null;
    }
	
	public Commander createCommanderBySearch(String name) throws UsernameTakenException, ResourceNotFoundException, ResourceAlreadyExistsException, ValidationException {
		Optional<Commander> exists = repo.findByPartialName(name);
		if (exists.isPresent()) {
			// If found in database, return it
			throw new ResourceAlreadyExistsException("Commander", -1);
		}
		else {
			// If not found in database, make an API call to fetch data
			String apiUrl = "https://api.magicthegathering.io/v1/cards?name=" + name;
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);
			String responseBody = response.getBody();
			
			// Check if the response body is empty
	        if (responseBody == null || responseBody.isEmpty()) {
	            throw new ResourceNotFoundException("Commander", -1);
	        }
						
			// Parse and return data
			Commander commanderFromApi = parseApiResponseAndSave(responseBody);
			
			// Validate required fields
			if (commanderFromApi.getName() == null || commanderFromApi.getColorIdentity() == null) {
				throw new ValidationException();
			}
			
			return commanderFromApi;
		}
	}
	
	public Commander updateCommander(Commander commander) throws ResourceNotFoundException {
		if (!repo.existsById(commander.getId())) {
			throw new ResourceNotFoundException("Commander", commander.getId());
		}
		return repo.save(commander);
	}
	
	public Commander deleteCommanderById(int id) throws ResourceNotFoundException {
		Optional<Commander> found = repo.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Commander", id);
		}
		repo.deleteById(id);
		return found.get();
	}
}
