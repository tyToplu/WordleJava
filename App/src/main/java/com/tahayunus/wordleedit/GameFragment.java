package com.tahayunus.wordleedit;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidanimations.library.flippers.FlipInXAnimator;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.tahayunus.wordleedit.data.Data;
import com.tahayunus.wordleedit.databinding.FragmentGameBinding;
import com.tahayunus.wordleedit.databinding.StatisticsLayoutBinding;
import com.tahayunus.wordleedit.gamefragmenthelper.*;
import com.tahayunus.wordleedit.util.Animation;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class GameFragment extends Fragment {

    private String EMPTY_STRING = "";
    private int NUMBER_OF_ROWS = 6;
    String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int ALPHABET_LENGTH = 26;
    private Letter DEFAULT_LETTER = new Letter(" ", R.drawable.border, R.color.black);
    private Key DEFAULT_KEY = new Key(R.color.gray, R.color.black);
    private Position currentPos = new Position(0, 0);
    private List<List<TextView>> listOfTextViews;
    private FragmentGameBinding binding;
    private Stack<String> letter_garbage = new Stack<>();
    private String guess = "";
    private String word;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;

    // view model is scoped to fragment.
    public GameFragment() {
        Random rand = new Random(System.currentTimeMillis());
        int r = rand.nextInt(Data.getWordLength());
        int wordSlice = r * Data.WORD_LENGTH;
        word = Data.getWordList().substring(wordSlice,wordSlice+5);
        word = "ahmet";

        // Required empty public constructor
         }

    private void logOut() {
        FragmentManager fragmentManager = getParentFragmentManager();

        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                fragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                        new EnterancePage()).commit();
            }
        });
    }
    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        //Bundle args = new Bundle();
        //    args.putString(ARG_PARAM1, param1);
        //  args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //  mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(getActivity(), gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());

        if(account != null){
            // griis yapilmissa ilave bir sey yapacaksan...
        }
        binding = FragmentGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentManager fragmentManager = getParentFragmentManager();
        if(item.getItemId() == R.id.log_out){
            logOut();
            fragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                    new EnterancePage()).commit();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listOfTextViews = Arrays.asList(
                Arrays.asList(
                        binding.firstRow1,
                        binding.firstRow2,
                        binding.firstRow3,
                        binding.firstRow4,
                        binding.firstRow5
                ),
                Arrays.asList(
                        binding.secondRow1,
                        binding.secondRow2,
                        binding.secondRow3,
                        binding.secondRow4,
                        binding.secondRow5
                ),
                Arrays.asList(
                        binding.thirdRow1,
                        binding.thirdRow2,
                        binding.thirdRow3,
                        binding.thirdRow4,
                        binding.thirdRow5
                ),
                Arrays.asList(
                        binding.fourthRow1,
                        binding.fourthRow2,
                        binding.fourthRow3,
                        binding.fourthRow4,
                        binding.fourthRow5
                ),
                Arrays.asList(
                        binding.fifthRow1,
                        binding.fifthRow2,
                        binding.fifthRow3,
                        binding.fifthRow4,
                        binding.fifthRow5
                ),
                Arrays.asList(
                        binding.sixthRow1,
                        binding.sixthRow2,
                        binding.sixthRow3,
                        binding.sixthRow4,
                        binding.sixthRow5
                )
        );
        List<LinearLayout> lettersRow = Arrays.asList(
                binding.firstLettersRow,
                binding.secondLettersRow,
                binding.thirdLettersRow,
                binding.fourthLettersRow,
                binding.fifthLettersRow,
                binding.sixthLettersRow
        );

        Map<String, Button> makeQKeyboard = new HashMap<>();

        makeQKeyboard.put("A", binding.A);
        makeQKeyboard.put("B", binding.B);
        makeQKeyboard.put("C", binding.C);
        makeQKeyboard.put("D", binding.D);
        makeQKeyboard.put("E", binding.E);
        makeQKeyboard.put("F", binding.F);
        makeQKeyboard.put("G", binding.G);
        makeQKeyboard.put("H", binding.H);
        makeQKeyboard.put("I", binding.I);
        makeQKeyboard.put("J", binding.J);
        makeQKeyboard.put("K", binding.K);
        makeQKeyboard.put("L", binding.L);
        makeQKeyboard.put("M", binding.M);
        makeQKeyboard.put("N", binding.N);
        makeQKeyboard.put("O", binding.O);
        makeQKeyboard.put("P", binding.P);
        makeQKeyboard.put("Q", binding.Q);
        makeQKeyboard.put("R", binding.RR);
        makeQKeyboard.put("S", binding.S);
        makeQKeyboard.put("T", binding.T);
        makeQKeyboard.put("U", binding.U);
        makeQKeyboard.put("V", binding.V);
        makeQKeyboard.put("W", binding.W);
        makeQKeyboard.put("X", binding.X);
        makeQKeyboard.put("Y", binding.Y);
        makeQKeyboard.put("Z", binding.Z);
        makeQKeyboard.forEach((letter, button) ->
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setLetter(letter);
                    }
                })
        );
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLetter();
            }
        });
        binding.enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRow();
            }
        });
    }
    void setLetter(String letter) {
        if (currentPos.col < 5) {

            listOfTextViews.get(currentPos.row).get(currentPos.col).setText(letter);
            currentPos.nextColumn();
            letter_garbage.add(letter);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);

        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_logout,menu);

    }

    void deleteLetter() {
        if (currentPos.col > 0) {
            currentPos.previousColumn();
            listOfTextViews.get(currentPos.row).get(currentPos.col).setText("");
        }
        if(currentPos.col == 0)
            listOfTextViews.get(currentPos.row).get(currentPos.col).setText("");
    }
    void checkRow(){
            for (int i = 0; i< 5 ; i++)
                guess += listOfTextViews.get(currentPos.row).get(i).getText().
                        toString().toLowerCase(Locale.ROOT).trim();
            {
            if(guess.length() < 5){
                binding.info.setText("Not enough letters");
            }else if(word.equals(guess)){
                //shakeAnimation();
                flipAnimation();
                switch (currentPos.row){
                    case 0 : binding.info.setText("Genius");
                    case 1 : binding.info.setText("Magnificent");
                    case 2 :binding.info.setText("Impressive");
                    case 3 : binding.info.setText("Splendid");
                    case 4 : binding.info.setText("Great");
                    case 5 : binding.info.setText("Phew");
                }
                shakeAnimation();
                bindDialog();
                // animation
            } else if (currentPos.row <= 5 && guess.length() == 5){
                //shakeAnimation();
                flipAnimation();
                if(currentPos.row < 5)
                   currentPos.nextRow();
                else{
                    binding.info.setText("Game Ended");
                    bindDialog();
                }
                //animation
            }
            guess = "";
        }
    }
    private void flipAnimation(){
        Animation.rotate90Degrees(listOfTextViews.get(currentPos.row).get(0),0,word,0);
        Animation.rotate90Degrees(listOfTextViews.get(currentPos.row).get(1),300,word,1);
        Animation.rotate90Degrees(listOfTextViews.get(currentPos.row).get(2),600,word,2);
        Animation.rotate90Degrees(listOfTextViews.get(currentPos.row).get(3),900,word,3);
        Animation.rotate90Degrees(listOfTextViews.get(currentPos.row).get(4),1200,word,4);

    }

    private void shakeAnimation(){
        YoYo.with(Techniques.Wobble).duration(350).
                repeat(1).playOn(listOfTextViews.get(currentPos.row).get(0));
        YoYo.with(Techniques.Wobble).duration(350).
                repeat(1).playOn(listOfTextViews.get(currentPos.row).get(1));
        YoYo.with(Techniques.Wobble).duration(350).
                repeat(1).playOn(listOfTextViews.get(currentPos.row).get(2));
        YoYo.with(Techniques.Wobble).duration(350).
                repeat(1).playOn(listOfTextViews.get(currentPos.row).get(3));
        YoYo.with(Techniques.Wobble).duration(350).
                repeat(1).playOn(listOfTextViews.get(currentPos.row).get(4));
    }
    private void bindDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        StatisticsLayoutBinding binding = StatisticsLayoutBinding.inflate(getLayoutInflater());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        new CountDownTimer(1500,1000) {
            @Override
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                AlertDialog dialog = builder.setView(inflater.inflate(R.layout.statistics_layout, null))
                        .setNegativeButton(R.string.next, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                resetGame();

                            }
                        }).show();
            }
        }.start();
    }
    private void resetGame(){
        GameFragment instanceFragment = newInstance();
        FragmentTransaction tr = getFragmentManager().beginTransaction();
        tr.replace(R.id.fragment_container_view, instanceFragment);
        tr.commit();
    }
}