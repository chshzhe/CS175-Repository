package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.byted.camp.todolist.beans.Priority;
import com.byted.camp.todolist.beans.State;
import com.byted.camp.todolist.db.TodoContract.TodoNote;
import com.byted.camp.todolist.db.TodoDbHelper;


public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private Button addBtn;
    private RadioGroup radioGroup;
    private AppCompatRadioButton lowRadio;

    private TodoDbHelper dbHelper;
    private SQLiteDatabase database;

    private NoteOperator operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);

        dbHelper = new TodoDbHelper(this);
        database = dbHelper.getWritableDatabase();

        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }
        radioGroup = findViewById(R.id.radio_group);
        lowRadio = findViewById(R.id.btn_low);
        lowRadio.setChecked(true);


        final boolean isUpdateActivity = getIntent().getBooleanExtra("isUpdateActivity", false);
        final long note_id = getIntent().getLongExtra("note_id", -1);
        String originContent = getIntent().getStringExtra("content");
        Priority originPriority = Priority.from(getIntent().getIntExtra("priority", 0));
        editText.setText(originContent);
        radioGroup.clearCheck();
        int radio_id;
        if (originPriority == Priority.Low) {
            radio_id = R.id.btn_low;
        } else if (originPriority == Priority.Medium) {
            radio_id = R.id.btn_medium;
        } else radio_id = R.id.btn_high;
        RadioButton radioButton = findViewById(radio_id);
        radioButton.setChecked(true);

        addBtn = findViewById(R.id.btn_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isUpdateActivity) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("shouldUpdate", true);
                    intent.putExtra("note_id", note_id);
                    intent.putExtra("content", content.toString());
                    intent.putExtra("priority", getSelectedPriority().intValue);
                    startActivity(intent);

                } else {
                    boolean succeed = saveNote2Database(content.toString().trim(),
                            getSelectedPriority());
                    if (succeed) {
                        Toast.makeText(NoteActivity.this,
                                "Note added", Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_OK);
                    } else {
                        Toast.makeText(NoteActivity.this,
                                "Error", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
        database = null;
        dbHelper.close();
        dbHelper = null;
    }

    private boolean saveNote2Database(String content, Priority priority) {
        if (database == null || TextUtils.isEmpty(content)) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(TodoNote.COLUMN_CONTENT, content);
        values.put(TodoNote.COLUMN_STATE, State.TODO.intValue);
        values.put(TodoNote.COLUMN_DATE, System.currentTimeMillis());
        values.put(TodoNote.COLUMN_PRIORITY, priority.intValue);
        long rowId = database.insert(TodoNote.TABLE_NAME, null, values);
        return rowId != -1;
    }

    private Priority getSelectedPriority() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.btn_high:
                return Priority.High;
            case R.id.btn_medium:
                return Priority.Medium;
            default:
                return Priority.Low;
        }
    }
}
