package com.aykuttasil.percentbar.util.adapter;

import android.content.Context;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDAdapter;
import com.aykuttasil.percentbar.PercentBarView;
import com.aykuttasil.percentbar.R;
import com.aykuttasil.percentbar.util.PicassoCircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aykutasil on 30.09.2016.
 */

public class MaterialListAdapter extends ArrayAdapter<Pair<PercentBarView.BarField, String>> implements MDAdapter {


    private MaterialDialog dialog;

    public MaterialListAdapter(Context context, List<Pair<PercentBarView.BarField, String>> modelFriendList) {
        super(context, R.layout.md_simplelist_item, android.R.id.title, modelFriendList);
    }

    @Override
    public void setDialog(MaterialDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(final int index, View convertView, ViewGroup parent) {
        final View view = super.getView(index, convertView, parent);
        if (dialog != null) {
            final Pair<PercentBarView.BarField, String> item = getItem(index);
            ImageView ic = (ImageView) view.findViewById(android.R.id.icon);

            Picasso.with(getContext())
                    .load(item.second)
                    .transform(new PicassoCircleTransform())
                    .into(ic);

            TextView tv = (TextView) view.findViewById(android.R.id.title);
            tv.setTextColor(dialog.getBuilder().getItemColor());
            tv.setText(item.second);
            dialog.setTypeface(tv, dialog.getBuilder().getRegularFont());
        }
        return view;
    }
}

