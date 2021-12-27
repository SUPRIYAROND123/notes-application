package com.disqo.notesapp.note;

import javassist.NotFoundException;
import org.apache.log4j.Logger;
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

    public static final Logger logger = Logger.getLogger(NoteService.class.getName());

    public Note getNote(int noteId, int userId) throws Exception {
        Note note = noteRepository.findById(noteId);
        if(note != null) {
            if(note.getUserId() == userId) {
                return noteRepository.findById(noteId);
            } else {
                //401
                logger.error("action=getNote,msg= Trying to get a note which is not authorized for the user, noteId=" + note.getId() + ", userId=" + note.getUserId());
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
        } else {
            logger.error("action=getNote,msg= Trying to get a note which doesn't exist");
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
                logger.error("action=deleteNote,msg= Trying to delete a note which is not authorized for the user, noteId=" + note.getId() + ", userId=" + note.getUserId());
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
        } else {
            logger.error("action=deleteNote,msg= Trying to delete a note which doesn't exist");
            throw new NotFoundException("Note with id=" + noteId + " is not found");
        }
    }

    public Note updateNote(Note note) {
        if(note == null) {
            logger.error("action=updateNote,msg= Trying to update a note with null/empty note");
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Note existingNote = noteRepository.findById(note.getId());
        if(existingNote == null) {
            logger.error("action=updateNote,msg= Trying to update a note which doesn't exist");
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        if(existingNote.getUserId() != note.getUserId()) {
            logger.error("action=updateNote,msg= Trying to update a note which is note authorized for user, noteId=" + note.getId() + " and userId=" + note.getUserId());
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
            logger.info("action=addNote,msg=Note with id="+note.getId() + " doesn't exist, creating a new note!");
            //note doesn't exist;
        }
        note.setCreateTime(new Date());
        note.setLastUpdateTime(new Date());
        logger.info("action=addNote,msg=New note is being added for user with userId=" + note.getUserId());
        return noteRepository.save(note);
    }
}
