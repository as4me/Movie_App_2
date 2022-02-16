package com.as4me.movieapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Sign_up_screen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)

        auth = Firebase.auth

        val edit_text_email: EditText = findViewById(R.id.email_input)
        val edit_text_password: EditText = findViewById(R.id.password_input)
        val signupbutton: Button = findViewById(R.id.signupbutton)
        val loginbutton: Button = findViewById(R.id.loginbutton)


        signupbutton.setOnClickListener {

            val email = edit_text_email.text.toString()
            val password = edit_text_password.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Log.d("MovieApp", "createUserWithEmail:success")
                        val user = auth.currentUser

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("MovieApp", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }

        }

        loginbutton.setOnClickListener {
            val email = edit_text_email.text.toString()
            val password = edit_text_password.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("MovieApp", "signinUserWithEmail:success")
                        val user = auth.currentUser

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("MovieApp", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }

        }



    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
           // TO-DO
        }
    }
}