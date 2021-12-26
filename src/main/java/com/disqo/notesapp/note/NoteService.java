package com.disqo.notesapp.note;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Date;

@Service
public class NoteService {
    private NoteRepository noteRepository;
    NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note getNote(int noteId, int userId) throws Exception {
        Note note = noteRepository.findById(noteId);
        if(note != null) {
            if(note.getUserId() == userId) {
                return noteRepository.findById(noteId);
            } else {
                //401
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
        } else {
            throw new NotFoundException("Note with id=" + noteId + " is not found");
        }
    }

    public void deleteNote(int noteId, int userId) throws Exception {
        Note note = noteRepository.findById(noteId);
        if(note != null) {
            if(note.getUserId() == userId) {
                noteRepository.deleteById(noteId);
            } else {
                //401
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
        } else {
            throw new NotFoundException("Note with id=" + noteId + " is not found");
        }
    }

    public Note updateNote(Note note) {
        if(note == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Note existingNote = noteRepository.findById(note.getId());
        if(existingNote == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        if(existingNote.getUserId() != note.getUserId()) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        existingNote.setNote(note.getNote());
        existingNote.setTitle(note.getTitle());
        existingNote.setLastUpdateTime(new Date());
        return noteRepository.save(existingNote);
    }

    public Note addNote(Note note) {
        if(note == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        try {
            Note existingNote = getNote(note.getId(), note.getUserId());
            if(existingNote != null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println("replace with logger");
            //note doesn't exist;
        }
        note.setCreateTime(new Date());
        note.setLastUpdateTime(new Date());
        return noteRepository.save(note);
    }
}
