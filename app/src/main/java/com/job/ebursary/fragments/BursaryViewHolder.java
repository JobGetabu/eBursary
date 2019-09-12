package com.job.ebursary.fragments;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Job on Sunday : 6/24/2018.
 */
public class BursaryViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private String transId;


    public BursaryViewHolder(@NonNull View itemView) {
        super(itemView);

    }

    public void init(Context context, String transId) {
        this.context = context;
        this.transId = transId;

    }
}
