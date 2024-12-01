package com.pcr.procookingrecipes.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.pcr.procookingrecipes.ConexionAPI.SecurePreferences;
import com.pcr.procookingrecipes.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;

    private EditText usuarioEditText, passEditText;
    private Button botonEntrar, botonRegistrar;
    private ImageButton botonGoogle;
    private TextView olvideContrasenaTextView;

    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;

    // Declarar el ActivityResultLauncher para Google Sign-In y Firebase Authentication
    private final ActivityResultLauncher<Intent> googleSignInResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                // Manejar el resultado del ActivityResult
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Verificar si el resultado es OK
                    if (result.getResultCode() == RESULT_OK) {
                        // Obtener la cuenta de Google
                        Intent data = result.getData();
                        GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult();
                        if (account != null) {
                            // Usar el token de Google para autenticar al usuario en Firebase
                            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                            auth.signInWithCredential(credential)
                                    .addOnCompleteListener(LoginActivity.this, authTask -> {
                                        if (authTask.isSuccessful()) {
                                            abrirMainActivity();
                                        } else {
                                            Log.e(TAG, "Error: " + authTask.getException().getMessage());
                                            Toast.makeText(LoginActivity.this, "Error al iniciar sesión con Google", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Log.e(TAG, "onActivityResult: Google Account is null.");
                        }
                    } else {
                        Log.e(TAG, "onActivityResult: Result code is not OK.");
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Configurar Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Crear el cliente de Google Sign-In
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Referencias a vistas
        usuarioEditText = findViewById(R.id.usuarioEditText);
        passEditText = findViewById(R.id.passEditText);
        botonEntrar = findViewById(R.id.botonEntrar);
        botonRegistrar = findViewById(R.id.botonRegistrar);
        botonGoogle = findViewById(R.id.botonGoogle);
        olvideContrasenaTextView = findViewById(R.id.olvideContrasenaTextView);

        usuarioEditText.setText("pepe@gmail.com");
        passEditText.setText("pepepepe");

        // Manejo de botones
        botonEntrar.setOnClickListener(view -> iniciarSesion());
        botonRegistrar.setOnClickListener(view -> registrarUsuario());
        botonGoogle.setOnClickListener(view -> iniciarSesionConGoogle());
        olvideContrasenaTextView.setOnClickListener(view -> recuperarContrasena());
    }

    private void iniciarSesion() {
        // Obtener datos de usuario y contraseña
        String email = usuarioEditText.getText().toString().trim();
        String password = passEditText.getText().toString().trim();

        // Verificar si los campos están vacíos
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Iniciar sesión con Firebase Auth usando el email y la contraseña
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        abrirMainActivity();
                    } else {
                        Log.e(TAG, "Error: " + task.getException().getMessage());
                        Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void registrarUsuario() {
        // Obtener datos de usuario y contraseña
        String email = usuarioEditText.getText().toString().trim();
        String password = passEditText.getText().toString().trim();

        // Verificar si los campos están vacíos
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Registrar usuario con Firebase Auth
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        abrirMainActivity();
                    } else {
                        Log.e(TAG, "Error: " + task.getException().getMessage());
                        Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void recuperarContrasena() {
        // Obtener el email del usuario
        String email = usuarioEditText.getText().toString().trim();

        // Verificar si el campo está vacío
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Ingresa tu correo para recuperar la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        // Enviar correo de recuperación
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Correo de recuperación enviado", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "Error: " + task.getException().getMessage());
                        Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void iniciarSesionConGoogle() {
        // Iniciar sesión con Google
        Intent signInIntent = googleSignInClient.getSignInIntent();
        googleSignInResultLauncher.launch(signInIntent); // Usar el launcher para lanzar la actividad
    }

    private void abrirMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
