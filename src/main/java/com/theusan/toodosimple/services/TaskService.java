package com.theusan.toodosimple.services;

import com.theusan.toodosimple.models.Task;
import com.theusan.toodosimple.models.User;
import com.theusan.toodosimple.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    public Task findById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.orElseThrow(() ->
                new RuntimeException("Tarefa não encontrada com o ID: " +
                        id + ", Type: " + Task.class.getName()));
    }

    @Transactional
    public Task create(Task obj) {
        obj.setId(null);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj) {
        Task existingTask = findById(obj.getId());
        existingTask.setDescription(obj.getDescription());
        return taskRepository.save(existingTask);
    }


    @Transactional
    public void delete(Long id) {
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            // Rethrow the exception as a RuntimeException to trigger transaction rollback
            throw new RuntimeException("Failed to delete task", e);
        }
    }

}
