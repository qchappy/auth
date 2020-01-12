package com.ynu.sei.auth.repositories;

import com.ynu.sei.auth.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {
}
