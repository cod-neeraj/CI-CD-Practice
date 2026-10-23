package com.example.demo.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import com.example.demo.Model.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final ConcurrentMap<Long, Task> tasks =
            new ConcurrentHashMap<>();

    private final AtomicLong idGenerator = new AtomicLong(0);

    public List<Task> getAllTasks() {
        return tasks.values().stream()
                .sorted(Comparator.comparing(Task::id))
                .toList();
    }

    public Task createTask(String title) {
        long id = idGenerator.incrementAndGet();
        Task task = new Task(id, title.trim());
        tasks.put(id, task);
        return task;
    }

    public Optional<Task> getTaskById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public boolean deleteTask(Long id) {
        return tasks.remove(id) != null;
    }
}
