package com.example.android.animalsquiz;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.animalsquiz.databinding.ActivityResultsBinding;

public class ResultsActivity extends AppCompatActivity {

  //intent messages for sending to the main activity
  private static final String MSG_REPLAY = "com.example.android.animalsquiz.MSG_REPLAY";

  //===============================================================
  // member variables
  //===============================================================
  private ActivityResultsBinding m_binding;
  private ObjectAnimator m_scoreTextAnimator;

  //===============================================================
  // public
  //===============================================================

  //---------------------------------------------------------------
  // onBtnPlayAgain
  //---------------------------------------------------------------
  /**
   * Called when the play again button is pressed.
   * @param view The view that called this method
   */
  public void onBtnPlayAgain(View view) {
    Intent intent = new Intent(this, MainActivity.class);
    intent.putExtra(MSG_REPLAY, true);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
    m_binding = DataBindingUtil.setContentView(this, R.layout.activity_results);
    init();
  }

  //---------------------------------------------------------------
  // onDestroy
  //---------------------------------------------------------------
  @Override
  protected void onDestroy() {
    destroyScoreTextAnimator();
    super.onDestroy();
  }

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // createScoreTextAnimator
  //---------------------------------------------------------------
  private void createScoreTextAnimator() {
    m_scoreTextAnimator = ObjectAnimator.ofInt(m_binding.txtResultsScore, "textColor",
            0xffff0000, 0xffffff00, 0xff00ff00, 0xff00ffff, 0xff0000ff, 0xffff00ff);
    m_scoreTextAnimator.setEvaluator(new ArgbEvaluator());
    m_scoreTextAnimator.setDuration(1000);
    m_scoreTextAnimator.setRepeatCount(ObjectAnimator.INFINITE);
    m_scoreTextAnimator.start();
  }

  //---------------------------------------------------------------
  // destroyScoreTextAnimator
  //---------------------------------------------------------------
  private void destroyScoreTextAnimator() {
    if (m_scoreTextAnimator != null) {
      m_scoreTextAnimator.cancel();
      m_scoreTextAnimator = null;
    }
  }

  //---------------------------------------------------------------
  // init
  //---------------------------------------------------------------
  private void init() {
    Intent intent = getIntent();

    //get the values sent from the main activity via the intent
    int numQuestions = intent.getIntExtra(MainActivity.MSG_NUM_QUESTIONS, 0);
    int numCorrectQuestions = intent.getIntExtra(MainActivity.MSG_NUM_CORRECT_QUESTIONS, 0);

    setScoreText(numQuestions, numCorrectQuestions);
    setFeedbackText(numQuestions, numCorrectQuestions);

    if (numCorrectQuestions == numQuestions) {
      createScoreTextAnimator();
    }

    int[] incorrectQuestions = intent.getIntArrayExtra(MainActivity.MSG_INCORRECT_QUESTIONS);
    setIncorrectQuestions(incorrectQuestions);
  }

  //---------------------------------------------------------------
  // setFeedbackText
  //---------------------------------------------------------------
  private void setFeedbackText(int numQuestions, int numCorrectQuestions) {
    float percentageCorrect;

    if (numQuestions == 0) {
      // add check to prevent a "divide by zero" exception
      percentageCorrect = 0;
    } else {
      percentageCorrect = numCorrectQuestions / numQuestions;
    }

    int feedbackResultsId;
    if (percentageCorrect == 1.0f) {
      feedbackResultsId = R.string.results_all_right;
    } else if (percentageCorrect >= 0.75f) {
      feedbackResultsId = R.string.results_most_right;
    } else if (percentageCorrect >= 0.5f) {
      feedbackResultsId = R.string.results_half_right;
    } else if (percentageCorrect >= 0) {
      feedbackResultsId = R.string.results_some_right;
    } else {
      feedbackResultsId = R.string.results_none_right;
    }

    m_binding.txtResultsFeedback.setText(feedbackResultsId);
  }

  //---------------------------------------------------------------
  // setIncorrectQuestions
  //---------------------------------------------------------------
  private void setIncorrectQuestions(int[] incorrectQuestions) {
    if (incorrectQuestions.length == 0) {
      //no incorrect questions
      m_binding.txtResultsIncorrectQuestions.setVisibility(View.GONE);
      return;
    }

    StringBuilder stringBuilder = new StringBuilder();
    int lastIndex = incorrectQuestions.length - 1;
    for (int index = 0; index <= lastIndex; index++) {
      int incorrectQuestion = incorrectQuestions[index];
      if (stringBuilder.length() == 0) {
        stringBuilder.append(incorrectQuestion + 1);
        continue;
      }

      boolean isLastOne = index == lastIndex;
      if (isLastOne) {
        stringBuilder.append(String.format(", and %1$d", incorrectQuestion + 1));
      } else {
        stringBuilder.append(String.format(", %1$d", incorrectQuestion + 1));
      }
    }

    String formatMessage = getResources().getString(R.string.results_incorrect_questions);
    String message = String.format(formatMessage, stringBuilder.toString());
    m_binding.txtResultsIncorrectQuestions.setText(message);
  }

  //---------------------------------------------------------------
  // setScoreText
  //---------------------------------------------------------------
  private void setScoreText(int numQuestions, int numCorrectQuestions) {
    String textFormat = getResources().getString(R.string.results_score);
    m_binding.txtResultsScore.setText(String.format(textFormat, numCorrectQuestions, numQuestions));
  }
}
