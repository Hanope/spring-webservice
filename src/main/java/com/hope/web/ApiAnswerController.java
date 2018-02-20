package com.hope.web;

import com.hope.domain.Answer;
import com.hope.domain.AnswerRepository;
import com.hope.domain.Question;
import com.hope.domain.QuestionRepository;
import com.hope.domain.Result;
import com.hope.domain.User;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

  @Autowired
  private AnswerRepository answerRepository;

  @Autowired
  private QuestionRepository questionRepository;

  @PostMapping("")
  public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
    if (!HttpSessionUtils.isLoginUser(session)) {
      return null;
    }

    User loginUser = HttpSessionUtils.getUserFromSession(session);
    Question question = questionRepository.findOne(questionId);
    Answer answer = new Answer(loginUser, question, contents);
    question.addAnswer();
    return answerRepository.save(answer);
  }

  @DeleteMapping("/{id}")
  public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
    if (!HttpSessionUtils.isLoginUser(session)) {
      return Result.fail("로그인해야 합니다.");
    }

    Answer answer = answerRepository.findOne(id);
    User loginUser = HttpSessionUtils.getUserFromSession(session);
    if (!answer.isSameWriter(loginUser)) {
      return Result.fail("자신의 글만 삭제할 수 있습니다.");
    }

    answerRepository.delete(id);

    Question question = questionRepository.findOne(questionId);
    question.deleteAnswer();
    questionRepository.save(question);
    return Result.ok();
  }
}
