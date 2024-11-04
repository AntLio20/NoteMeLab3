package com.example.notemelab3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.graphics.Color;


public class EditNoteActivity extends AppCompatActivity {

    private String selectedColor = "#A7BED3";
    private Button lastSelectedButton;
    private Button color1, color2, color3, color4, color5;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_note);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int id = bundle.getInt("ID", 0);
        String title = bundle.getString("TITLE", "TITLE MISSING");
        String subtitle = bundle.getString("SUBTITLE", "SUBTITLE MISSING");
        String text = bundle.getString("TEXT", "TEXT MISSING");
        String color = bundle.getString("COLOR", "#A7BED3");

        EditText editTitle = (EditText)findViewById(R.id.title);
        EditText editSubtitle = (EditText)findViewById(R.id.subtitle);
        EditText editText = (EditText)findViewById(R.id.description);
        editTitle.setText(title);
        editSubtitle.setText(subtitle);
        editText.setText(text);
        Button selectedColor = null;

        dbHandler = new DBHandler(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveNote(id));

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> finish());

        color1 = findViewById(R.id.color1);
        color1.setOnClickListener(v -> changeNoteColor("#A7BED3", color1));

        color2 = findViewById(R.id.color2);
        color2.setOnClickListener(v -> changeNoteColor("#C6E2E9", color2));

        color3 = findViewById(R.id.color3);
        color3.setOnClickListener(v -> changeNoteColor("#F1FFC4", color3));

        color4 = findViewById(R.id.color4);
        color4.setOnClickListener(v -> changeNoteColor("#FFCAAF", color4));

        color5 = findViewById(R.id.color5);
        color5.setOnClickListener(v -> changeNoteColor("#DAB894", color5));

        switch (color) {
            case "#A7BED3": selectedColor = color1; break;
            case "#C6E2E9": selectedColor = color2; break;
            case "#F1FFC4": selectedColor = color3; break;
            case "#FFCAAF": selectedColor = color4; break;
            case "#DAB894": selectedColor = color5; break;
            default: selectedColor = color1;
        }
        changeNoteColor(color, selectedColor);
    }

    private void changeNoteColor(String color, Button selectedButton) {
        selectedColor = color;
        View colorLayout = findViewById(R.id.noteLayout);
        colorLayout.setBackgroundColor(Color.parseColor(selectedColor));
        if (lastSelectedButton != null) {
            lastSelectedButton.setScaleX(1f);
            lastSelectedButton.setScaleY(1f);
        }
        selectedButton.setScaleX(0.7f);
        selectedButton.setScaleY(0.7f);
        lastSelectedButton = selectedButton;
    }

    private void saveNote(int id) {
        EditText noteTitle = findViewById(R.id.title);
        EditText noteSubtitle = findViewById(R.id.subtitle);
        EditText noteDescription = findViewById(R.id.description);

        String title = noteTitle.getText().toString();
        String subtitle = noteSubtitle.getText().toString();
        String description = noteDescription.getText().toString();

        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (subtitle.isEmpty()) {
            Toast.makeText(this, "Please enter a subtitle", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (description.isEmpty()) {
            Toast.makeText(this, "Please enter note content", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHandler.updateNote(id, title, subtitle, description, selectedColor);
        if (result != -1) {
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();

            finish();
        } else {
            Toast.makeText(this, "Error saving note", Toast.LENGTH_SHORT).show();
        }
    }
}