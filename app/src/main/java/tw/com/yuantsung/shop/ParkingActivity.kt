package tw.com.yuantsung.shop

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_parking.*
import org.jetbrains.anko.*
import java.net.URL

class ParkingActivity : AppCompatActivity(), AnkoLogger {
    private val TAG = ParkingActivity::class.java.simpleName
    val parkingUrl = "http://data.tycg.gov.tw/opendata/datalist/datasetMeta/download?id=f4cc0b12-86ac-40f9-8745-885bddc18f79&rid=0daad6e6-0632-44f5-bd25-5e1de1e9146f"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking)

        //Anko
        doAsync {
            val url = URL(parkingUrl)
            val json = url.readText()
//            Log.d(TAG,"parking json: $json ")
            info { json }
            uiThread {
//                Toast.makeText(it,"get content!",Toast.LENGTH_LONG).show()
                toast("got it")
                infoText.text = json
                alert("get it","Alert") {
                    okButton {
                        parseGson(json)
                    }
                    cancelButton {

                    }
                }.show()
            }
        }

//        ParkingTask().execute(parkingUrl)

    }

    private fun parseGson(json: String) {
        val parking = Gson().fromJson<parkingAuto>(json,parkingAuto::class.java)
        info { "size ${parking.parkingLots.size}" }
        parking.parkingLots.forEach {
            info { "${it.areaId} ${it.areaName} ${it.parkName} ${it.totalSpace}" }
        }
    }

    inner class ParkingTask : AsyncTask<String,Void,String>() {
        override fun doInBackground(vararg params: String?): String {
            val url = URL(params[0])
            val json = url.readText()
            Log.d(TAG,"parking json: $json ")
            return json

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Toast.makeText(this@ParkingActivity,"get content!",Toast.LENGTH_LONG).show()
            infoText.text = result
        }
    }
}

/*
{
  "parkingLots" : [ {
        "areaId" : "2",
        "areaName" : "中壢區",
        "parkName" : "中央停車場",
        "totalSpace" : 350,
        "surplusSpace" : "159",
        "payGuide" : "汽車：30元/小時。停車時數未滿一小時者，以一小時計算。逾一小時者，其超過之不滿一小時部分，如不逾三十分鐘者，以半小時計算；如逾三十分鐘者，仍以一小時計算收費。月租2400元",
        "introduction" : "中壢區公所管轄之公有停車場",
        "address" : "中壢區中央東路83號",
        "wgsX" : 121.2264,
        "wgsY" : 24.9563,
        "parkId" : "P-JL-003"
      }
      ]
      }

) */

//class Parking(val parkingLots: List<ParkingLot>)
//
//data class ParkingLot(var areaId: String,
//                      var areaName: String,
//                      var parkName: String,
//                      var totalSpace: Int
//)

data class parkingAuto(
    val parkingLots: List<ParkingLot>
)

data class ParkingLot(
    val address: String,
    val areaId: String,
    val areaName: String,
    val introduction: String,
    val parkId: String,
    val parkName: String,
    val payGuide: String,
    val surplusSpace: String,
    val totalSpace: Int,
    val wgsX: Double,
    val wgsY: Double)