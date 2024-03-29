package com.hope.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Question extends AbstractEntity {

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
  @JsonProperty
  private User writer;

  @JsonProperty
  private String title;

  @Lob
  @JsonProperty
  private String contents;

  @OneToMany(mappedBy = "question")
  @OrderBy("id DESC")
  private List<Answer> answers;

  @JsonProperty
  private Integer countOfAnswer = 0;

  public Question() {}

  public Question(User writer, String title, String contents) {
    this.writer = writer;
    this.title = title;
    this.contents = contents;
  }

  public void update(String title, String contents) {
    this.title = title;
    this.contents = contents;
  }

  public boolean isSameWriter(User loginUser) {
    return this.writer.equals(loginUser);
  }

  public void addAnswer() {
    this.countOfAnswer++;
  }

  public void deleteAnswer() {
    this.countOfAnswer--;
  }
}
