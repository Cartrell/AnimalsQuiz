package com.example.android.animalsquiz;

import android.content.res.TypedArray;

/**
 * Data block for a question.
 */
public class QuestionData {
  //===============================================================
  // final
  //===============================================================
  public static final String CHOICE_TYPE_RADIO = "radio";
  public static final String CHOICE_TYPE_CHECK = "check";
  public static final String CHOICE_TYPE_EDIT = "edit";

  //===============================================================
  // member variables
  //===============================================================
  private int m_numCorrectChoices;
  private String[] m_correctChoiceStringIds;
  private String[] m_incorrectChoiceStringIds;
  private String m_questionId;
  private String m_questionType;
  private String m_questionImageId;
  private String m_questionTextStringId;

  //===============================================================
  // public
  //===============================================================

  //---------------------------------------------------------------
  // getCorrectChoiceStringIds
  //---------------------------------------------------------------
  public String[] getCorrectChoiceStringIds() {
    return(m_correctChoiceStringIds);
  }

  //---------------------------------------------------------------
  // getId
  //---------------------------------------------------------------
  public String getId() {
    return(m_questionId);
  }

  //---------------------------------------------------------------
  // getIncorrectChoiceStringIds
  //---------------------------------------------------------------
  public String[] getIncorrectChoiceStringIds() {
    return(m_incorrectChoiceStringIds);
  }

  //---------------------------------------------------------------
  // getQuestionImageId
  //---------------------------------------------------------------
  public String getQuestionImageId() {
    return(m_questionImageId);
  }

  //---------------------------------------------------------------
  // getQuestionString
  //---------------------------------------------------------------
  public String getQuestionString() {
    return(m_questionTextStringId);
  }

  //---------------------------------------------------------------
  // toString
  //---------------------------------------------------------------
  @Override
  public String toString() {
    return(String.format("ID: %1$s" +
            "\nNum choices: %2$d" +
            "\nType: %3$s", m_questionId, m_numCorrectChoices, m_questionType));
  }

  //===============================================================
  // package
  //===============================================================

  //---------------------------------------------------------------
  // ctor
  //---------------------------------------------------------------
  /**
   * QuestionData constructor.
   */
  QuestionData() {
  }

  //---------------------------------------------------------------
  // getQuestionType
  //---------------------------------------------------------------
  String getQuestionType() {
    return(m_questionType);
  }

  //---------------------------------------------------------------
  // initialize
  //---------------------------------------------------------------
  int initialize(TypedArray questionsData, int tagIndex) {
    if (tagIndex >= questionsData.length()) {
      return(-1);
    }

    String itemName = questionsData.getString(tagIndex++);
    if (itemName == null) {
      return(-1);
    }

    if (itemName.equals("questionId")) {
      tagIndex = parseQuestionId(questionsData, tagIndex);
      tagIndex = parseRemainingData(questionsData, tagIndex);
    } else {
      //first tag must be "questionId"
      return(-1);
    }

    return(tagIndex);
  }

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // parseCorrectChoices
  //---------------------------------------------------------------
  private int parseCorrectChoices(TypedArray questionsData, int tagIndex) {
    if (tagIndex >= questionsData.length()) {
      return(-1);
    }

    m_numCorrectChoices = questionsData.getInt(tagIndex++, 0);
    if (m_numCorrectChoices <= 0) {
      return(-1);
    }

    m_correctChoiceStringIds = new String[m_numCorrectChoices];
    tagIndex = parseStringArray(questionsData, tagIndex, m_correctChoiceStringIds);

    return(tagIndex);
  }

  //---------------------------------------------------------------
  // parseIncorrectChoices
  //---------------------------------------------------------------
  private int parseIncorrectChoices(TypedArray questionsData, int tagIndex) {
    if (tagIndex >= questionsData.length()) {
      return(-1);
    }

    int numChoices = questionsData.getInt(tagIndex++, 0);
    if (numChoices < 0) {
      return(-1);
    } else if (numChoices > 0) {
      m_incorrectChoiceStringIds = new String[numChoices];
      tagIndex = parseStringArray(questionsData, tagIndex, m_incorrectChoiceStringIds);
    }

    return(tagIndex);
  }

  //---------------------------------------------------------------
  // parseQuestionId
  //---------------------------------------------------------------
  private int parseQuestionId(TypedArray questionsData, int tagIndex) {
    if (tagIndex >= questionsData.length()) {
      return(-1);
    }

    m_questionId = questionsData.getString(tagIndex++);
    return(tagIndex);
  }

  //---------------------------------------------------------------
  // parseQuestionImageId
  //---------------------------------------------------------------
  private int parseQuestionImageId(TypedArray questionsData, int tagIndex) {
    if (tagIndex >= questionsData.length()) {
      return(-1);
    }

    m_questionImageId = questionsData.getString(tagIndex++);
    return(tagIndex);
  }

  //---------------------------------------------------------------
  // parseQuestionType
  //---------------------------------------------------------------
  private int parseQuestionType(TypedArray questionsData, int tagIndex) {
    if (tagIndex >= questionsData.length()) {
      return(-1);
    }

    m_questionType = questionsData.getString(tagIndex++);
    if (m_questionType == null) {
      return(-1);
    }

    if (!m_questionType.equals(CHOICE_TYPE_RADIO) &&
            !m_questionType.equals(CHOICE_TYPE_CHECK) &&
            !m_questionType.equals(CHOICE_TYPE_EDIT)) {
      return(-1);
    }

    return(tagIndex);
  }

  //---------------------------------------------------------------
  // parseRemainingData
  //---------------------------------------------------------------
  private int parseRemainingData(TypedArray questionsData, int tagIndex) {
    while (tagIndex != -1 && tagIndex < questionsData.length()) {
      String itemName = questionsData.getString(tagIndex++);
      if (itemName == null) {
        return (-1);
      }

      switch (itemName) {
        case "questionId":
          //reached a new question, stop parsing this one

          //go back one index, so next question data can parse its question ID first
          return(--tagIndex);

        case "questionTextStringId":
          tagIndex = parseQuestionTextStringId(questionsData, tagIndex);
          break;

        case "questionType":
          tagIndex = parseQuestionType(questionsData, tagIndex);
          break;

        case "questionImageId":
          tagIndex = parseQuestionImageId(questionsData, tagIndex);
          break;

        case "correctChoices":
          tagIndex = parseCorrectChoices(questionsData, tagIndex);
          break;

        case "incorrectChoices":
          tagIndex = parseIncorrectChoices(questionsData, tagIndex);
          break;

        default:
          //skip unknown tags
      }
    }

    return(tagIndex);
  }

  //---------------------------------------------------------------
  // parseQuestionTextStringId
  //---------------------------------------------------------------
  private int parseQuestionTextStringId(TypedArray questionsData, int tagIndex) {
    if (tagIndex >= questionsData.length()) {
      return(-1);
    }
    m_questionTextStringId = questionsData.getString(tagIndex++);
    return(tagIndex);
  }

  //---------------------------------------------------------------
  // parseStringArray
  //---------------------------------------------------------------
  private int parseStringArray(TypedArray questionsData, int tagIndex, String[] stringIds_in_out) {
    if (tagIndex >= questionsData.length()) {
      return(-1);
    }

    int arrayIndex = 0;
    do {
      if (arrayIndex >= stringIds_in_out.length) {
        break;
      }

      String stringId = questionsData.getString(tagIndex++);
      if (stringId == null) {
        break;
      }

      stringIds_in_out[arrayIndex++] = stringId;
    } while (-1 < tagIndex && tagIndex < questionsData.length());

    return(tagIndex);
  }
}