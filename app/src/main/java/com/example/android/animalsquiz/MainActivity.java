package com.example.android.animalsquiz;

import android.content.Intent;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.animalsquiz.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  //===============================================================
  // const
  //===============================================================

  //intent messages for sending to the results activity
  public static final String MSG_NUM_CORRECT_QUESTIONS = "com.example.android.animalsquiz.MSG_NUM_CORRECT_QUESTIONS";
  public static final String MSG_NUM_QUESTIONS = "com.example.android.animalsquiz.MSG_NUM_QUESTIONS";
  public static final String MSG_INCORRECT_QUESTIONS = "com.example.android.animalsquiz.MSG_INCORRECT_QUESTIONS ";

  //===============================================================
  // member variables
  //===============================================================
  private ActivityMainBinding m_binding;
  private ArrayList<QuestionData> m_questionsData;

  //===============================================================
  // public
  //===============================================================

  //---------------------------------------------------------------
  // onBtnGrading
  //---------------------------------------------------------------

  /**
   * Called when the grading button is pressed.
   *
   * @param view The view that called this method
   */
  public void onBtnGrading(View view) {
    ViewGroup questionsGroup = m_binding.questionsGroup;
    ChoicesChecker checker = new ChoicesChecker();
    int numCorrect = checker.run(questionsGroup);

    String formatString = getResources().getString(R.string.grading_message);
    CharSequence message = String.format(formatString, numCorrect, getNumQuestions());
    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    toast.show();
  }

  //---------------------------------------------------------------
  // onBtnSubmit
  //---------------------------------------------------------------

  /**
   * Called when the submit button is pressed.
   *
   * @param view The view that called this method
   */
  public void onBtnSubmit(View view) {
    ChoicesChecker checker = new ChoicesChecker();

    Intent intent = new Intent(this, ResultsActivity.class);

    int numCorrectQuestions = checker.run(m_binding.questionsGroup);
    intent.putExtra(MSG_NUM_CORRECT_QUESTIONS, numCorrectQuestions);

    int numQuestions = getNumQuestions();
    intent.putExtra(MSG_NUM_QUESTIONS, numQuestions);

    ArrayList<Integer> incorrectQuestions = checker.getIncorrectQuestions();
    intent.putExtra(MSG_INCORRECT_QUESTIONS, convertIntegersToInt(incorrectQuestions));

    startActivity(intent);
  }

  //===============================================================
  // protected
  //===============================================================

  //---------------------------------------------------------------
  // onCreate
  //---------------------------------------------------------------
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    m_binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    parseQuestionsData();
    buildQuiz();
  }

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // buildQuiz
  //---------------------------------------------------------------

  /**
   * Builds all the quiz questions and views.
   */
  private void buildQuiz() {
    QuizBuilder quizBuilder = new QuizBuilder();
    String questionNumberFormat = getResources().getString(R.string.questionNumberFormat);
    quizBuilder.build(m_binding.questionsGroup, m_questionsData, questionNumberFormat);
  }

  //---------------------------------------------------------------
  // convertIntegersToInt
  //---------------------------------------------------------------

  /**
   * Utility method for convertinf an array list of Integeter objects to a regular array of
   * primitive ints.
   *
   * @param arrayList the array list to convert
   * @return a regular array of primitive ints
   */
  private int[] convertIntegersToInt(ArrayList<Integer> arrayList) {
    int size = arrayList.size();
    int[] out = new int[size];
    for (int index = 0; index < size; index++) {
      out[index] = arrayList.get(index);
    }
    return (out);
  }

  //---------------------------------------------------------------
  // getNumQuestions
  //---------------------------------------------------------------

  /**
   * Returns the total number of questions available
   */
  private int getNumQuestions() {
    return (m_binding.questionsGroup.getChildCount());
  }

  //---------------------------------------------------------------
  // parseQuestionsData
  //---------------------------------------------------------------

  /**
   * Creates an array list of questions data by parsing the questions array resource.
   */
  private void parseQuestionsData() {
    TypedArray sourceQuestionsData = getResources().obtainTypedArray(R.array.questions);
    int numTags = sourceQuestionsData.length();
    int tagIndex = 0;

    m_questionsData = new ArrayList<>();

    while (tagIndex < numTags) {
      QuestionData questionData = new QuestionData();
      tagIndex = questionData.initialize(sourceQuestionsData, tagIndex);
      if (tagIndex != -1) {
        Log.v("parseQuestionsData", questionData.toString());
        m_questionsData.add(questionData);
      } else {
        Log.w("parseQuestionsData", "quit at tagIndex: " + tagIndex);
        break;
      }
    }

    Log.v("parseQuestionsData", "num questions parsed: " + m_questionsData.size());
  }
}