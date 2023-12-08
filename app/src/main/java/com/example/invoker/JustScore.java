package com.artem.invoker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.artem.invoker.R;
import com.artem.invoker.databinding.ActivityJustScoreBinding;
import com.artem.invoker.databinding.ActivityNewRecordBinding;

import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;

public class JustScore extends AppCompatActivity {

    ActivityJustScoreBinding binding;
    private SharedPreferences pref;

    private BannerAdView mBannerAdView;
    private static final String YANDEX_MOBILE_ADS_TAG = "YandexMobileAds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJustScoreBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = new Intent(this, Menu.class);
        Intent intent2 = new Intent(this, GameActivity.class);

        mBannerAdView = (BannerAdView) findViewById(R.id.banner_ad_view);
        mBannerAdView.setAdUnitId("R-M-2368551-3");


        // Создание объекта таргетирования рекламы.
        final AdRequest adRequest = new AdRequest.Builder().build();

        // Загрузка объявления.
        mBannerAdView.loadAd(adRequest);

        String score = getIntent().getStringExtra("score");

        pref = getSharedPreferences("APP_PREFERENCES", this.MODE_PRIVATE);

        binding.score.setText(score);

        binding.record.setText("Рекорд: "+pref.getString("APP_PREFERENCES_NAME", "0"));
        binding.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        binding.nextGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });
    }
}