package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

    TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {

        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);

        ResponseEntity<TimeEntry> response = ResponseEntity.status(HttpStatus.CREATED).body(timeEntry);
        return response;
    }

    @GetMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {

        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);

        ResponseEntity<TimeEntry> response;
        if (timeEntry == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            response = ResponseEntity.status(HttpStatus.OK).body(timeEntry);
        }

        return response;

    }

    //change
    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {

        List<TimeEntry> list = timeEntryRepository.list();
        ResponseEntity<List<TimeEntry>> response = ResponseEntity.status(HttpStatus.OK).body(list);
        return response;
    }

    @PutMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry update = timeEntryRepository.update(timeEntryId, expected);
        ResponseEntity<TimeEntry> response;
        if (update == null)
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            response = ResponseEntity.status(HttpStatus.OK).body(update);

        return response;
    }

    @DeleteMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long timeEntryId) {

        TimeEntry delete = timeEntryRepository.delete(timeEntryId);

        ResponseEntity<TimeEntry> response =ResponseEntity.status(HttpStatus.NO_CONTENT).body(delete) ;
        return response;
    }
}
