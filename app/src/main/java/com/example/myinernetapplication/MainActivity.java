package com.example.myinernetapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String LOG = "MyLogMain";
    private ViewModelMainActivity viewModel;
    ImageView imageDog;
    Button buttonNextDog;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tototo();

        viewModel = new ViewModelProvider(this).get(ViewModelMainActivity.class);
        viewModel.loadDogeImage();
        viewModel.getIsLoading().observe(
                this,
                isLoad -> {
                    if(isLoad)
                        progressBar.setVisibility(View.VISIBLE);
                    else
                        progressBar.setVisibility(View.GONE);
                }
                );
        viewModel.getMutableLiveData().observe(
                this,
                dogImage -> {
                    Log.d(LOG, dogImage.toString());
                    Glide.with(MainActivity.this)
                            .load(dogImage.getMessage())
                            .into(imageDog);
                });
        viewModel.getErrButton().observe(this, err -> {
            if(err) Toast.makeText(
                    getApplicationContext(),
                    "Ошибкааааа",
                    Toast.LENGTH_SHORT
            ).show();
        });
        buttonNextDog.setOnClickListener(v -> {
            viewModel.loadDogeImage();
        });
    }
    private void tototo(){
        imageDog = findViewById(R.id.imageDog);
        buttonNextDog = findViewById(R.id.nextButton);
        progressBar = findViewById(R.id.progressBar);
    }
}