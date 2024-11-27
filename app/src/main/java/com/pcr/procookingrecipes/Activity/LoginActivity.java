package com.pcr.procookingrecipes.Activity;

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

    // Declarar el ActivityResultLauncher
    private final ActivityResultLauncher<Intent> googleSignInResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(TAG, "onActivityResult: Result received.");
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult();
                        if (account != null) {
                            // Usar el token de Google para autenticar al usuario en Firebase
                            Log.d(TAG, "onActivityResult: Google Sign-In success.");
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
                .requestIdToken(getString(R.string.default_web_client_id)) // Asegúrate de configurar esto en tu Firebase Console
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Referencias a vistas
        usuarioEditText = findViewById(R.id.usuarioEditText);
        passEditText = findViewById(R.id.passEditText);
        botonEntrar = findViewById(R.id.botonEntrar);
        botonRegistrar = findViewById(R.id.botonRegistrar);
        botonGoogle = findViewById(R.id.botonGoogle);
        olvideContrasenaTextView = findViewById(R.id.olvideContrasenaTextView);

        // Manejo de botones
        botonEntrar.setOnClickListener(view -> iniciarSesion());
        botonRegistrar.setOnClickListener(view -> registrarUsuario());
        botonGoogle.setOnClickListener(view -> iniciarSesionConGoogle());
        olvideContrasenaTextView.setOnClickListener(view -> recuperarContrasena());

        Log.d(TAG, "onCreate: Activity created.");
    }

    private void iniciarSesion() {
        Log.d(TAG, "iniciarSesion: Iniciando sesión con correo y contraseña.");
        String email = usuarioEditText.getText().toString().trim();
        String password = passEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

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
        Log.d(TAG, "registrarUsuario: Registrando nuevo usuario.");
        String email = usuarioEditText.getText().toString().trim();
        String password = passEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

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
        Log.d(TAG, "recuperarContrasena: Recuperando contraseña.");
        String email = usuarioEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Ingresa tu correo para recuperar la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

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
        Log.d(TAG, "iniciarSesionConGoogle: Iniciando sesión con Google.");
        Intent signInIntent = googleSignInClient.getSignInIntent();
        googleSignInResultLauncher.launch(signInIntent); // Usa el launcher para lanzar la actividad
    }

    private void abrirMainActivity() {
        Log.d(TAG, "abrirMainActivity: Navegando a MainActivity.");
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
