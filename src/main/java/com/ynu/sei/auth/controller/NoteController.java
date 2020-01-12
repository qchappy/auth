package com.ynu.sei.auth.controller;

import com.ynu.sei.auth.domain.Note;
import com.ynu.sei.auth.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @RequestMapping(method = RequestMethod.POST)
    public String addNote(@RequestBody Note[] notes) {
        noteRepository.deleteAll();

        for (int i = 0; i < notes.length; i++) {
            noteRepository.save(notes[i]);
        }
        return "Notes saved!";
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Note> getAllNote() {
        return noteRepository.findAll();
    }
}
