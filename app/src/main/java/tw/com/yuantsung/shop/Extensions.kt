package tw.com.yuantsung.shop

import android.app.Activity
import android.content.Context

fun Activity.setNickName(nickname:String) {
    getSharedPreferences("shop", Context.MODE_PRIVATE)
        .edit()
        .putString("NICKNAME",nickname)
        .apply()
    setResult(Activity.RESULT_OK)
    finish()
}

fun Activity.getNickName(): String {
    return getSharedPreferences("shop", Context.MODE_PRIVATE).getString("NICKNAME","")
}