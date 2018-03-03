package com.example.android.animalsquiz;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.animalsquiz.questionbuilder.CheckQuestionBuilder;
import com.example.android.animalsquiz.questionbuilder.EditQuestionBuilder;
import com.example.android.animalsquiz.questionbuilder.RadioQuestionBuilder;

import java.util.ArrayList;

class QuizBuilder {
  //===============================================================
  // package
  //===============================================================

  //---------------------------------------------------------------
  // build
  //---------------------------------------------------------------
  void build(ViewGroup questionsGroup, ArrayList<QuestionData> questionsData,
             String questionNumberFormat) {

    /*
    Make sure that:
    1). MIN_NUM_QUESTIONS is not larger than MAX_NUM_QUESTIONS.
    2). MAX_NUM_QUESTIONS does not exceed the total number of questions available in the resources.
    3). Neither MIN nor MAX is less than 1.
     */
    final int MIN_NUM_QUESTIONS = 5;
    final int MAX_NUM_QUESTIONS = 10;
    int numQuestions = MIN_NUM_QUESTIONS +
            (int)(Math.random() * (MAX_NUM_QUESTIONS - MIN_NUM_QUESTIONS + 1));

    ArrayList<QuestionData>questionsUsedData = pickRandomQuestions(questionsData, numQuestions);
    buildQuestionViews(questionsGroup, questionsUsedData);
    numberQuestions(questionsGroup, questionNumberFormat);
  }

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // buildQuestionViews
  //---------------------------------------------------------------
  private void buildQuestionViews(ViewGroup questionsGroup, ArrayList<QuestionData> questionsData) {
    RadioQuestionBuilder radioQuestionBuilder = new RadioQuestionBuilder();
    CheckQuestionBuilder checkQuestionBuilder = new CheckQuestionBuilder();
    EditQuestionBuilder editQuestionBuilder = new EditQuestionBuilder();

    Context context = questionsGroup.getContext();
    int lastQuestionIndex = questionsData.size() - 1;
    for (int questionIndex = 0; questionIndex <= lastQuestionIndex; questionIndex++) {
      QuestionData questionData = questionsData.get(questionIndex);

      ViewGroup questionView;
      boolean isLastQuestion = questionIndex == lastQuestionIndex;
      switch (questionData.getQuestionType()) {
        case QuestionData.CHOICE_TYPE_RADIO:
          questionView = radioQuestionBuilder.build(context, questionData, isLastQuestion);
          break;

        case QuestionData.CHOICE_TYPE_CHECK:
          questionView = checkQuestionBuilder.build(context, questionData, isLastQuestion);
          break;

        case QuestionData.CHOICE_TYPE_EDIT:
          questionView = editQuestionBuilder.build(context, questionData, isLastQuestion);
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

  //---------------------------------------------------------------
  // pickRandomQuestions
  //---------------------------------------------------------------
  private ArrayList<QuestionData> pickRandomQuestions(ArrayList<QuestionData> sourceData,
                                                      int numQuestions) {
    //create a copy of the source data array
    ArrayList<QuestionData> randomList = new ArrayList<>(sourceData);

    //randomize the order of the elements in the random list
    int length = randomList.size();
    for (int index = 0; index < length; index++) {
      //pick a random index from the array
      int randomIndex = (int)(Math.random() * length);

      //swap the value at current index with the random index
      swapElementsAt(randomList, index, randomIndex);
    }

    //build the target array list and copy the first "numQuestions" number of questions from
    // the random list to this target list
    ArrayList<QuestionData> targetList = new ArrayList<>(numQuestions);
    for (int index = 0; index < numQuestions; index++) {
      QuestionData selectedQuestion = randomList.get(index);
      Log.v("pickRandomQuestions", "building question: " + selectedQuestion.getId());
      targetList.add(index, selectedQuestion);
    }

    return(targetList);
  }

  //---------------------------------------------------------------
  // swapElementsAt
  //---------------------------------------------------------------
  /**
   * Swaps the data of two elements within an array list. The array list itself is
   * modified so there is no need to return anything.
   * @param arrayList the array list whose elements will be swapped
   * @param index1 the index of the first element to swap
   * @param index2 the index of the second element to swap
   */
  private void swapElementsAt(ArrayList<QuestionData> arrayList, int index1, int index2) {
    //save the element at index1 because we're about to replace it with what's in index2
    QuestionData temp = arrayList.get(index1);

    //Place the element that is located at index2 into index1. This will overwrite the previous
    // element that was at index1, but remember: we saved it in the temp variable first.
    arrayList.set(index1, arrayList.get(index2));

    //Finally, set the element at the index2 location.
    arrayList.set(index2, temp);
  }
}