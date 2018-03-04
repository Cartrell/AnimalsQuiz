package com.example.android.animalsquiz;

import android.util.Log;

import java.util.ArrayList;

class QuizQuestionsSelector {

  //===============================================================
  // package
  //===============================================================

  //---------------------------------------------------------------
  // build
  //---------------------------------------------------------------
  ArrayList<QuestionData> build(ArrayList<QuestionData> sourceQuestionsData) {
    if (sourceQuestionsData == null) {
      return(null); //sanity check
    }

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

    numQuestions = Math.min(numQuestions, sourceQuestionsData.size());
    if (numQuestions < 1) {
      return(null); //sanity check
    }

    return(pickRandomQuestions(sourceQuestionsData, numQuestions));
  }

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // isMultipleChoiceQuestion
  //---------------------------------------------------------------
  private boolean isMultipleChoiceQuestion(QuestionData questionData) {
    String type = questionData.getQuestionType();
    return(QuestionData.CHOICE_TYPE_CHECK.equals(type) ||
            QuestionData.CHOICE_TYPE_RADIO.equals(type));
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
      swapArrayElements(randomList, index, randomIndex);
    }

    //build the target array list and copy the first "numQuestions" number of questions from
    // the random list to this target list
    ArrayList<QuestionData> targetList = new ArrayList<>(numQuestions);
    for (int index = 0; index < numQuestions; index++) {
      QuestionData selectedQuestion = randomList.get(index);
      Log.v("pickRandomQuestions", "building question: " + selectedQuestion.getId());

      if (isMultipleChoiceQuestion(selectedQuestion)) {
        randomizeQuestionChoices(selectedQuestion);
      }

      targetList.add(index, selectedQuestion);
    }

    return(targetList);
  }

  //---------------------------------------------------------------
  // randomizeQuestionChoiceIds
  //---------------------------------------------------------------
  private void randomizeQuestionChoiceIds(String[] choiceStringIds_in_out) {
    int length = choiceStringIds_in_out.length;
    for (int index = 0; index < length; index++) {
      int randomIndex = (int)(Math.random() * length);
      swapArrayElements(choiceStringIds_in_out, index, randomIndex);
    }
  }

  //---------------------------------------------------------------
  // randomizeQuestionChoices
  //---------------------------------------------------------------
  private void randomizeQuestionChoices(QuestionData questionData) {
    randomizeQuestionChoiceIds(questionData.getCorrectChoiceStringIds());
    randomizeQuestionChoiceIds(questionData.getIncorrectChoiceStringIds());
  }

  //---------------------------------------------------------------
  // swapArrayElements
  //---------------------------------------------------------------
  /**
   * Swaps the data of two elements within an array list. The array list itself is
   * modified so nothing is returned.
   * @param values the array whose elements will be swapped
   * @param index1 the index of the first element to swap
   * @param index2 the index of the second element to swap
   */
  private void swapArrayElements(ArrayList<QuestionData> values, int index1, int index2) {
    //save the element at index1 because we're about to replace it with what's in index2
    QuestionData temp = values.get(index1);

    //Place the element that is located at index2 into index1. This will overwrite the previous
    // element that was at index1, but remember: we saved it in the temp variable first.
    values.set(index1, values.get(index2));

    //Finally, set the element at the index2 location.
    values.set(index2, temp);
  }

  //---------------------------------------------------------------
  // swapArrayElements
  //---------------------------------------------------------------
  /**
   * Swaps the data of two elements within an array list. The array list itself is
   * modified so nothing is returned.
   * @param values the array whose elements will be swapped
   * @param index1 the index of the first element to swap
   * @param index2 the index of the second element to swap
   */
  private void swapArrayElements(String[] values, int index1, int index2) {
    String temp = values[index1];
    values[index1] = values[index2];
    values[index2] = temp;
  }
}