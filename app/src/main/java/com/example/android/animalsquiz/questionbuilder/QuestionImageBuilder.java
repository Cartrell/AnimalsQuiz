package com.example.android.animalsquiz.questionbuilder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.android.animalsquiz.QuestionData;

class QuestionImageBuilder {
  //===============================================================
  // package-private
  //===============================================================

  //---------------------------------------------------------------
  // build
  //---------------------------------------------------------------
  LinearLayout build(Context context, QuestionData questionData) {
    String questionImageId = questionData.getQuestionImageId();
    if (questionImageId == null) {
      //this question doesn't use a question image
      return(null);
    }

    LinearLayout imageLayout = new LinearLayout(context);
    imageLayout.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));

    createDividerView(imageLayout);
    createImageView(imageLayout, questionImageId);
    createDividerView(imageLayout);

    return(imageLayout);
  }

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // createDividerView
  //---------------------------------------------------------------
  private void createDividerView(LinearLayout imageLayout) {
    View dividerView = new View(imageLayout.getContext());
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    params.weight = 2;
    dividerView.setLayoutParams(params);
    imageLayout.addView(dividerView);
  }

  //---------------------------------------------------------------
  // createImageView
  //---------------------------------------------------------------
  private void createImageView(LinearLayout imageLayout, String questionImageId) {
    Context context = imageLayout.getContext();
    Resources resources = context.getResources();

    int imageResource = resources.getIdentifier(questionImageId, "drawable",
            context.getPackageName());
    if (imageResource == 0) {
      return; //sanity check
    }

    ImageView imageView = new ImageView(context);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    params.weight = 2;
    imageView.setLayoutParams(params);
    imageView.setAdjustViewBounds(true);

    Drawable drawable = resources.getDrawable(imageResource);
    imageView.setImageDrawable(drawable);
    imageLayout.addView(imageView);
  }
}