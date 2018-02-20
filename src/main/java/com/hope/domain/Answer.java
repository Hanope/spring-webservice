package com.hope.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Answer {

  @Id
  @GeneratedValue
  @JsonProperty
  private Long id;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
  @JsonProperty
  private User writer;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
  @JsonProperty
  private Question question;

  @Lob
  @JsonProperty
  private String contents;

  private LocalDateTime createDate;

  public Answer() {}

  public Answer(User writer, Question question, String contents) {
    this.writer = writer;
    this.question = question;
    this.contents = contents;
    this.createDate = LocalDateTime.now();
  }

  public String getFormattedCreateDate() {
    if (createDate == null) {
      return "";
    }
    return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Answer answer = (Answer) o;
    return Objects.equals(id, answer.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Answer{" +
        "id=" + id +
        ", writer=" + writer +
        ", contents='" + contents + '\'' +
        ", createDate=" + createDate +
        '}';
  }

  public boolean isSameWriter(User loginUser) {
    return loginUser.equals(this.writer);
  }
}
