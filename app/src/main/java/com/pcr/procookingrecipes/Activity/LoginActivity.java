package com.pcr.procookingrecipes.Activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pcr.procookingrecipes.databinding.ActivityLoginBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity{
    private static final String BD_URL = "jdbc:mysql://localhost:3306/MySQLPCR";

    private ActivityLoginBinding binding;
    private GoogleSignInClient mGoogleSignInClient;
    Button logInButton;
    TextView textView;
    private FirebaseFirestore db;
    private boolean usuarioExiste = false;
    String mail;
    String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textView = binding.C;
        // Inicializa Firestore
        db = FirebaseFirestore.getInstance();
        // Configura Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.button.setOnClickListener(view -> signIn());
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 9001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 9001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                textView.setText("OLE");
                mail = account.getEmail();
                nombre = account.getDisplayName();


                Map<String, Object> user = new HashMap<>();
                user.put("mail", mail);
                user.put("nombre", nombre);

                //Leer de la base de datos si existe el usuario
                db.collection("usuarios")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // comparar el mail con los mail de la bbdd
                                        if (document.getString("mail").equals(account.getEmail())) {
                                            usuarioExiste = true;
                                            abrirMainActivity();
                                        }
                                    }
                                    if (!usuarioExiste){
                                        db.collection("usuarios")
                                                .add(user)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        abrirMainActivity();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error añadiendo registro de usuario.", e);
                                                    }
                                                });
                                    }
                                } else {
                                    Log.w(TAG, "Error leyendo de la base de datos.", task.getException());
                                }
                            }
                        });
            }
        } catch (ApiException e) {
            textView.setText("Falló el inicio de sesión con Google");
        }
    }

    private void abrirMainActivity() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("userEmail", mail);
        intent.putExtra("userName", nombre);
        startActivity(intent);
        finish();
    }


}


