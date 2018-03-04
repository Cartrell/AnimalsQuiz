package com.example.android.animalsquiz.questionbuilder;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.android.animalsquiz.IQuizChoiceCallbacks;
import com.example.android.animalsquiz.QuestionData;
import com.example.android.animalsquiz.R;

public class RadioQuestionBuilder extends QuestionBuilder implements RadioGroup.OnCheckedChangeListener {
  //===============================================================
  // static
  //===============================================================
  private static int sm_nextButtonId = 1000;

  //===============================================================
  // members
  //===============================================================
  private RadioGroup m_radioGroup;

  //===============================================================
  // public
  //===============================================================

  //---------------------------------------------------------------
  // build
  //---------------------------------------------------------------
  @Override
  public ViewGroup build(Context context, QuestionData questionData, boolean isLastQuestion,
                         IQuizChoiceCallbacks callbacks) {
    super.build(context, questionData, isLastQuestion, callbacks);

    //Create the question linear layout. This is a container for all the view objects for
    // this question.
    LinearLayout linearLayout = new LinearLayout(context);
    linearLayout.setTag(QuestionData.CHOICE_TYPE_RADIO);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT);
    linearLayout.setLayoutParams(params);

    linearLayout.setOrientation(LinearLayout.VERTICAL);

    createQuestionTextView(linearLayout, questionData);
    createQuestionImage(linearLayout, questionData);
    createRadioGroup(linearLayout, questionData);

    if (!isLastQuestion) {
      createQuestionDivider(linearLayout);
    }

    return(linearLayout);
  }

  //---------------------------------------------------------------
  // onCheckedChanged
  //---------------------------------------------------------------
  @Override
  public void onCheckedChanged(RadioGroup radioGroup, int checkedRadioButtonId) {
    if (m_callbacks == null) {
      return; //sanity check
    }

    int radioIndex = m_radioGroup.indexOfChild(m_radioGroup.findViewById(checkedRadioButtonId));
    m_callbacks.quizChoiceChanged(getQuestionId(), radioIndex);
  }

  //---------------------------------------------------------------
  // setUserChoice
  //---------------------------------------------------------------
  @Override
  public void setUserChoice(Object userChoice) {
    if (userChoice == null) {
      return; //sanity check
    }

    int radioChildIndex = (int)userChoice;
    if (radioChildIndex >= m_radioGroup.getChildCount()) {
      return; //sanity check
    }

    RadioButton button = (RadioButton)m_radioGroup.getChildAt(radioChildIndex);
    if (button != null) {
      button.setChecked(true);
    }
  }

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // createRadioButton
  //---------------------------------------------------------------
  private void createRadioButton(String label, boolean isCorrect) {
    //to set style programmatically, with api < 21, need to set style this way. see:
    //https://stackoverflow.com/questions/3142067/android-set-style-in-code

    //how to use layout inflation:
    //https://possiblemobile.com/2013/05/layout-inflation-as-intended/
    Context context = m_radioGroup.getContext();
    Activity activity = (Activity)context;
    RadioButton button = (RadioButton)activity.getLayoutInflater().inflate(
            R.layout.radiobutton_template, m_radioGroup, false);
    button.setId(++sm_nextButtonId);
    button.setText(label);
    button.setTag(isCorrect);

    setButtonFont(button, context.getResources().getString(R.string.fontfile_designosaur_regular),
            context);

    m_radioGroup.addView(button);
  }

  //---------------------------------------------------------------
  // createRadioButtons
  //---------------------------------------------------------------
  private void createRadioButtons(boolean isCorrect, String[] buttonLabels) {
    for (String buttonLabel : buttonLabels) {
      createRadioButton(buttonLabel, isCorrect);
    }
  }

  //---------------------------------------------------------------
  // createRadioGroup
  //---------------------------------------------------------------
  private void createRadioGroup(LinearLayout linearLayout, QuestionData questionData) {
    m_radioGroup = new RadioGroup(linearLayout.getContext());
    m_radioGroup.setTag(CHOICES_GROUP_TAG);
    m_radioGroup.setOrientation(RadioGroup.VERTICAL);
    m_radioGroup.setOnCheckedChangeListener(this);

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    params.gravity = Gravity.CENTER_HORIZONTAL;
    m_radioGroup.setLayoutParams(params);

    //create the radio buttons that represent the correct and incorrect choices
    createRadioButtons(true,  questionData.getCorrectChoiceStringIds());
    createRadioButtons(false,  questionData.getIncorrectChoiceStringIds());

    //mix up the order of the radio buttons
    /*ChoicesMixer choicesMixer = new ChoicesMixer();
    choicesMixer.mix(m_radioGroup);*/

    //finally add the radio group to the linear layout
    linearLayout.addView(m_radioGroup);
  }
}
