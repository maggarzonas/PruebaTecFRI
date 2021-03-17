package gt.maggarzona.imagensoyfri.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.google.gson.GsonBuilder
import gt.maggarzona.imagensoyfri.InformacionUsuario
import gt.maggarzona.imagensoyfri.R
import gt.maggarzona.imagensoyfri.adaptador.RecyclerAdapter
import gt.maggarzona.imagensoyfri.conexiones.conexionWeb
import okhttp3.OkHttpClient
import okhttp3.Request
import quicktype.CamposFoto
import java.io.IOException


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var mRecyclerView : RecyclerView
    lateinit var objectList: List<CamposFoto>
    lateinit var contexto: Context
    lateinit var ly_sindatos: LinearLayout
    lateinit var tv_mensajevacio: TextView
    lateinit var sv_filtro: SearchView
    lateinit var ib_refrescar: ImageButton

    val mAdapter : RecyclerAdapter = RecyclerAdapter()
    var isLoading = false
    var pagina: Int = 1
    var filtro: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = ""
        conexionWeb.dFragmentoH = this
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        contexto = requireContext()
        linearLayoutManager = LinearLayoutManager(contexto)

        sv_filtro = root.findViewById(R.id.sv_filtro) as SearchView
        ib_refrescar = root.findViewById(R.id.ib_refrescar) as ImageButton
        ly_sindatos = root.findViewById(R.id.ly_sindatos) as LinearLayout
        tv_mensajevacio = root.findViewById(R.id.tv_mensajevacio) as TextView

        sv_filtro.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                pagina = 1
                filtro = query
                obtenerDatos(contexto, pagina, filtro)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.replace(" ", "").isEmpty()){
                    filtro = "";
                    context?.let { obtenerDatos(it, pagina, filtro) }
                }
                return false
            }
        })

        ib_refrescar.setOnClickListener(View.OnClickListener {
            pagina = 1
            sv_filtro.setQuery("", true)
            filtro = ""
            obtenerDatos(contexto, pagina, filtro)
        }
        )

        sv_filtro.queryHint = "Buscar por username"

        obtenerDatos(contexto, pagina, filtro)

        mRecyclerView = root.findViewById(R.id.rv_lista_fotos) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = linearLayoutManager

        initScrollListener()
        return root
    }

    fun obtenerDatos(contexto: Context, pagina: Int, busqueda: String) {
        var texto = "No se encontró información en favoritos para $busqueda"
        var url = ""

        if(busqueda.replace(" ", "").isEmpty()){
            texto = "Tuvimos un invonveniente al cargar la información, por favor intenta de nuevo"
            url = "https://api.unsplash.com/photos?page=$pagina&client_id=${conexionWeb.id}"
        }else{
            url = "https://api.unsplash.com/users/$busqueda/photos?page=$pagina&client_id=${conexionWeb.id}"
        }

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val httpBody = response.body?.string()
                val gson = GsonBuilder().create()
                if (response.isSuccessful && response.code == 200) {
                    objectList =
                        gson.fromJson(httpBody.toString(), Array<CamposFoto>::class.java).asList()
                    if(objectList.isNotEmpty()){
                        activity?.runOnUiThread {
                            ly_sindatos.visibility = View.GONE
                            mRecyclerView.visibility = View.VISIBLE
                            objectList.let {
                                mAdapter.RecyclerAdapter(
                                        it,
                                        contexto,
                                        0,
                                )
                            }
                            mRecyclerView.adapter = mAdapter
                            isLoading = false
                        }
                    }else{
                        activity?.runOnUiThread {
                            mRecyclerView.visibility = View.GONE
                            tv_mensajevacio.text = "No se encontró información para mostrar"
                            ly_sindatos.visibility = View.VISIBLE
                        }
                    }
                }else{
                    if(response.code == 400){
                        texto = "The request was unacceptable, often due to missing a required parameter"
                    }else if(response.code == 401){
                        texto = "Invalid Access Token"
                    }else if(response.code == 403){
                        texto = "Missing permissions to perform request"
                    } else if(response.code == 404){
                        texto = "The requested resource doesn’t exist"
                    }else if(response.code == 503 || response.code == 500){
                        texto = "Something went wrong on our end"
                    }
                    activity?.runOnUiThread {
                        mRecyclerView.visibility = View.GONE
                        tv_mensajevacio.text = texto
                        ly_sindatos.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                activity?.runOnUiThread {
                    mRecyclerView.visibility = View.GONE
                    ly_sindatos.visibility = View.VISIBLE
                    tv_mensajevacio.text = e.toString()
                }
            }
        })
    }

    private fun initScrollListener() {
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() ==
                        mAdapter.itemCount - 1
                    ) {
                        pagina += 1
                        obtenerDatos(contexto, pagina, filtro)
                        isLoading = true
                    }
                }
            }
        })
    }

    fun informacionUsuario(urlImagen: String, url: String, twitterUser: String?, instagramuser: String?, colecciones: String, bio: String?, likes: String, totalFotos: String, urlActual: String, descripcion: String ){
        val intent = Intent(context, InformacionUsuario::class.java)
        intent.putExtra("urlImagen", urlImagen)
        intent.putExtra("url", url)
        intent.putExtra("twitterUser", twitterUser)
        intent.putExtra("instagramuser", instagramuser)
        intent.putExtra("colecciones", colecciones)
        intent.putExtra("bio", bio)
        intent.putExtra("likes", likes)
        intent.putExtra("totalFotos", totalFotos)
        intent.putExtra("urlActual", urlActual)
        intent.putExtra("descripcion", descripcion)
        startActivityForResult(intent, 2)
    }
}