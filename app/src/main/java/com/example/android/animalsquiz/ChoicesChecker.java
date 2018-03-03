package com.example.android.animalsquiz;

import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.android.animalsquiz.questionbuilder.QuestionBuilder;

import java.util.ArrayList;

class ChoicesChecker {
  /**
   * This class is handles checking qhich questions are correct and incorrect.
   */

  //===============================================================
  // member variables
  //===============================================================
  private ArrayList<Integer> m_incorrectQuestions;

  //===============================================================
  // package
  //===============================================================

  //---------------------------------------------------------------
  // ctor
  //---------------------------------------------------------------
  /**
   * ChoicesChecker constructor.
   */
  ChoicesChecker() {
    m_incorrectQuestions = new ArrayList<>();
  }

  //---------------------------------------------------------------
  // getIncorrectQuestions
  //---------------------------------------------------------------

  /**
   * Returns the array list of Integer objects that indicate which zero-based question numbers
   * are incorrect.
   */
  ArrayList<Integer> getIncorrectQuestions() {
    return(m_incorrectQuestions);
  }

  //---------------------------------------------------------------
  // run
  //---------------------------------------------------------------
  /**
   * Checks the number of correct questions.
   * @param questionsGroup the view group that contains all the questions
   * @return the number of correct answers
   */
  int run(ViewGroup questionsGroup) {
    m_incorrectQuestions.clear();

    int numCorrect = 0;
    int numQuestions = questionsGroup.getChildCount();

    for (int questionIndex = 0; questionIndex < numQuestions; questionIndex++) {
      ViewGroup questionView = (ViewGroup)questionsGroup.getChildAt(questionIndex);

      String questionType = (String)questionView.getTag();
      boolean isQuestionCorrect;

      switch (questionType) {
        case QuestionData.CHOICE_TYPE_CHECK:
          isQuestionCorrect = isCheckQuestionCorrect(questionView);
          break;

        case QuestionData.CHOICE_TYPE_EDIT:
          isQuestionCorrect = isEditQuestionCorrect(questionView);
          break;

        case QuestionData.CHOICE_TYPE_RADIO:
          isQuestionCorrect = isRadioQuestionCorrect(questionView);
          break;

        default:
          //if all question types were handled correctly during setup, should never get here
          // but, just in case (jic)...
          isQuestionCorrect = false;
          break;
      }

      if (isQuestionCorrect) {
        numCorrect++;
      } else {
        m_incorrectQuestions.add(questionIndex);
      }
    }

    return(numCorrect);
  }

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // isCheckQuestionCorrect
  //---------------------------------------------------------------

  /**
   * Determines if a question with checkboxes is correct.
   * @param questionView The question view that contains the selected answer.
   * @return true if the question is correct
   */
  private boolean isCheckQuestionCorrect(ViewGroup questionView) {
    //find the view group that contains the check buttons
    LinearLayout linearGroup = questionView.findViewWithTag(QuestionBuilder.CHOICES_GROUP_TAG);
    if (linearGroup == null) {
      return(false); //sanity check
    }

    //loop through each checkbox and determine if it is marked (or not marked) correctly
    for (int index = 0; index < linearGroup.getChildCount(); index++) {
      CheckBox button = (CheckBox)linearGroup.getChildAt(index);
      boolean isCorrect = (boolean)button.getTag();
      boolean isChecked = button.isChecked();
      if (isCorrect && !isChecked) {
        return(false); //the check button represents a correct choice, but it wasn't selected
      } else if (!isCorrect && isChecked) {
        return(false); //the check button represents an incorrect choice, but it WAS selected!
      }
    }

    //if the code reaches here, all the correct boxes were checked, and all the incorrect ones
    // were not checked, so the check question is correct
    return(true);
  }

  //---------------------------------------------------------------
  // isEditQuestionCorrect
  //---------------------------------------------------------------

  /**
   * Determines if a question with an exit text is correct.
   * @param questionView The question view that contains the selected answer.
   * @return true if the question is correct
   */
  private boolean isEditQuestionCorrect(ViewGroup questionView) {
    //get the edit text of he questions view
    EditText editText = questionView.findViewById(QuestionBuilder.EDIT_TEXT_TAG);
    if (editText == null) {
      return(false); //sanity check
    }

    //an edit text may have more than one correct answer
    String[] correctStrings = (String[])editText.getTag();
    if (correctStrings == null) {
      return(false); //sanity check
    }

    //check all the correct strings and see if one of them matches the exit text's text
    String editTextString = editText.getText().toString();
    for (String correctString : correctStrings) {
      //ignore the case of the edit text - therefore "answer" s the same as "AnSwER"
      if (correctString.equalsIgnoreCase(editTextString)) {
        return(true); //found a match - correct
      }
    }

    return(false); //no matches found - incorrect
  }

  //---------------------------------------------------------------
  // isRadioQuestionCorrect
  //---------------------------------------------------------------
  /**
   * Determines if a question with radio buttons is correct.
   * @param questionView The question view that contains the selected answer.
   * @return true if the question is correct
   */
  private boolean isRadioQuestionCorrect(ViewGroup questionView) {
    //find the view group that contains the radio buttons
    RadioGroup radioGroup = questionView.findViewWithTag(QuestionBuilder.CHOICES_GROUP_TAG);
    if (radioGroup == null) {
      return(false); //sanity check
    }

    //determine if a radio button has been selected
    int checkedButtonId = radioGroup.getCheckedRadioButtonId();
    if (checkedButtonId == 0) {
      return(false); //no radio button selected
    }

    //find radio button with a tag of true
    RadioButton button = radioGroup.findViewWithTag(true);

    //the radio button is the correct button if if has a tag of true, and its ID matches that
    // of selected button
    return(button != null && button.getId() == checkedButtonId);
  }
}