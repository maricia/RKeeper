package com.maricia.claim.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.maricia.claim.R;
import com.maricia.claim.model.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryPickerFragment extends Fragment {

  private RadioGroup categories;
  private TextView categoryLabel;


    public CategoryPickerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View picker = inflater.inflate(R.layout.fragment_category_picker, container, false);
        categories = (RadioGroup) picker.findViewById(R.id.categories);
        categoryLabel = (TextView)picker.findViewById(R.id.selected_category);
        categories.setOnCheckedChangeListener(new IconPickerWrapper(categoryLabel));
        categories.check(R.id.other);
        return picker;

    }

    public Category getSelectedCategory(){
        return Category.forIdResource(categories.getCheckedRadioButtonId());
    }

    public void setSelectedCategory(final Category category){
        categories.check(category.getIdResource());
    }

}
