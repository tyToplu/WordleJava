package com.tahayunus.wordleedit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.tahayunus.wordleedit.databinding.FragmentEnterancePageBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EnterancePage#newInstance} factory method to
 * create an instance of this fragment.
 */

public class EnterancePage extends Fragment {
    private static final int RC_SIGN_IN = 100;
    private FragmentEnterancePageBinding binding;
    FragmentManager fragmentManager;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;

    public EnterancePage() {
        // Required empty public constructor


    }
    public void SignIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            OtherActivity();
        }
    }

    private void OtherActivity() {
        // navigate to enterance page
        fragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                new GameFragment()).commit();
    }


    public static EnterancePage newInstance() {
        EnterancePage fragment = new EnterancePage();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        if(getActivity() == null)
            System.out.println("null");
        gsc = GoogleSignIn.getClient(getActivity(), gso);

        binding = FragmentEnterancePageBinding.inflate(inflater, container, false);
        fragmentManager = getParentFragmentManager();
        View view = binding.getRoot();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());

        if(account != null){
            // griis yapilmissa ilave bir sey yapacaksan...
            fragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                    new GameFragment()).commit();
        }
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.fragment_container_view,
                        new GameFragment()).commit();
            }
        });
        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}