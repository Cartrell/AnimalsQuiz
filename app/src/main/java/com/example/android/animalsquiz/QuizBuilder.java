package com.example.android.animalsquiz;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.animalsquiz.questionbuilder.CheckQuestionBuilder;
import com.example.android.animalsquiz.questionbuilder.EditQuestionBuilder;
import com.example.android.animalsquiz.questionbuilder.QuestionBuilder;
import com.example.android.animalsquiz.questionbuilder.RadioQuestionBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class QuizBuilder {
  //===============================================================
  // members
  //===============================================================
  private QuestionBuilder[] m_questionBuilders;

  //===============================================================
  // package
  //===============================================================

  //---------------------------------------------------------------
  // build
  //---------------------------------------------------------------
  void build(ViewGroup questionsGroup, ArrayList<QuestionData> questionsData,
             String questionNumberFormat, IQuizChoiceCallbacks callbacks) {

    buildQuestionViews(questionsGroup, questionsData, callbacks);
    numberQuestions(questionsGroup, questionNumberFormat);
  }

  //---------------------------------------------------------------
  // setUserChoices
  //---------------------------------------------------------------
  void setUserChoices(UserChoicesData userChoicesData) {
    if (userChoicesData == null) {
      return; //sanity check
    }

    HashMap<String, Object> hashMap = userChoicesData.getData();
    for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
      String questionId = entry.getKey();
      Object userChoice = entry.getValue();
      QuestionBuilder questionBuilder = getQuestionBuilderById(questionId);

      if (questionBuilder != null) {
        questionBuilder.setUserChoice(userChoice);
      }
    }
  }

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // buildQuestionViews
  //---------------------------------------------------------------
  private void buildQuestionViews(ViewGroup questionsGroup, ArrayList<QuestionData> questionsData,
                                  IQuizChoiceCallbacks callbacks) {
    m_questionBuilders = new QuestionBuilder[questionsData.size()];

    Context context = questionsGroup.getContext();
    int lastQuestionIndex = questionsData.size() - 1;
    for (int questionIndex = 0; questionIndex <= lastQuestionIndex; questionIndex++) {
      QuestionData questionData = questionsData.get(questionIndex);

      ViewGroup questionView;
      boolean isLastQuestion = questionIndex == lastQuestionIndex;
      switch (questionData.getQuestionType()) {
        case QuestionData.CHOICE_TYPE_RADIO:
          RadioQuestionBuilder radioQuestionBuilder = new RadioQuestionBuilder();
          questionView = radioQuestionBuilder.build(context, questionData, isLastQuestion, callbacks);
          m_questionBuilders[questionIndex] = radioQuestionBuilder;
          break;

        case QuestionData.CHOICE_TYPE_CHECK:
          CheckQuestionBuilder checkQuestionBuilder = new CheckQuestionBuilder();
          questionView = checkQuestionBuilder.build(context, questionData, isLastQuestion, callbacks);
          m_questionBuilders[questionIndex] = checkQuestionBuilder;
          break;

        case QuestionData.CHOICE_TYPE_EDIT:
          EditQuestionBuilder editQuestionBuilder = new EditQuestionBuilder();
          questionView = editQuestionBuilder.build(context, questionData, isLastQuestion, callbacks);
          m_questionBuilders[questionIndex] = editQuestionBuilder;
          break;

        default:
          questionView = null;
          break;
      }

      if (questionView != null) {
        questionsGroup.addView(questionView);
      }
    }
  }

  //---------------------------------------------------------------
  // getQuestionBuilderById
  //---------------------------------------------------------------
  private QuestionBuilder getQuestionBuilderById(String questionId) {
    for (QuestionBuilder questionBuilder : m_questionBuilders) {
      String questionBuilderQId = questionBuilder.getQuestionId();
      if (questionBuilderQId == null) {
        continue;
      }

      if (questionBuilderQId.equals(questionId)) {
        return(questionBuilder);
      }
    }

    return(null);
  }

  //---------------------------------------------------------------
  // numberQuestions
  //---------------------------------------------------------------
  private void numberQuestions(ViewGroup questionsGroup, String questionNumberFormat) {
    int numQuestions = questionsGroup.getChildCount();

    for (int questionIndex = 0; questionIndex < numQuestions; questionIndex++) {
      //by design, the first child view in every question is a linear view
      ViewGroup linearGroup = (ViewGroup) questionsGroup.getChildAt(questionIndex);

      //also by design, the second child view of every linear view is a view group that contains
      // the choices
      TextView questionTextView = (TextView)linearGroup.getChildAt(0);
      String questionNumber = String.format(questionNumberFormat, questionIndex + 1);
      String questionText = questionNumber + questionTextView.getText().toString();
      questionTextView.setText(questionText);
    }
  }
}