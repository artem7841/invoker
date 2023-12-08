package com.artem.invoker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.artem.invoker.R;
import com.artem.invoker.databinding.ActivityGameBinding;
import com.artem.invoker.databinding.ActivityMainBinding;
import com.artem.invoker.databinding.ActivityMenuBinding;

import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.InitializationListener;
import com.yandex.mobile.ads.common.MobileAds;

import java.util.Arrays;
import java.util.Objects;

public class GameActivity extends AppCompatActivity {

    ActivityGameBinding binding;

    String[] skills = new String[] {"q", "q", "w"};

    private SharedPreferences pref;
    String[] temp = {"qqw", "eww", "eqw", "qqq", "www", "eeq", "eqq", "eew", "eee", "qww"};
    int rand;
    int lives;
    int score = 0;
    CountDownTimer timer;
    String[] skill_10 = new String[] {
            "Ghost walk",
            "Alacrity",
            "Deafening Blast",
            "Cold snap",
            "E.M.P",
            "Forge spirit",
            "Ice wall",
            "Chaos meteor",
            "Sun strike",
            "Torbado"
    };
    int[] skill_12 = new int[] {
            R.drawable.ghostwalk,
            R.drawable.alacrity,
            R.drawable.blast,
            R.drawable.coldsnap,
            R.drawable.emp,
            R.drawable.forgespirit,
            R.drawable.icewall,
            R.drawable.chaosmeteor,
            R.drawable.sunstrike,
            R.drawable.tornado
    };


    private Animation mEnlargeAnimation, mShrinkAnimation;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(this, Menu.class);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("df");
            timer.cancel();
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        lives = 3;

        pref = getSharedPreferences("APP_PREFERENCES", this.MODE_PRIVATE);


        Update_skills();
        rand = (int) (Math.random()*10);

        Set_image();

