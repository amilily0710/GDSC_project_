package com.example.gdsc_project.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gdsc_project.databinding.ActivitySignUpBinding
import com.example.gdsc_project.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class SignUpActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

//        binding.birthButton.setOnClickListener {
//            val calendar : Calendar = Calendar.getInstance()
//            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
//                dateString = "${year}년 ${month+1}월 ${dayOfMonth}일"
//                binding.textBirth.text = "BIRTH : $dateString"
//            }
//            DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show()
//        }


        binding.signBtn.setOnClickListener {
            createAccount(binding.editId.text.toString(), binding.editPw.text.toString(), binding.editName.text.toString(), binding.editAge.text.toString(),
            )
        }
    }


    private fun createAccount(email: String, password: String, name:String, age:String){
        if(email.isNotEmpty()&& password.isNotEmpty() &&name.isNotEmpty() &&age.isNotEmpty()){
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this){task ->
                    if(task.isSuccessful){
                        Log.d("createAc", "createUserWithEmail:success")
                        writeNewUser(email, name, age)
                        finish()
                    }else{
                        Log.w("createAc", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else{
            Toast.makeText(baseContext, "입력해주세요",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun writeNewUser(email: String,name:String, age:String) {
//        val user = User(email, name, age)
//        val key = auth.uid
//
//        if (key != null) {
//            database.child("users").child(key).setValue(user)
//        }
        val key = auth.uid
        val user = hashMapOf(
            "email" to email,
            "name" to name,
            "age" to age
        )

        if (key != null) {
            db.collection("users").document(key).set(user)
        }


    }
}