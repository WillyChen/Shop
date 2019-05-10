package tw.com.yuantsung.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_bus.*
import kotlinx.android.synthetic.main.row_bus.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class BusActivity : AppCompatActivity(), AnkoLogger {

    var busData: Bus? = null

    val retrofit = Retrofit.Builder()
        .baseUrl("https://data.tycg.gov.tw/opendata/datalist/datasetMeta/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus)
        doAsync {
            val busService = retrofit.create(BusService::class.java)
            busData = busService.busList()
                .execute()
                .body()
            busData?.datas?.forEach {
                info {
                    "${it.BusID} ${it.RouteID} ${it.Speed}"
                }
            }
            uiThread {
                recycler.layoutManager = LinearLayoutManager(this@BusActivity)
                recycler.setHasFixedSize(true)
                recycler.adapter = BusAdapter()
            }
        }

    }


    inner class BusAdapter() : RecyclerView.Adapter<BusRowHoder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusRowHoder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_bus,parent,false)
            return BusRowHoder(view)
        }

        override fun getItemCount(): Int {
            val count = busData?.datas?.size?: 0
            return count
        }

        override fun onBindViewHolder(holder: BusRowHoder, position: Int) {
            val bus = busData?.datas?.get(position)
            holder.setHoderContent(bus!!)
        }


    }

    inner class BusRowHoder(view: View) : RecyclerView.ViewHolder(view) {
        val busIdTextView = view.busId
        val routeIdTextView = view.routeId
        val speedTextView = view.speed

        fun setHoderContent(bus: BusData) {
            busIdTextView.text = bus.BusID
            routeIdTextView.text = bus.RouteID
            speedTextView.text = bus.Speed
        }

    }
}

data class Bus(
    val datas: List<BusData>
)

data class BusData(
    val Azimuth: String,
    val BusID: String,
    val BusStatus: String,
    val DataTime: String,
    val DutyStatus: String,
    val GoBack: String,
    val Latitude: String,
    val Longitude: String,
    val ProviderID: String,
    val RouteID: String,
    val Speed: String,
    val ledstate: String,
    val sections: String
)

interface BusService {
    @GET("download?id=b3abedf0-aeae-4523-a804-6e807cbad589&rid=bf55b21a-2b7c-4ede-8048-f75420344aed")
    fun busList(): Call<Bus>
}