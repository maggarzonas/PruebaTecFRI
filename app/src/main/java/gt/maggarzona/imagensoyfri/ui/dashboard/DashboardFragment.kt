package gt.maggarzona.imagensoyfri.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gt.maggarzona.imagensoyfri.InformacionUsuarioOL
import gt.maggarzona.imagensoyfri.R
import gt.maggarzona.imagensoyfri.adaptador.RecyclerAdapterFav
import gt.maggarzona.imagensoyfri.baseDatos.baseDatos
import gt.maggarzona.imagensoyfri.clases.clsInformacion
import gt.maggarzona.imagensoyfri.conexiones.conexionWeb

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var sv_filtro: SearchView
    lateinit var mRecyclerView : RecyclerView
    lateinit var ly_sindatos: LinearLayout
    lateinit var tv_mensajevacio: TextView

    lateinit var ib_refrescar: ImageButton

    var filtro: String = ""

    val mAdapter : RecyclerAdapterFav = RecyclerAdapterFav()
    var isLoading: Boolean = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        conexionWeb.dFragmentoF = this
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        linearLayoutManager = LinearLayoutManager(context)

        sv_filtro = root.findViewById(R.id.sv_filtro) as SearchView
        ib_refrescar = root.findViewById(R.id.ib_refrescar) as ImageButton
        ly_sindatos = root.findViewById(R.id.ly_sindatos) as LinearLayout
        tv_mensajevacio = root.findViewById(R.id.tv_mensajevacio) as TextView

        mRecyclerView = root.findViewById(R.id.rv_lista_fotos) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = linearLayoutManager

        sv_filtro.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filtro = query
                context?.let { obtenerDatos(it, filtro) }
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.replace(" ", "").isEmpty()){
                    filtro = "";
                    context?.let { obtenerDatos(it, filtro) }
                }
                return false
            }
        })

        ib_refrescar.setOnClickListener(View.OnClickListener {
            sv_filtro.setQuery("", true)
            filtro = ""
            context?.let { obtenerDatos(it, filtro) }
        }
        )

        sv_filtro.queryHint = "Buscar por user/username"

        context?.let { obtenerDatos(it, filtro) }

        return root
    }

    fun obtenerDatos(contexto: Context, busqueda: String){
        lateinit var listado: List<clsInformacion>
        var texto = "No se encontró información en favoritos para $busqueda"
        val db = baseDatos(contexto)
        listado = if(busqueda.replace(" ", "").isEmpty()){
            texto = "Aún no tienes imágenes agregadas como favoritos en la app"
            db.obtenerFavorito()
        }else{
            db.obtenerFavoritoUser(busqueda)
        }

        if(listado.isNotEmpty()){
            mRecyclerView.visibility = View.VISIBLE
            ly_sindatos.visibility = View.GONE
            activity?.runOnUiThread{
                mAdapter.RecyclerAdapterFav(listado, contexto, 1)
                mRecyclerView.adapter = mAdapter
                isLoading = false
            }
        }else{
            tv_mensajevacio.text = texto
            ly_sindatos.visibility = View.VISIBLE
            mRecyclerView.visibility = View.GONE
        }
    }

    fun informacionUsuario(url: String, twitterUser: String?, instagramuser: String?, colecciones: String, bio: String?, likes: String, totalFotos: String, descripcion: String, id: String){
        val intent = Intent(context, InformacionUsuarioOL::class.java)
        //intent.putExtra("urlImagen", urlImagen)
        intent.putExtra("id", id)
        intent.putExtra("url", url)
        intent.putExtra("twitterUser", twitterUser)
        intent.putExtra("instagramuser", instagramuser)
        intent.putExtra("colecciones", colecciones)
        intent.putExtra("bio", bio)
        intent.putExtra("likes", likes)
        intent.putExtra("totalFotos", totalFotos)
        //intent.putExtra("urlActual", urlActual)
        intent.putExtra("descripcion", descripcion)
        startActivityForResult(intent, 2)
    }
}