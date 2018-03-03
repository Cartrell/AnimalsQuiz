package com.example.android.animalsquiz;

import android.view.View;
import android.view.ViewGroup;

/**
 * This class mixes up the order of all the child views in a view group.
 */
public class ChoicesMixer {
  //===============================================================
  // public
  //===============================================================

  //---------------------------------------------------------------
  // ctor
  //---------------------------------------------------------------
  /**
   * ChoicesMixer constructor.
   */
  public ChoicesMixer() {
  }

  //---------------------------------------------------------------
  // mix
  //---------------------------------------------------------------
  /**
   * Randomizes all the child views in a view group.
   * @param viewGroup The view group whose child views are to be randomized
   */
  public void mix(ViewGroup viewGroup) {
    /*
      Plan:
      1. Save all the child views to an array.
      2. Remove all the child views from the view group.
      3. Shuffle the order of child views in the array.
      4. Added all the child views back to the view group.
     */

    // 1. Save all the child views to an array.
    View[] views = createViewsArray(viewGroup);

    // 2. Remove all the child views from the view group.
    viewGroup.removeAllViews();

    // 3. Shuffle the order of child views in the array.
    randomizeViewsArray(views);

    // 4. Added all the child views back to the view group.
    reAddViews(viewGroup, views);
  }

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // createViewsArray
  //---------------------------------------------------------------
  /**
   * Adds all the child views of the view group into an array, and returns the array.
   * @param viewGroup the view group from which the array is created
   * @return View[] of View objects
  */
  private View[] createViewsArray(ViewGroup viewGroup) {
    //create an array with the same number of elements in it as there are child views in
    // the view group
    int childCount = viewGroup.getChildCount();
    View[] views = new View[childCount];

    //add the child views, to the array with this loop
    for (int childViewIndex = 0; childViewIndex < childCount; childViewIndex++){
      View view = viewGroup.getChildAt(childViewIndex);
      views[childViewIndex] = view;
    }

    //return the array
    return(views);
  }

  //---------------------------------------------------------------
  // randomizeViewsArray
  //---------------------------------------------------------------
  /**
   * Randomizes the order of the elements in the array of View objects. The array itself is
   * modified so there is no need to return anything.
   * @param views the array of View objects whose elements will be randomized
   */
  private void randomizeViewsArray(View[] views) {
    int numOfViews = views.length;
    for (int childViewIndex = 0; childViewIndex < numOfViews; childViewIndex++) {
      //pick a random index from the array
      int randomIndex = (int) (Math.random() * numOfViews);

      //swap the value at current index with the random index
      swapViewsAt(views, childViewIndex, randomIndex);
    }
  }

  //---------------------------------------------------------------
  // reAddViews
  //---------------------------------------------------------------
  /**
   * Adds the buttons back to the radio group.
   * @param viewGroup The view group that will have the randomized child views added to it
   * @param views the array of View objects whose elements will be added
   */
  private void reAddViews(ViewGroup viewGroup, View[] views) {
    //this "for each" loop goes thru each child view in the views array
    for (View view : views) {
      viewGroup.addView(view);
    }
  }

  //---------------------------------------------------------------
  // swapViewsAt
  //---------------------------------------------------------------
  /**
   * Swaps the data of two elements within the View array. The array itself is
   * modified so there is no need to return anything.
   * @param views the array of View objects whose elements will be swapped
   * @param index1 the index of the first element to swap
   * @param index2 the index of the second element to swap
   */
  private void swapViewsAt(View[] views, int index1, int index2) {

    //save the button at index1 because we're about to replace it with what's in index2
    View tempView = views[index1];

    //Place the button that is located at index2 into index1. This will overwrite the previous
    // button that was at index1, but remember: we saved it in the tempView variable first.
    views[index1] = views[index2];

    //Finally, set the temp radio button at the index2 location.
    views[index2] = tempView;
  }
}
