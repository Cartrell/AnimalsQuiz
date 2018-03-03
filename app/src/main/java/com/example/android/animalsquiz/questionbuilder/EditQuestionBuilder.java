package com.example.android.animalsquiz.questionbuilder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.android.animalsquiz.QuestionData;
import com.example.android.animalsquiz.R;

public class EditQuestionBuilder extends QuestionBuilder {

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
    linearLayout.setTag(QuestionData.CHOICE_TYPE_EDIT);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    linearLayout.setLayoutParams(params);

    linearLayout.setOrientation(LinearLayout.VERTICAL);

    createQuestionTextView(linearLayout, questionData);
    createQuestionImage(linearLayout, questionData);
    createEditText(linearLayout, questionData);

    if (!isLastQuestion) {
      createQuestionDivider(linearLayout);
    }

    return(linearLayout);
  }

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // createEditText
  //---------------------------------------------------------------
  private void createEditText(LinearLayout linearLayout, QuestionData questionData) {
    EditText editText = new EditText(linearLayout.getContext());
    editText.setId(QuestionBuilder.EDIT_TEXT_TAG);
    editText.setTag(questionData.getCorrectChoiceStringIds());
    editText.setHint(R.string.edit_text_hint);

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    params.leftMargin = params.rightMargin = 16;
    editText.setLayoutParams(params);

    linearLayout.addView(editText);
  }
}
