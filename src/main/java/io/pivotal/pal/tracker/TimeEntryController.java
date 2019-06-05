package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        ResponseEntity<TimeEntry> response = ResponseEntity.status(HttpStatus.CREATED).body(timeEntry);
        return response;
    }

    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {

        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);

        ResponseEntity<TimeEntry> response;
        if (timeEntry == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            actionCounter.increment();
            response = ResponseEntity.status(HttpStatus.OK).body(timeEntry);
        }

        return response;

    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        actionCounter.increment();
        List<TimeEntry> list = timeEntryRepository.list();
        ResponseEntity<List<TimeEntry>> response = ResponseEntity.status(HttpStatus.OK).body(list);
        return response;
    }

    @PutMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry update = timeEntryRepository.update(timeEntryId, expected);
        ResponseEntity<TimeEntry> response;
        if (update == null)
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else {
            actionCounter.increment();
            response = ResponseEntity.status(HttpStatus.OK).body(update);
        }

        return response;
    }

    @DeleteMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long timeEntryId) {

        TimeEntry delete = timeEntryRepository.delete(timeEntryId);

        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());

        ResponseEntity<TimeEntry> response =ResponseEntity.status(HttpStatus.NO_CONTENT).body(delete) ;
        return response;
    }
}