        Animation.AnimationListener animationEnlargeListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.skill1.startAnimation(mShrinkAnimation);
                binding.skill2.startAnimation(mShrinkAnimation);
                binding.skill3.startAnimation(mShrinkAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        Animation.AnimationListener animationShrinkListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                // когда анимация уменьшения заканчивается,
                // то начинаем анимацию увеличения
                binding.skill1.startAnimation(mEnlargeAnimation);
                binding.skill2.startAnimation(mEnlargeAnimation);
                binding.skill3.startAnimation(mEnlargeAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        };

        mEnlargeAnimation = AnimationUtils.loadAnimation(this, R.anim.enlarge);
        mShrinkAnimation = AnimationUtils.loadAnimation(this, R.anim.shrink);
        mEnlargeAnimation.setAnimationListener(animationEnlargeListener);
        mShrinkAnimation.setAnimationListener(animationShrinkListener);

        Intent New_score = new Intent(this, NewRecord.class);
        Intent Just_score = new Intent(this, JustScore.class);

        binding.score.setText(String.valueOf(score));

        binding.skill1.setAnimation(mEnlargeAnimation);
        binding.skill2.setAnimation(mEnlargeAnimation);

        timer  = new CountDownTimer(30 * 1000, 1000) {

            public void onTick(long millisUntilFinished) {

                long seconds = (millisUntilFinished / 1000) % 60;
                long minutes = (millisUntilFinished / 1000) / 60;
                if (seconds < 10) {
                    if (minutes < 10) {
                        binding.timer.setText("0" + minutes + ":0" + seconds);
                    } else {
                        binding.timer.setText("" + minutes + ":0" + seconds);
                    }

                } else {
                    if (minutes < 10) {
                        binding.timer.setText("0" + minutes + ":" + seconds);
                    } else {
                        binding.timer.setText("" + minutes + ":" + seconds);
                    }
                }
            }
            public void onFinish() {
                if(lives != 0) {
                    if (score > Integer.parseInt(pref.getString("APP_PREFERENCES_NAME", "0"))) {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("APP_PREFERENCES_NAME", String.valueOf(score));
                        editor.apply();
                        New_score.putExtra("score", String.valueOf(score));
                        stop_timer(timer);
                        startActivity(New_score);
                    } else {
                        stop_timer(timer);
                        Just_score.putExtra("score", String.valueOf(score));
                        startActivity(Just_score);
                    }
                }

            };


        };

        timer.start();

        binding.exort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                New_skill("e");
                System.out.println(skills[0] + skills[1] + skills[2]);
                Update_skills();
            }
        });
        binding.quas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                New_skill("q");
                System.out.println(skills[0] + skills[1] + skills[2]);
                Update_skills();
            }
        });
        binding.wex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                New_skill("w");
                System.out.println(skills[0] + skills[1] + skills[2]);
                Update_skills();
            }
        });

        binding.invoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoke();
            }
        });
    }

    public void Set_image() {
        binding.nameSkill.setText(skill_10[rand]);
        binding.imageView.setImageDrawable(getDrawable(skill_12[rand]));
    }

    public void invoke() {
        Intent New_score = new Intent(this, NewRecord.class);
        Intent Just_score = new Intent(this, JustScore.class);
        String a = temp[rand];
        Arrays.sort(skills);
        String b = String.join("", (skills));
        long mills_true = 100;
        long[] mills_false = { 200, 100, 100 };

        Vibrator vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        if(Objects.equals(a, b)) {
            rand = (int) (Math.random()*10);
            Set_image();
            score++;
            binding.score.setText(String.valueOf(score));


        } else {

            if (vibrator.hasVibrator()) {
                vibrator.vibrate(mills_true);
            }
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("APP_PREFERENCES_NAME", String.valueOf(score));
            lives--;
            switch (lives) {
                case 0:
                    binding.heart1.setImageDrawable(getResources().getDrawable(R.drawable.heart_empty));
                    if (score  > Integer.parseInt(pref.getString("APP_PREFERENCES_NAME", "0"))) {
                        editor.apply();
                        stop_timer(timer);
                        Just_score.putExtra("score", String.valueOf(score));
                        startActivity(New_score);
                    } else {
                        stop_timer(timer);
                        Just_score.putExtra("score", String.valueOf(score));
                        startActivity(Just_score);
                    }
                    break;
                case 2:
                    binding.heart3.setImageDrawable(getResources().getDrawable(R.drawable.heart_empty));
                    break;
                case 1:
                    binding.heart2.setImageDrawable(getResources().getDrawable(R.drawable.heart_empty));
                    break;

            }

            rand = (int) (Math.random()*10);
            Set_image();

        }
    }

    public void stop_timer(CountDownTimer t) {
        t.cancel();
    }

    public void New_skill(String skill) {
        switch (skill) {
            case ("e"):
                skills[0] = skills[1];
                skills[1] = skills[2];
                skills[2] = "e";

                break;
            case ("w"):
                skills[0] = skills[1];
                skills[1] = skills[2];
                skills[2] = "w";
                System.out.println(skills[0] + skills[1] + skills[2]);
                break;
            case ("q"):
                skills[0] = skills[1];
                skills[1] = skills[2];
                skills[2] = "q";
                System.out.println(skills[0] + skills[1] + skills[2]);
                break;
        }

    }

    public void Update_skills() {

        switch (skills[0]) {
            case (""):
                binding.skill1.setVisibility(View.GONE);
                break;
            case("e"):
                binding.skill1.setVisibility(View.VISIBLE);
                binding.skill1.setImageDrawable(getDrawable(R.drawable.red));
                break;
            case("w"):
                binding.skill1.setVisibility(View.VISIBLE);
                binding.skill1.setImageDrawable(getDrawable(R.drawable.perpl));
                break;
            case("q"):
                binding.skill1.setVisibility(View.VISIBLE);
                binding.skill1.setImageDrawable(getDrawable(R.drawable.blue));
                break;
        }

        switch (skills[1]) {
            case (""):
                binding.skill2.setVisibility(View.GONE);
                break;
            case("e"):
                binding.skill2.setVisibility(View.VISIBLE);
                binding.skill2.setImageDrawable(getDrawable(R.drawable.red));
                break;
            case("w"):
                binding.skill2.setVisibility(View.VISIBLE);
                binding.skill2.setImageDrawable(getDrawable(R.drawable.perpl));
                break;
            case("q"):
                binding.skill2.setVisibility(View.VISIBLE);
                binding.skill2.setImageDrawable(getDrawable(R.drawable.blue));
                break;

        }

        switch (skills[2]) {
            case (""):
                binding.skill3.setVisibility(View.GONE);
                break;
            case("e"):
                binding.skill3.setVisibility(View.VISIBLE);
                binding.skill3.setImageDrawable(getDrawable(R.drawable.red));
                break;
            case("w"):
                binding.skill3.setVisibility(View.VISIBLE);
                binding.skill3.setImageDrawable(getDrawable(R.drawable.perpl));
                break;
            case("q"):
                binding.skill3.setVisibility(View.VISIBLE);
                binding.skill3.setImageDrawable(getDrawable(R.drawable.blue));
                break;
        }



    }
}