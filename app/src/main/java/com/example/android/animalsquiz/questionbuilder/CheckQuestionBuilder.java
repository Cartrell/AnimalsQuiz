package com.example.android.animalsquiz.questionbuilder;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.android.animalsquiz.ChoicesMixer;
import com.example.android.animalsquiz.QuestionData;
import com.example.android.animalsquiz.R;

public class CheckQuestionBuilder extends QuestionBuilder {
  //===============================================================
  // static
  //===============================================================
  private static int sm_nextButtonId = 2000;

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

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // createCheckBox
  //---------------------------------------------------------------
  private void createCheckBox(LinearLayout linearLayout, String label, boolean isCorrect) {
    //to set style programmatically, with api < 21, need to set style this way. see:
    //https://stackoverflow.com/questions/3142067/android-set-style-in-code

    //how to use layout inflation:
    //https://possiblemobile.com/2013/05/layout-inflation-as-intended/
    Context context = linearLayout.getContext();
    Activity activity = (Activity)context;
    CheckBox button = (CheckBox)activity.getLayoutInflater().inflate(
            R.layout.checkbox_template, linearLayout, false);
    button.setId(++sm_nextButtonId);

    setButtonFont(button, context.getResources().getString(R.string.fontfile_designosaur_regular),
            context);

    button.setText(label);
    button.setTag(isCorrect);
    linearLayout.addView(button);
  }

  //---------------------------------------------------------------
  // createCheckBoxes
  //---------------------------------------------------------------
  private void createCheckBoxes(LinearLayout linearLayout, boolean isCorrect, String[] buttonLabels) {
    for (String buttonLabel : buttonLabels) {
      createCheckBox(linearLayout, buttonLabel, isCorrect);
    }
  }

  //---------------------------------------------------------------
  // createCheckGroup
  //---------------------------------------------------------------
  private void createCheckGroup(LinearLayout linearLayout, QuestionData questionData) {
    LinearLayout checkGroup = new LinearLayout(linearLayout.getContext());
    checkGroup.setTag(CHOICES_GROUP_TAG);
    checkGroup.setOrientation(LinearLayout.VERTICAL);

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    params.gravity = Gravity.CENTER_HORIZONTAL;
    checkGroup.setLayoutParams(params);

    //create the check boxes that represent the correct and incorrect choices
    createCheckBoxes(checkGroup, true, questionData.getCorrectChoiceStringIds());
    createCheckBoxes(checkGroup, false, questionData.getIncorrectChoiceStringIds());

    //mix up the order of the radio buttons
    ChoicesMixer choicesMixer = new ChoicesMixer();
    choicesMixer.mix(checkGroup);

    //finally add the check box group to the linear layout
    linearLayout.addView(checkGroup);
  }
}
