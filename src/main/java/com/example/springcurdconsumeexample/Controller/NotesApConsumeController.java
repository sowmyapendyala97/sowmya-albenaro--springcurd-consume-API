package com.example.springcurdconsumeexample.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.springcurdconsumeexample.Entity.Notes;

@RestController
public class NotesApConsumeController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/getAllNotes")
	public List<Notes> getAllNotes() {
		ResponseEntity<Notes[]> response = restTemplate.getForEntity("http://localhost:8081/getAllNotes",
				Notes[].class);

		Notes[] arr = response.getBody();

		return Arrays.stream(arr).collect(Collectors.toList());
	}

	@PostMapping("/createNotes")
	public ResponseEntity<Notes> createNote(@RequestBody Notes notes) {
		ResponseEntity<Notes> arr = restTemplate.postForEntity("http://localhost:8081/createNote", notes, Notes.class);
		return arr;

	}

	@PutMapping("/updateNotes/{title}")
	public ResponseEntity<?> updateNotes(@PathVariable String title, @RequestBody Notes notes) {
		HttpEntity<Notes> entity = new HttpEntity<>(notes);

		ResponseEntity<Object> arr = restTemplate.exchange("http://localhost:8081/updateNotes/" + title, HttpMethod.PUT,
				entity, Object.class);

		return arr;

	}

	@DeleteMapping("/deleteNotes/{title}")
	public ResponseEntity<String> deleteNotes(@PathVariable String title) {

		HttpEntity<Notes> entity = new HttpEntity<>(new HttpHeaders());

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8081/deleteById/" + title,
				HttpMethod.DELETE, entity, String.class);

		return response;

	}

}
