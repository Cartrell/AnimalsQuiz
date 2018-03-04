package com.example.android.animalsquiz;

import java.util.ArrayList;
import java.util.HashMap;

class UserChoicesData {
  //===============================================================
  // members
  //===============================================================
  private HashMap<String, Object> m_data;

  //===============================================================
  // package
  //===============================================================

  //---------------------------------------------------------------
  // ctor
  //---------------------------------------------------------------
  UserChoicesData(ArrayList<QuestionData> selectedQuestionsData) {
    int numQuestions = selectedQuestionsData.size();
    m_data = new HashMap<>();

    for (int index = 0; index < numQuestions; index++) {
      QuestionData questionData = selectedQuestionsData.get(index);
      String questionId = questionData.getId();
      switch (questionData.getQuestionType()) {
        case QuestionData.CHOICE_TYPE_CHECK:
          m_data.put(questionId, initCheckChoiceData(questionData));
          break;

        case QuestionData.CHOICE_TYPE_EDIT:
          m_data.put(questionId, initEditChoiceData(questionData));
          break;

        case QuestionData.CHOICE_TYPE_RADIO:
          m_data.put(questionId, initRadioChoiceData(questionData));
          break;
      }
    }
  }

  //---------------------------------------------------------------
  // getData
  //---------------------------------------------------------------
  HashMap<String, Object> getData() {
    return(m_data);
  }

  //---------------------------------------------------------------
  // setSelectedChoice
  //---------------------------------------------------------------
  void setSelectedChoice(String questionId, Object userChoice) {
    if (m_data.containsKey(questionId)) {
      m_data.put(questionId, userChoice);
    }
  }

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // initCheckChoiceData
  //---------------------------------------------------------------
  private boolean[] initCheckChoiceData(QuestionData questionData) {
    int size = questionData.getCorrectChoiceStringIds().length +
            questionData.getIncorrectChoiceStringIds().length;
    return(new boolean[size]);
  }

  //---------------------------------------------------------------
  // initEditChoiceData
  //---------------------------------------------------------------
  private String initEditChoiceData(QuestionData questionData) {
    return("");
  }

  //---------------------------------------------------------------
  // initRadioChoiceData
  //---------------------------------------------------------------
  private int initRadioChoiceData(QuestionData questionData) {
    return(-1);
  }
}
