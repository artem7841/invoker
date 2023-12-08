package com.artem.invoker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.artem.invoker.R;
import com.artem.invoker.databinding.ActivityMenuBinding;
import com.artem.invoker.databinding.ActivityNewRecordBinding;
//import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.InitializationListener;
import com.yandex.mobile.ads.common.MobileAds;

public class NewRecord extends AppCompatActivity {

    ActivityNewRecordBinding binding;

    private SharedPreferences pref;
    private BannerAdView mBannerAdView;
    private static final String YANDEX_MOBILE_ADS_TAG = "YandexMobileAds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewRecordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = new Intent(this, Menu.class);
        Intent intent2 = new Intent(this, GameActivity.class);

        MobileAds.initialize(this, new InitializationListener() {
            @Override
            public void onInitializationCompleted() {
                Log.d(YANDEX_MOBILE_ADS_TAG, "SDK initialized");
            }
        });

        mBannerAdView = (BannerAdView) findViewById(R.id.banner_ad_view);
        mBannerAdView.setAdUnitId("R-M-4113311-2");
        //mBannerAdView.setAdSize(AdSize.stickySize(1000));

        // Создание объекта таргетирования рекламы.
        final AdRequest adRequest = new AdRequest.Builder().build();

        // Загрузка объявления.
        mBannerAdView.loadAd(adRequest);



        pref = getSharedPreferences("APP_PREFERENCES", this.MODE_PRIVATE);

        if(pref.contains("APP_PREFERENCES_NAME")) {
            binding.score.setText(pref.getString("APP_PREFERENCES_NAME", ""));
        } else {
            System.out.println("dsdsds");
        }

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