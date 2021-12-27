package com.disqo.notesapp.note;

import com.disqo.notesapp.exception.EmptyNoteException;
import com.disqo.notesapp.exception.NoteAlreadyExistsException;
import com.disqo.notesapp.exception.NoteNotFoundException;
import com.disqo.notesapp.exception.UnauthorizedUserException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NoteService {
    private NoteRepository noteRepository;
    NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public static final Logger logger = Logger.getLogger(NoteService.class.getName());

    public Note getNote(int noteId, int userId) {
        Note note = noteRepository.findById(noteId);
        if(note != null) {
            if(note.getUserId() == userId) {
                return noteRepository.findById(noteId);
            } else {
                //401
                logger.error("action=getNote,msg= Trying to get a note which is not authorized for the user, noteId=" + note.getId() + ", userId=" + note.getUserId());
                throw new UnauthorizedUserException("Note is not authorized for user, userId=" + note.getUserId() + ", noteId=" + note.getId());
            }
        } else {
            logger.error("action=getNote,msg= Trying to get a note which doesn't exist");
            throw new NoteNotFoundException("Note with id=" + noteId + " is not found");
        }
    }

    public void deleteNote(Integer noteId, Integer userId) throws Exception {
        if(noteId==null || userId ==null) {
            throw new NoteNotFoundException("Null noteId or userId");
        }
        Note note = noteRepository.findById(noteId.intValue());
        if(note != null) {
            if(note.getUserId().equals(userId)) {
                noteRepository.deleteById(noteId);
            } else {
                //401
                logger.error("action=deleteNote,msg= Trying to delete a note which is not authorized for the user, noteId=" + note.getId() + ", userId=" + note.getUserId());
                throw new UnauthorizedUserException("Note is not authorized for user, userId=" + note.getUserId() + ", noteId=" + note.getId());
            }
        } else {
            logger.error("action=deleteNote,msg= Trying to delete a note which doesn't exist");
            throw new NoteNotFoundException("Note with id=" + noteId + " is not found");
        }
    }

    public Note updateNote(Note note) {
        if(note == null || note.getId()==null) {
            logger.error("action=updateNote,msg= Trying to update a note with null/empty note");
            throw new EmptyNoteException("Not a valid note");
        }
        Note existingNote = noteRepository.findById(note.getId().intValue());
        if(existingNote == null) {
            logger.error("action=updateNote,msg= Trying to update a note which doesn't exist");
            throw new NoteNotFoundException("Note with id=" + note.getId() + " is not found");
        }
        if(!existingNote.getUserId().equals(note.getUserId())) {
            logger.error("action=updateNote,msg= Trying to update a note which is note authorized for user, noteId=" + note.getId() + " and userId=" + note.getUserId());
            throw new UnauthorizedUserException("Note is not authorized for user, userId=" + note.getUserId() + ", noteId=" + note.getId());
        }
        existingNote.setNote(note.getNote());
        existingNote.setTitle(note.getTitle());
        existingNote.setLastUpdateTime(new Date());
        return noteRepository.save(existingNote);
    }

    public List<Note> findAllNotes() {
        return noteRepository.findAll();
    }

    public Note addNote(Note note) {
        if(note == null || note.getUserId()==null) {
            throw new EmptyNoteException("Not a valid note");
        }
        try {
            Note existingNote = getNote(note.getId(), note.getUserId());
            if(existingNote != null) {
                throw new NoteAlreadyExistsException("Note with noteId=" + note.getId() + " already exists");
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
