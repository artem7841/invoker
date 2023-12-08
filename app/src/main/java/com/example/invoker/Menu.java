package com.artem.invoker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.artem.invoker.R;
import com.artem.invoker.databinding.ActivityMainBinding;
import com.artem.invoker.databinding.ActivityMenuBinding;
//import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.InitializationListener;
import com.yandex.mobile.ads.common.MobileAds;

public class Menu extends AppCompatActivity {

    @NonNull ActivityMenuBinding binding;
    private SharedPreferences pref;
    private BannerAdView mBannerAdView;
    private static final String YANDEX_MOBILE_ADS_TAG = "YandexMobileAds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(view);
        setContentView(scrollView);
        Intent intent = new Intent(this, MainActivity.class);
        Intent intent2 = new Intent(this, GameActivity.class);
        Intent intent3 = new Intent(Intent.ACTION_VIEW);
        intent3.setData(Uri.parse("https://steamcommunity.com/profiles/76561199094313342/"));

        MobileAds.initialize(this, new InitializationListener() {
            @Override
            public void onInitializationCompleted() {
                Log.d(YANDEX_MOBILE_ADS_TAG, "SDK initialized");
            }
        });

        mBannerAdView = (BannerAdView) findViewById(R.id.banner_ad_view);
        mBannerAdView.setAdUnitId("R-M-4113311-1");
    

        // Создание объекта таргетирования рекламы.
        final AdRequest adRequest = new AdRequest.Builder().build();

        // Загрузка объявления.
        mBannerAdView.loadAd(adRequest);


        pref = getSharedPreferences("APP_PREFERENCES", this.MODE_PRIVATE);

        binding.record.setText("Рекорд: " + pref.getString("APP_PREFERENCES_NAME", "Рекорд: 0"));

        binding.imageButton3 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent3);
            }
        });

        binding.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });
        binding.imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}