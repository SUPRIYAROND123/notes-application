package com.disqo.notesapp.note;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NoteController.class)
public class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    public static final String TEST_NOTE_CONTENT = "this is a test note content";
    public static final String TEST_NOTE_TITLE = "this is a test note title";

    @Test
    public void givenNote_whenGetNoteById() throws Exception {
        Note note = new Note();
        note.setNote(TEST_NOTE_CONTENT);
        note.setTitle(TEST_NOTE_TITLE);
        note.setUserId(1);
        note.setId(1);
        given(noteService.getNote(1,1)).willReturn(note);
        mockMvc.perform(get("/note")
                        .param("userId","1").param("noteId","1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(TEST_NOTE_TITLE))
                .andExpect(jsonPath("$.note").value(TEST_NOTE_CONTENT));
    }

    @Test
    public void postNote() throws Exception {
        Note note = new Note();
        note.setNote(TEST_NOTE_CONTENT);
        note.setTitle(TEST_NOTE_TITLE);
        note.setUserId(1);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(note);

        given(noteService.addNote(note)).willReturn(note);
        mockMvc.perform(post("/note")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNotes_whenGetNotes() throws Exception {
        Note note = new Note();
        note.setNote(TEST_NOTE_CONTENT);
        note.setTitle(TEST_NOTE_TITLE);
        note.setUserId(1);
        List<Note> notes = Arrays.asList(note);
        given(noteService.findAllNotes()).willReturn(notes);
        mockMvc.perform(get("/notes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value(TEST_NOTE_TITLE))
                .andExpect(jsonPath("$[0].note").value(TEST_NOTE_CONTENT));
    }
}
