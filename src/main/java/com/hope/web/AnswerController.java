package com.hope.web;

import com.hope.domain.Answer;
import com.hope.domain.AnswerRepository;
import com.hope.domain.Question;
import com.hope.domain.QuestionRepository;
import com.hope.domain.User;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

  @Autowired
  private AnswerRepository answerRepository;

  @Autowired
  private QuestionRepository questionRepository;

  @PostMapping("")
  public String create(@PathVariable Long questionId, String contents, HttpSession session) {
    if (!HttpSessionUtils.isLoginUser(session)) {
      return "/users/loginForm";
    }

    User loginUser = HttpSessionUtils.getUserFromSession(session);
    Question question = questionRepository.findOne(questionId);
    Answer answer = new Answer(loginUser, question, contents);
    answerRepository.save(answer);

    return String.format("redirect:/questions/%d", questionId);
  }
}
