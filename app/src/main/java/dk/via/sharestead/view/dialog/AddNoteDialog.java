package dk.via.sharestead.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import dk.via.sharestead.R;
import dk.via.sharestead.model.Note;

public class AddNoteDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new
                AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_note_dialog, null);
        builder.setView(view);

        Button addNoteBtn = view.findViewById(R.id.addNoteBtn);
        EditText title = view.findViewById(R.id.noteTitle);
        EditText priority = view.findViewById(R.id.notePriority);
        EditText description = view.findViewById(R.id.noteDescription);
        ImageButton backArrow = view.findViewById(R.id.backArrow);
        CheckBox favourite = view.findViewById(R.id.favouriteCheckBox);

        backArrow.setOnClickListener(view1 -> getDialog().dismiss());

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleText = title.getText().toString().trim();
                String descriptionText = description.getText().toString().trim();
                String priorityText = priority.getText().toString();
                int priorityNum = 0;
                if(priorityText.length() > 0)
                {
                    priorityNum = Integer.parseInt(priority.getText().toString());
                }
                boolean favouriteBoolean = favourite.isChecked();

                Note note = new Note(titleText, descriptionText, priorityNum, favouriteBoolean);
                Intent intent = new Intent();
                intent.putExtra("note_details", note);
                getTargetFragment().onActivityResult(
                        getTargetRequestCode(), Activity.RESULT_OK, intent);
                getDialog().dismiss();
            }
        });

        return builder.create();
    }
}
