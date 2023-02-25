package br.com.cleonildo.todo.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequest {
    private String description;
    private Boolean done;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime created;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime doneDate;
}
