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
        signUp.setOnClickListener{
            loginEmail()
        }
        makeAccount.setOnClickListener{
            createEmailId()
            editLoginId.text = null
            editLoginPassword.text = null
        }
        logout.setOnClickListener{
            logoutEmail()
            editLoginId.text = null
            editLoginPassword.text = null
        }
    }

    fun moveNextPage(){
        var currentUser = FirebaseAuth.getInstance().currentUser
            startActivity(Intent(this,Review::class.java))
    }

    fun loginEmail(){
        var email = editLoginId.text.toString()
        var password = editLoginPassword.text.toString()

        try {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        moveNextPage()
                    }
                }
        }catch (e:IllegalArgumentException){
            Toast.makeText(this, "로그인을 해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    fun createEmailId(){
        var email = editLoginId.text.toString()
        var password = editLoginPassword.text.toString()

        try {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        moveNextPage()
                    }
                }
        }catch(e:IllegalArgumentException){
            Toast.makeText(this,"회원가입 정보를 입력해주세요.",Toast.LENGTH_SHORT).show()
        }
    }

    fun logoutEmail(){
        FirebaseAuth.getInstance().signOut()
    }
}