package com.tahayunus.wordleedit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.tahayunus.wordleedit.data.Data;
import com.tahayunus.wordleedit.databinding.FragmentGameBinding;
import com.tahayunus.wordleedit.databinding.StatisticsLayoutBinding;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class DistributionView extends Fragment {
    StatisticsLayoutBinding binding;
    HorizontalBarChart mChart;
    public DistributionView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = StatisticsLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mChart = getActivity().findViewById(R.id.bar_chart);


        setData(12, 50);

    }
    private void setData(int count, int range){
        ArrayList<BarEntry> yVals = new ArrayList<>();
        float barWidth = 9f;
        float spaceForBar = 10f;
        for (int i = 0; i< count ; i++) {
            float val = (float)Math.random()*range;
            yVals.add(new BarEntry(i * spaceForBar, val));
        }
        BarDataSet set1;
        set1 = new BarDataSet(yVals, "Data Set1");
        BarData data = new BarData(set1);
        //data.setBarWidth(barWidth);
        mChart.setData(data);
    }

}

