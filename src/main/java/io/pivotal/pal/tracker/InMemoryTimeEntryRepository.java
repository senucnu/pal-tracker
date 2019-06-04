package io.pivotal.pal.tracker;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryTimeEntryRepository extends TimeEntryRepository {

    Map<Long, TimeEntry> repositoryMap = new HashMap();
    long currentIdValue;

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(++currentIdValue);
        repositoryMap.putIfAbsent(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long id) {
        return repositoryMap.get(id);
    }

    public List<TimeEntry> list() {
        return new ArrayList<>(repositoryMap.values());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        timeEntry.setId(id);
        repositoryMap.replace(timeEntry.getId(), timeEntry);
        return repositoryMap.get(id);
    }

    public TimeEntry delete(long id) {
        return repositoryMap.remove(id);
    }
}
