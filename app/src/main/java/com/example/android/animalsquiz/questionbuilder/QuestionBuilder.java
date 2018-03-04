package com.example.android.animalsquiz.questionbuilder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.animalsquiz.FontCache;
import com.example.android.animalsquiz.IQuizChoiceCallbacks;
import com.example.android.animalsquiz.QuestionData;
import com.example.android.animalsquiz.R;

/**
 * This base class builds a view group for a quiz question. This includes the question text, choices
 * (radio or check buttons, or edit view). Objects of this class should not be instantiated
 * directly. Instead this class should be extended.
 */
abstract public class QuestionBuilder {
  //===============================================================
  // variables
  //===============================================================
  public static final int CHOICES_GROUP_TAG = 1;
  public static final int EDIT_TEXT_TAG = 2;

  //===============================================================
  // members
  //===============================================================
  protected IQuizChoiceCallbacks m_callbacks;
  private String m_questionId;

  //===============================================================
  // public
  //===============================================================

  //---------------------------------------------------------------
  // build
  //---------------------------------------------------------------

  /**
   * While the QuizBuilder is building the question view groups, it will call this method. Objects
   * of this class must be a subclass, and they must override this method, call this base class
   * first, then provide the necessary functionality and return a view group depending on the type
   * of question.
   * @param context The context of the app.
   * @param questionData The question from which the view group is being created.
   * @param isLastQuestion This will be true if this question is the last question to be created.
   *                       This is for any special functionality that happens on the last question.
   * @param callbacks Callbacks invoked when the user changes the response to a quiz question.
   * @return The ViewGroup that represents the question. Note that this base method will always
   *  return null, so it's up to the overriding method to return the ViewGroup.
   */
  public ViewGroup build(Context context, QuestionData questionData, boolean isLastQuestion,
                         IQuizChoiceCallbacks callbacks) {
    m_callbacks = callbacks;
    m_questionId = questionData.getId();
    return(null);
  }

  //---------------------------------------------------------------
  // getQuestionId
  //---------------------------------------------------------------
  public String getQuestionId() {
    return(m_questionId);
  }

  //---------------------------------------------------------------
  // setUserChoice
  //---------------------------------------------------------------
  abstract public void setUserChoice(Object userChoice);

  //===============================================================
  // package
  //===============================================================

  //---------------------------------------------------------------
  // ctor
  //---------------------------------------------------------------
  /**
   * QuestionData constructor.
   */
  QuestionBuilder() {
  }

  //---------------------------------------------------------------
  // createQuestionDivider
  //---------------------------------------------------------------
  void createQuestionDivider(LinearLayout linearLayout) {
    Context context = linearLayout.getContext();
    View view = new View(context);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 1);
    params.setMargins(16, 16, 16, 16);
    view.setLayoutParams(params);
    view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
    linearLayout.addView(view);
  }

  //---------------------------------------------------------------
  // createQuestionImage
  //---------------------------------------------------------------
  void createQuestionImage(LinearLayout linearLayout, QuestionData questionData) {
    QuestionImageBuilder imageBuilder = new QuestionImageBuilder();
    LinearLayout imageLayout = imageBuilder.build(linearLayout.getContext(), questionData);
    if (imageLayout != null) {
      linearLayout.addView(imageLayout);
    }
  }

  //---------------------------------------------------------------
  // createQuestionTextView
  //---------------------------------------------------------------
  /**
   * Creates the question text view.
   * TextView textView = new TextView(linearLayout.getContext());
   * to set style programmatically, with api < 21, need to set style this way. see:
   * https://stackoverflow.com/questions/3142067/android-set-style-in-code
   * how to use layout inflation:
   * https://possiblemobile.com/2013/05/layout-inflation-as-intended/
   * @param linearLayout The parent linear layout to which the question text view will be added.
   * @param questionData The question from which the view group is being created.
   */
  void createQuestionTextView(LinearLayout linearLayout, QuestionData questionData) {
    Context context = linearLayout.getContext();
    Activity activity = (Activity)context;
    TextView textView = (TextView)activity.getLayoutInflater().inflate(
            R.layout.questiontext_template, linearLayout, false);

    textView.setText(questionData.getQuestionString());
    linearLayout.addView(textView);
  }

  //---------------------------------------------------------------
  // setButtonFont
  //---------------------------------------------------------------
  void setButtonFont(Button button, String fontFile, Context context) {
    Typeface typeface = FontCache.Get(fontFile, context);
    if (typeface != null) {
      button.setTypeface(typeface);
    }
  }
}
