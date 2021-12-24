package com.disqo.notesapp.note;

import org.springframework.web.bind.annotation.*;

@RestController
public class NoteController {

    private NoteService noteService;

    @GetMapping("/note")
    public String getNoteById(@RequestParam int userId, @RequestParam int noteId) {
        return "Welcome to notes application!";
    }

    @PostMapping("/note")
    public Note addNote(@RequestParam int userId, @RequestParam int noteId, @RequestBody Note note) {
        return noteService.addNote(note);
    }
}
