package com.mirea.lutchenkoam.notebook;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private EditText editTextFileName;
    private EditText editTextQuote;

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

        editTextFileName = findViewById(R.id.editTextFileName);
        editTextQuote = findViewById(R.id.editTextQuote);

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(v -> writeFileToExternalStorage());

        Button buttonLoad = findViewById(R.id.buttonLoad);
        buttonLoad.setOnClickListener(v -> readFileFromExternalStorage());
    }

    private void writeFileToExternalStorage() {
        String fileName = editTextFileName.getText().toString().trim();
        String quote = editTextQuote.getText().toString().trim();

        if (!fileName.isEmpty() && !quote.isEmpty()) {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(path, fileName + ".txt");

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(quote.getBytes());
                fileOutputStream.close();
                Log.d("MainActivity", "File saved successfully");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("MainActivity", "Error saving file: " + e.getMessage());
            }
        } else {
            Log.e("MainActivity", "File name or quote is empty");
        }
    }

    private void readFileFromExternalStorage() {
        String fileName = editTextFileName.getText().toString().trim();

        if (!fileName.isEmpty()) {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(path, fileName + ".txt");

            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                fileInputStream.close();
                editTextQuote.setText(stringBuilder.toString().trim());
                Log.d("MainActivity", "File loaded successfully");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("MainActivity", "Error loading file: " + e.getMessage());
            }
        } else {
            Log.e("MainActivity", "File name is empty");
        }
    }
}