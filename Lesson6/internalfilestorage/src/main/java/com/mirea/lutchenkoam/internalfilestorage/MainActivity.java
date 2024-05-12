package com.mirea.lutchenkoam.internalfilestorage;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "mirea.txt";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

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

        EditText editText = findViewById(R.id.editText);
        Button buttonSave = findViewById(R.id.buttonSave);
        TextView textView = findViewById(R.id.textView);

        buttonSave.setOnClickListener(view -> {
            String data = editText.getText().toString();
            if (!data.isEmpty()) {
                if (isExternalStorageWritable()) {
                    writeFileToExternalStorage(data);
                    saveToInternalStorage(data);
                    editText.getText().clear();
                    textView.setText(getTextFromFile());
                } else {
                    Toast.makeText(MainActivity.this, "Хранилище недоступно", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Введите данные", Toast.LENGTH_SHORT).show();
            }
        });

        if (isExternalStorageReadable()) {
            textView.setText(getTextFromFile());
        } else {
            Toast.makeText(MainActivity.this, "Хранилище недоступно", Toast.LENGTH_SHORT).show();
        }
    }

    public String getTextFromFile() {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, FILE_NAME);
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public void writeFileToExternalStorage(String data) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, FILE_NAME);

        try {
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write((data + "\n").getBytes());
            fos.close();
            Toast.makeText(this, "Данные успешно сохранены", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.w("ExternalStorage", "Error writing " + file, e);
            Toast.makeText(this, "Ошибка при сохранении данных", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToInternalStorage(String data) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, Context.MODE_APPEND);
            fos.write((data + "\n").getBytes());
            fos.close();
            Toast.makeText(this, "Данные успешно сохранены", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.w("InternalStorage", "Ошибка при записи " + FILE_NAME, e);
            Toast.makeText(this, "Ошибка при сохранении данных", Toast.LENGTH_SHORT).show();
        }
    }

}
