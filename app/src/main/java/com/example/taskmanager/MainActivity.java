package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TaskDatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private TaskListAdapter taskListAdapter;
    private Button addTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TaskDatabaseHelper(this);
        recyclerView = findViewById(R.id.recycler_view);
        addTaskButton = findViewById(R.id.add_task_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadTasks();

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadTasks() {
        List<Task> tasks = dbHelper.getAllTasks();
        taskListAdapter = new TaskListAdapter(this, tasks, new TaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                intent.putExtra("TASK_ID", task.getId()); // Pass the task ID to AddEditTaskActivity
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(taskListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }
}
