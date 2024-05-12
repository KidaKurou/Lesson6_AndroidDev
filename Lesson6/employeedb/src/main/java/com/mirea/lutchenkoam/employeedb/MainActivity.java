package com.mirea.lutchenkoam.employeedb;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AppDatabase db = App.getInstance().getDatabase();
        EmployeeDao employeeDao = db.employeeDao();

        // Создание нового сотрудника
        Employee employee = new Employee();
        employee.name = "Patrik Bateman";
        employee.salary = 100000;
        // Запись сотрудника в базу
        employeeDao.insert(employee);
        // Получение и вывод всех работников
        List<Employee> employees = employeeDao.getAll();
        for (Employee employee1 : employees) {
            Log.d(TAG, "Employee Name: " + employee1.name + ", Salary: " + employee1.salary);
        }
        TextView employeeInfoTextView = findViewById(R.id.employeeInfoTextView);
        String info = String.format("Employee: %s, Salary: %d", employee.name, employee.salary);
        employeeInfoTextView.setText(info);
        // Обновление полей объекта
        employee.salary = 200000;
        employeeDao.update(employee);
        Log.d(TAG, employee.name + " " + employee.salary);
    }
}