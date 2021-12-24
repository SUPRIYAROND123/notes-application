package com.disqo.notesapp.note;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, Integer> {

    Note findById(int id);

    List<Note> findAll();

}
