package br.com.cleonildo.todo.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_todo")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @Column
    @Size(min = 10, max = 255)
    @Getter
    @Setter
    private String description;

    @Column
    @NotNull
    @Getter
    @Setter
    private Boolean done;

    @Column
    @NotNull
    @Getter
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime created;

    @Column
    @Getter
    @Setter
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime doneDate;

    @PrePersist
    public void beforeSave() {
        created = LocalDateTime.now();
    }

}
