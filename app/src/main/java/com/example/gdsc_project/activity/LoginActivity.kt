package com.example.gdsc_project.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gdsc_project.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        binding.apply {
            loginBtn.setOnClickListener {
                if(loginId.text.toString().isNotEmpty() && loginPw.text.toString().isNotEmpty()){
                    signIn(loginId.text.toString(),loginPw.text.toString())
                }
            }
            signUpBtn.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun signIn(email: String, password:String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){task->
                if(task.isSuccessful){
                    Log.d("SignIn", "signInWithEmail:success")
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Log.w("SignIn", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "비밀번호가 틀렸습니다!",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
