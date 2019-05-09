package tw.com.yuantsung.shop

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signup.setOnClickListener {
            val emailStr = emailText.text.toString()
            val passwordStr = passwordText.text.toString()

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(emailStr,passwordStr)
                .addOnCompleteListener({
                    if (it.isSuccessful) {
                        AlertDialog.Builder(this)
                            .setTitle("Sign Up")
                            .setMessage("Account create")
                            .setPositiveButton("OK",{dialog, which ->
                                setResult(Activity.RESULT_OK)
                                finish()
                            }).show()
                    }
                    else {
                        AlertDialog.Builder(this)
                            .setTitle("Sign Up")
                            .setMessage(it.exception?.message)
                            .setPositiveButton("OK",null).show()
                    }
                }
            )

        }

    }
}
