package com.example.android.animalsquiz.questionbuilder;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.android.animalsquiz.ChoicesMixer;
import com.example.android.animalsquiz.QuestionData;
import com.example.android.animalsquiz.R;

public class RadioQuestionBuilder extends QuestionBuilder {
  //===============================================================
  // static
  //===============================================================
  private static int sm_nextButtonId = 1000;

  //===============================================================
  // public
  //===============================================================

  //---------------------------------------------------------------
  // build
  //---------------------------------------------------------------
  @Override
  public ViewGroup build(Context context, QuestionData questionData, boolean isLastQuestion) {
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

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // createRadioButton
  //---------------------------------------------------------------
  private void createRadioButton(RadioGroup radioGroup, String label, boolean isCorrect) {
    //to set style programmatically, with api < 21, need to set style this way. see:
    //https://stackoverflow.com/questions/3142067/android-set-style-in-code

    //how to use layout inflation:
    //https://possiblemobile.com/2013/05/layout-inflation-as-intended/
    Context context = radioGroup.getContext();
    Activity activity = (Activity)context;
    RadioButton button = (RadioButton)activity.getLayoutInflater().inflate(
            R.layout.radiobutton_template, radioGroup, false);
    button.setId(++sm_nextButtonId);
    button.setText(label);
    button.setTag(isCorrect);

    setButtonFont(button, context.getResources().getString(R.string.fontfile_designosaur_regular),
            context);

    radioGroup.addView(button);
  }

  //---------------------------------------------------------------
  // createRadioButtons
  //---------------------------------------------------------------
  private void createRadioButtons(RadioGroup radioGroup, boolean isCorrect, String[] buttonLabels) {
    for (String buttonLabel : buttonLabels) {
      createRadioButton(radioGroup, buttonLabel, isCorrect);
    }
  }

  //---------------------------------------------------------------
  // createRadioGroup
  //---------------------------------------------------------------
  private void createRadioGroup(LinearLayout linearLayout, QuestionData questionData) {
    RadioGroup radioGroup = new RadioGroup(linearLayout.getContext());
    radioGroup.setTag(CHOICES_GROUP_TAG);
    radioGroup.setOrientation(RadioGroup.VERTICAL);

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    params.gravity = Gravity.CENTER_HORIZONTAL;
    radioGroup.setLayoutParams(params);

    //create the radio buttons that represent the correct and incorrect choices
    createRadioButtons(radioGroup, true,  questionData.getCorrectChoiceStringIds());
    createRadioButtons(radioGroup, false,  questionData.getIncorrectChoiceStringIds());

    //mix up the order of the radio buttons
    ChoicesMixer choicesMixer = new ChoicesMixer();
    choicesMixer.mix(radioGroup);

    //finally add the radio group to the linear layout
    linearLayout.addView(radioGroup);
  }
}
