package com.example.android.animalsquiz.questionbuilder;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.android.animalsquiz.IQuizChoiceCallbacks;
import com.example.android.animalsquiz.QuestionData;
import com.example.android.animalsquiz.R;

public class CheckQuestionBuilder extends QuestionBuilder implements CompoundButton.OnCheckedChangeListener {
  //===============================================================
  // static
  //===============================================================
  private static int sm_nextButtonId = 2000;

  //===============================================================
  // members
  //===============================================================
  private LinearLayout m_checkGroup;

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
    linearLayout.setTag(QuestionData.CHOICE_TYPE_CHECK);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    linearLayout.setLayoutParams(params);

    linearLayout.setOrientation(LinearLayout.VERTICAL);

    createQuestionTextView(linearLayout, questionData);
    createQuestionImage(linearLayout, questionData);
    createCheckGroup(linearLayout, questionData);

    if (!isLastQuestion) {
      createQuestionDivider(linearLayout);
    }

    return(linearLayout);
  }

  //---------------------------------------------------------------
  // onCheckedChanged
  //---------------------------------------------------------------
  @Override
  public void onCheckedChanged(CompoundButton button, boolean isChecked) {
    if (m_callbacks == null) {
      return; //sanity check
    }

    int numChoices = m_checkGroup.getChildCount();
    boolean[] areBoxesChecked = new boolean[numChoices];

    for (int index = 0; index < numChoices; index++) {
      CheckBox checkBox = (CheckBox)m_checkGroup.getChildAt(index);
      areBoxesChecked[index] = checkBox.isChecked();
    }

    m_callbacks.quizChoiceChanged(getQuestionId(), areBoxesChecked);
  }

  //---------------------------------------------------------------
  // setUserChoice
  //---------------------------------------------------------------
  @Override
  public void setUserChoice(Object userChoice) {
    if (userChoice == null) {
      return; //sanity check
    }

    boolean[] areBoxesChecked = (boolean[])userChoice;
    int numChoices = m_checkGroup.getChildCount();

    for (int index = 0; index < numChoices; index++) {
      CheckBox checkBox = (CheckBox)m_checkGroup.getChildAt(index);
      checkBox.setChecked(areBoxesChecked[index]);
    }
  }

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // createCheckBox
  //---------------------------------------------------------------
  private void createCheckBox(String label, boolean isCorrect) {
    //to set style programmatically, with api < 21, need to set style this way. see:
    //https://stackoverflow.com/questions/3142067/android-set-style-in-code

    //how to use layout inflation:
    //https://possiblemobile.com/2013/05/layout-inflation-as-intended/
    Context context = m_checkGroup.getContext();
    Activity activity = (Activity)context;
    CheckBox button = (CheckBox)activity.getLayoutInflater().inflate(
            R.layout.checkbox_template, m_checkGroup, false);
    button.setId(++sm_nextButtonId);

    setButtonFont(button, context.getResources().getString(R.string.fontfile_designosaur_regular),
            context);

    button.setText(label);
    button.setTag(isCorrect);
    button.setOnCheckedChangeListener(this);
    m_checkGroup.addView(button);
  }

  //---------------------------------------------------------------
  // createCheckBoxes
  //---------------------------------------------------------------
  private void createCheckBoxes(boolean isCorrect, String[] buttonLabels) {
    for (String buttonLabel : buttonLabels) {
      createCheckBox(buttonLabel, isCorrect);
    }
  }

  //---------------------------------------------------------------
  // createCheckGroup
  //---------------------------------------------------------------
  private void createCheckGroup(LinearLayout linearLayout, QuestionData questionData) {
    m_checkGroup = new LinearLayout(linearLayout.getContext());
    m_checkGroup.setTag(CHOICES_GROUP_TAG);
    m_checkGroup.setOrientation(LinearLayout.VERTICAL);

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    params.gravity = Gravity.CENTER_HORIZONTAL;
    m_checkGroup.setLayoutParams(params);

    //create the check boxes that represent the correct and incorrect choices
    createCheckBoxes(true, questionData.getCorrectChoiceStringIds());
    createCheckBoxes(false, questionData.getIncorrectChoiceStringIds());

    //mix up the order of the radio buttons
    /*ChoicesMixer choicesMixer = new ChoicesMixer();
    choicesMixer.mix(m_checkGroup);*/

    //finally add the check box group to the linear layout
    linearLayout.addView(m_checkGroup);
  }
}
