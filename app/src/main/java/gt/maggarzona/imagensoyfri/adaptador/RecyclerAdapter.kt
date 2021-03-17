package gt.maggarzona.imagensoyfri.adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gt.maggarzona.imagensoyfri.R
import gt.maggarzona.imagensoyfri.holder.ViewHolder
import quicktype.CamposFoto

class RecyclerAdapter : RecyclerView.Adapter<ViewHolder>() {
    var campos: List<CamposFoto>  = ArrayList()
    lateinit var context:Context
    var tipo: Int = 0
    //var fragmentoH: HomeFragment? = null
    //var fragmentoF: DashboardFragment? = null

    fun RecyclerAdapter(lista: List<CamposFoto>, context: Context, tipo: Int){
        this.campos = lista
        this.context = context
        this.tipo = tipo
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = campos.get(position)
        holder.bind(item, context, tipo)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_imagenes, parent, false))
    }

    override fun getItemCount(): Int {
        return campos.size
    }
}