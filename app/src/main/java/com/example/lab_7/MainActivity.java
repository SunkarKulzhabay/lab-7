package com.example.lab_7;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText edInput;
    private TextView tvOutput;
    private static final int REQUEST_CODE_WRITE_PERM = 401;
    private static final String FALCON_TEXT = "Сокол - это хищная птица из семейства соколиных.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edInput = findViewById(R.id.ed_input);
        tvOutput = findViewById(R.id.tv_output);
        Button btnWrite = findViewById(R.id.btn_write);
        Button btnRead = findViewById(R.id.btn_read);

        requestNeededPermission();

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = edInput.getText().toString();
                if (!text.isEmpty()) {
                    writeFile(text);
                    Toast.makeText(MainActivity.this, "Text written to file", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOutput.setText(FALCON_TEXT);
            }
        });
    }

    private void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_WRITE_PERM);
        }
    }

    private void writeFile(String data) {
        File file = new File(Environment.getExternalStorageDirectory(), "mytext.txt");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data.getBytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error writing file", Toast.LENGTH_SHORT).show();
        }
    }
}