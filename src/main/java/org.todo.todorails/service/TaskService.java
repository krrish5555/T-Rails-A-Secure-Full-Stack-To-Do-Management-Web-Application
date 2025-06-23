package org.todo.todorails.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.todo.todorails.model.Task;
import org.todo.todorails.model.User;
import org.todo.todorails.repository.TaskRepository;
import org.todo.todorails.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    /* -------------------------------------------------
       Save a new task for the currently-authenticated user
       ------------------------------------------------- */
    public Task saveTask(Task task) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username     = auth.getName();
        User user           = userRepository.findByUsername(username);

        task.setUser(user);
        task.setDateAdded(LocalDate.now());

        return taskRepository.save(task);
    }

    /* -------------------------------------------------
       Get today's **pending** tasks for current user
       ------------------------------------------------- */
    public List<Task> getTodayTasksForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username     = auth.getName();
        User user           = userRepository.findByUsername(username);

        LocalDate today = LocalDate.now();
        return taskRepository.findByUserAndDueDateAndCompleted(user, today, false);
    }

    /* -------------------------------------------------
       Overload for tests (allows a user to be supplied)
       ------------------------------------------------- */
    public List<Task> getTodayTasksForCurrentUser(User user) {
        LocalDate today = LocalDate.now();
        return taskRepository.findByUserAndDueDateAndCompleted(user, today, false);
    }

    /* -------------------------------------------------
       Get **all** tasks for current user
       ------------------------------------------------- */
    public List<Task> getAllTasksForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username     = auth.getName();
        User user           = userRepository.findByUsername(username);

        return taskRepository.findByUser(user);
    }

    /* -------------------------------------------------
       Mark task as done
       ------------------------------------------------- */
    public boolean markTaskAsDone(Long taskId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username     = auth.getName();
        User user           = userRepository.findByUsername(username);

        Task task = taskRepository.findByUserAndId(user, taskId);
        if (task != null && !task.isCompleted()) {
            task.setCompleted(true);               // ✅ mark as done
            task.setCompletionDate(LocalDate.now());
            taskRepository.save(task);
            return true;
        }
        return false; // task not found / not owned / already completed
    }

    /* -------------------------------------------------
       Get a task (only if NOT completed) for current user
       ------------------------------------------------- */
    public Task getTaskById(Long taskId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username     = auth.getName();
        User user           = userRepository.findByUsername(username);

        Task task = taskRepository.findByUserAndId(user, taskId);
        return (task != null && !task.isCompleted()) ? task : null;
    }

    /* -------------------------------------------------
       Get a task (ignores completed flag) for current user
       ------------------------------------------------- */
    public Task getTaskByIdAny(Long taskId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username     = auth.getName();
        User user           = userRepository.findByUsername(username);

        return taskRepository.findByUserAndId(user, taskId);
    }

    /* -------------------------------------------------
       Update existing task (only owner can update)
       ------------------------------------------------- */
    public boolean updateTaskForUser(Task task) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username     = auth.getName();
        User user           = userRepository.findByUsername(username);

        Task taskInDb = taskRepository.getById(task.getId());
        if (user == null || !user.getUsername().equals(taskInDb.getUser().getUsername())) {
            return false;
        }

        taskInDb.setTitle(task.getTitle());
        taskInDb.setDescription(task.getDescription());
        taskInDb.setPriority(task.getPriority());
        taskInDb.setDueDate(task.getDueDate());
        taskInDb.setType(task.getType());
        taskInDb.setDateAdded(LocalDate.now());

        return taskRepository.save(taskInDb) != null;
    }

    /* -------------------------------------------------
       Delete existing task (only owner can delete)
       ------------------------------------------------- */
    public boolean deleteTask(Task task) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username     = auth.getName();
        User user           = userRepository.findByUsername(username);

        Task taskInDb = taskRepository.getById(task.getId());
        if (user == null || !user.getUsername().equals(taskInDb.getUser().getUsername())) {
            return false;
        }

        taskRepository.delete(taskInDb);
        return true;
    }

    /* -------------------------------------------------
       Count tasks by completion status
       ------------------------------------------------- */
    public int countByCompleted(boolean completedStatus) {
        return taskRepository.countByCompleted(completedStatus);
    }
}
