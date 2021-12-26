package com.disqo.notesapp.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/note")
    public Note getNote(@RequestParam int noteId, @RequestParam int userId) throws Exception {
        return noteService.getNote(noteId, userId);
    }

    @PostMapping("/note")
    public Note addNote(@RequestBody Note note) {
        return noteService.addNote(note);
    }

    @PutMapping("/note")
    public Note updateNote(@RequestBody Note note) {
        return noteService.updateNote(note);
    }

    @DeleteMapping("/note")
    public String deleteNote(@RequestParam int noteId, @RequestParam int userId) throws Exception {
        noteService.deleteNote(noteId, userId);
        return "Note deleted successfully!";
    }
}
