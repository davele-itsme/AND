package dk.via.sharestead.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dk.via.sharestead.R;
import dk.via.sharestead.adapter.NoteAdapter;
import dk.via.sharestead.model.Note;
import dk.via.sharestead.view.dialog.AddNoteDialog;
import dk.via.sharestead.view.dialog.UpdateNoteDialog;
import dk.via.sharestead.viewmodel.NoteViewModel;

public class NoteFragment extends Fragment implements NoteAdapter.OnListItemClickListener, NoteAdapter.OnListItemLongClickListener {
    private static final String TAG_ADD = "Add note dialog";
    private static final String TAG_UPDATE = "Update note dialog";
    private static final int REQUEST_CODE = 100;
    private static final int REQUEST_CODE_UPDATE = 101;
    private NoteViewModel noteViewModel;
    private StaggeredGridLayoutManager manager;
    private NoteAdapter noteAdapter;

    public NoteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        return inflater.inflate(R.layout.note_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRecyclerView(view);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(getViewLifecycleOwner(), notes -> noteAdapter.setNotes(notes));

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> showAddNoteDialog());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra("note_details_add")) {
                Note note = (Note) data.getSerializableExtra("note_details_add");
                noteViewModel.insert(note);
            }
        } else if (requestCode == REQUEST_CODE_UPDATE && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra("note_details_update")) {
                Note note = (Note) data.getSerializableExtra("note_details_update");
                noteViewModel.update(note);

            }
        }
    }

    @Override
    public void onListItemClick(Note note) {
        showUpdateNoteDialog(note);
    }

    @Override
    public void onListItemLongClick(Note note) {
        showDeleteDialog(note);
    }

    private void setRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.noteRecyclerView);
        recyclerView.setLayoutManager(manager);
        noteAdapter = new NoteAdapter(getContext(), this, this);
        recyclerView.setAdapter(noteAdapter);
    }

    private void showAddNoteDialog() {
        AddNoteDialog addNoteDialog = new AddNoteDialog();
        addNoteDialog.setTargetFragment(this, REQUEST_CODE);
        addNoteDialog.show(getActivity().getSupportFragmentManager(), TAG_ADD);
    }

    private void showDeleteDialog(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getResources().getString(R.string.delete_note_title));
        builder.setMessage(getResources().getString(R.string.delete_note_message));

        builder.setPositiveButton(getResources().getString(R.string.okay), (dialogInterface, i) -> {
            noteViewModel.delete(note);
            Toast.makeText(getContext(), getResources().getString(R.string.note_deleted), Toast.LENGTH_SHORT).show();
        });
        builder.setNeutralButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showUpdateNoteDialog(Note note) {
        Bundle args = new Bundle();
        args.putSerializable("note_set_text", note);
        UpdateNoteDialog updateNoteDialog = new UpdateNoteDialog();
        updateNoteDialog.setTargetFragment(this, REQUEST_CODE_UPDATE);
        updateNoteDialog.setArguments(args);
        updateNoteDialog.show(getActivity().getSupportFragmentManager(), TAG_UPDATE);
    }


}
