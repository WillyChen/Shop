package tw.com.yuantsung.shop

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_nickname.*

class NicknameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname)
        doneBtn.setOnClickListener {
            FirebaseDatabase.getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("nickname")
                .setValue(nickNameText.text.toString())

            setNickName(nickNameText.text.toString())
        }
    }
}
