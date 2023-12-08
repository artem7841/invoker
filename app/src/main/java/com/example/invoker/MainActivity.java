package com.artem.invoker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import com.artem.invoker.R;
import com.artem.invoker.databinding.ActivityMainBinding;
//import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    String[] skills = new String[] {"q", "q", "w"};


    String[] temp = {"qqw", "eww", "eqw", "qqq", "www", "eeq", "eqq", "eew", "eee", "qww"};
    int rand;

    String[] skill_10 = new String[] {
            "Ghost wslk",
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
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


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

        binding.skill1.setAnimation(mEnlargeAnimation);
        binding.skill2.setAnimation(mEnlargeAnimation);




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
        long mills_true = 100;
        long[] mills_false = { 200, 100, 100 };

        Vibrator vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        String a = temp[rand];
        Arrays.sort(skills);
        String b = String.join("", (skills));
        if(Objects.equals(a, b)) {
            System.out.println(a  + b);
            rand = (int) (Math.random()*10);
            Set_image();
        } else {
            System.out.println(a  + b);
            System.out.println("no");
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(mills_true);
            }

        }
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