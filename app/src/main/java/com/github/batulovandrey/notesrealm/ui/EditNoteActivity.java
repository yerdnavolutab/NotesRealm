package com.github.batulovandrey.notesrealm.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.github.batulovandrey.notesrealm.R;
import com.github.batulovandrey.notesrealm.bean.Note;

import io.realm.Realm;

/**
 * Activity to edit note {@link Note}
 *
 * @author Andrey Batulov on 26/07/2017
 */
public class EditNoteActivity extends BaseNoteActivity {

    public static Intent createExplicitIntent(Context context,
                                              String noteTitle,
                                              String noteId,
                                              String noteBody) {
        Intent intent = new Intent(context, EditNoteActivity.class);
        intent.putExtra(EXTRA_NOTE_TITLE, noteTitle);
        intent.putExtra(EXTRA_NOTE_ID, noteId);
        intent.putExtra(EXTRA_NOTE_BODY, noteBody);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fillUIData();
    }

    @Override
    protected void handleActivityClosing() {
        if (dataWasNotChanged()) {
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.close_activity)
                    .setMessage(R.string.save_changes)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (inputDataCorrect()) {
                                editNote();
                                finishActivityWithResultOk();
                            } else {
                                showErrorMessage();
                            }
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }
    }

    @Override
    protected void onMainButtonClick(View view) {
        if (dataWasNotChanged()) {
            finish();
        } else if (inputDataCorrect()) {
            showSnackBar(view);
        } else {
            showErrorMessage();
        }
    }

    protected void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        mNoteTitle = bundle.getString(EXTRA_NOTE_TITLE);
        mNoteId = bundle.getString(EXTRA_NOTE_ID);
        mNoteBody = bundle.getString(EXTRA_NOTE_BODY);
    }

    // region private methods

    private boolean dataWasNotChanged() {
        return mNoteTitle.equals(mTitleEditText.getText().toString()) &&
                mNoteBody.equals(mBodyEditText.getText().toString());
    }

    private void fillUIData() {
        mTitleEditText.setText(mNoteTitle);
        mBodyEditText.setText(mNoteBody);
        mNoteIdTextView.setText(mNoteId);
    }

    private void showSnackBar(View view) {
        Snackbar.make(view, R.string.save_note_question, Snackbar.LENGTH_LONG)
                .setAction(R.string.yes, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editNote();
                        finishActivityWithResultOk();
                    }
                }).show();
    }

    private boolean inputDataCorrect() {
        return mTitleEditText.getText().length() >= INPUT_MIN_LENGTH &&
                mBodyEditText.getText().length() >= INPUT_MIN_LENGTH;
    }

    private void showErrorMessage() {
        Toast.makeText(getApplicationContext(), "Поля должны быть минимум " + INPUT_MIN_LENGTH + " символа", Toast.LENGTH_LONG).show();
    }

    private void editNote() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = mRealm.where(Note.class)
                        .equalTo("id", mNoteId)
                        .findFirst();
                note.setTitle(mTitleEditText.getText().toString());
                note.setBody(mBodyEditText.getText().toString());
            }
        });
    }

    private void finishActivityWithResultOk() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NOTE_TITLE, mTitleEditText.getText().toString());
        intent.putExtra(EXTRA_NOTE_BODY, mBodyEditText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    // endregion private methods
}