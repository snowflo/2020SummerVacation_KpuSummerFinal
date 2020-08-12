package kr.ac.kpu.kpusummerwater.sampledata.ui.review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kr.ac.kpu.kpusummerwater.R
import kotlin.math.sign

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signUp.setOnClickListener(){
            loginEmail()
        }
        makeAccount.setOnClickListener(){
            createEmailId()
            editLoginId.text = null
            editLoginPassword.text = null
        }
        logout.setOnClickListener(){
            logoutEmail()
            editLoginId.text = null
            editLoginPassword.text = null
        }
    }

    fun moveNextPage(){
        var currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser==null){
            Toast.makeText(this, "로그인을 해주세요", Toast.LENGTH_SHORT).show()
        }
        else{
            startActivity(Intent(this,Review::class.java))
        }
    }

    fun loginEmail(){
        var email = editLoginId.text.toString()
        var password = editLoginPassword.text.toString()

        if(email!=null && password!=null) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        moveNextPage()
                    }
                }
        }else{
            Toast.makeText(this, "로그인을 해주세요", Toast.LENGTH_SHORT).show()
        }

        //토스트?
    }

    fun createEmailId(){
        var email = editLoginId.text.toString()
        var password = editLoginPassword.text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                moveNextPage()
            }
        }

        //토스트?
    }

    fun logoutEmail(){
        FirebaseAuth.getInstance().signOut()

        //토스트?
    }
}