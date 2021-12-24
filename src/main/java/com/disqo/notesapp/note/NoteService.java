package com.disqo.notesapp.note;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoteService {
    private NoteRepository NoteRepository;
    NoteService(NoteRepository NoteRepository) {
        this.NoteRepository = NoteRepository;

    }
    public Note getNote(int id) {
        return NoteRepository.findById(id);
    }
    public List<Note> getAllNotes() {
        return NoteRepository.findAll();
    }
    public Note addNote(Note note) {
        return NoteRepository.save(note);
    }
}
