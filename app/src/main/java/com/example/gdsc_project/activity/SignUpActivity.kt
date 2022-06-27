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
import com.google.firebase.ktx.Firebase
import java.util.*

class SignUpActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val database = Firebase.database.reference
    var dateString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.birthButton.setOnClickListener {
            val calendar : Calendar = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateString = "${year}년 ${month+1}월 ${dayOfMonth}일"
                binding.textBirth.text = "BIRTH : $dateString"
            }
            DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show()
        }


        binding.signBtn.setOnClickListener {
            createAccount(binding.editName.text.toString(), binding.editAge.text.toString(), binding.editLocation.text.toString(),
                dateString,binding.editId.text.toString(),binding.editPw.text.toString())
        }
    }


    private fun createAccount(name:String, age:String, location:String, birth:String, email: String, password: String){
       if(name.isNotEmpty() && age.isNotEmpty() && location.isNotEmpty() && birth.length >6 &&email.isNotEmpty()&& password.isNotEmpty()){
           auth?.createUserWithEmailAndPassword(email, password)
               ?.addOnCompleteListener(this){task ->
                   if(task.isSuccessful){
                       Log.d("createAc", "createUserWithEmail:success")
                       writeNewUser(name, age, location, birth, email)
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

    private fun writeNewUser(name:String, age:String, location:String, birth:String, email: String) {
        val user = User(name, age, location, birth)
        var tmp = email
        val charsToRemove = "@."
        charsToRemove.forEach { tmp = tmp.replace(it.toString(), "") }
        println(tmp)
        database.child("users").child(tmp).setValue(user)
    }
}