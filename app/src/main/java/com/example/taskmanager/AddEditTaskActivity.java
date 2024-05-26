package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditTaskActivity extends AppCompatActivity {

    private TaskDatabaseHelper dbHelper;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText dueDateEditText;
    private Button saveButton;

    private int taskId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        dbHelper = new TaskDatabaseHelper(this);
        titleEditText = findViewById(R.id.title_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);
        dueDateEditText = findViewById(R.id.due_date_edit_text);
        saveButton = findViewById(R.id.save_button);

        // Check if a task ID is passed from MainActivity
        if (getIntent().hasExtra("TASK_ID")) {
            taskId = getIntent().getIntExtra("TASK_ID", -1);
            Task task = dbHelper.getTask(taskId);
            if (task != null) {
                titleEditText.setText(task.getTitle());
                descriptionEditText.setText(task.getDescription());
                dueDateEditText.setText(task.getDueDate());
            }
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();
                String dueDate = dueDateEditText.getText().toString().trim();

                if (title.isEmpty() || dueDate.isEmpty()) {
                    // Show error message if title or due date is empty
                    // You can handle this based on your requirements
                    return;
                }

                if (taskId == -1) {
                    // If taskId is -1, it means it's a new task, so add it
                    dbHelper.addTask(new Task(0, title, description, dueDate));
                } else {
                    // If taskId is not -1, it means it's an existing task, so update it
                    dbHelper.updateTask(new Task(taskId, title, description, dueDate));
                }

                finish();
            }
        });
    }
}
