package gt.maggarzona.imagensoyfri

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class InformacionUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion_usuario)

        (this as? AppCompatActivity)?.supportActionBar?.title = ""

        val imgPerfil: de.hdodenhof.circleimageview.CircleImageView = findViewById(R.id.iv_usuario)

        val tv_coleccion: TextView = findViewById(R.id.tv_dcolecciones)
        val tv_likes: TextView = findViewById(R.id.tv_dlikes)
        val tv_fotos: TextView = findViewById(R.id.tv_dtotalFotos)

        val iv_twitter: ImageView = findViewById(R.id.iv_twitter)
        val iv_instagram: ImageView = findViewById(R.id.iv_instagram)
        val iv_portafolio: ImageView = findViewById(R.id.iv_portafolio)

        val iv_imagen: ImageView = findViewById(R.id.iv_imagen)

        val tv_twitter: TextView = findViewById(R.id.tv_twitter)
        val tv_instagram: TextView = findViewById(R.id.tv_instagram)
        val tv_bio: TextView = findViewById(R.id.tv_bio)
        val tv_descripcion: TextView = findViewById(R.id.tv_descripcion)

        val intent = intent
        val iPerfil = intent.getStringExtra("urlImagen")

        val dColeccion = intent.getStringExtra("colecciones")
        val dLikes = intent.getStringExtra("likes")
        val dFotos = intent.getStringExtra("totalFotos")

        val dTwitter = intent.getStringExtra("twitterUser")
        val dInstagram = intent.getStringExtra("instagramuser")
        val dPortafolio = intent.getStringExtra("url")

        val dBio = intent.getStringExtra("bio")
        val dActual = intent.getStringExtra("urlActual")
        val ddescripcion = intent.getStringExtra("descripcion")

        iv_twitter.visibility = View.INVISIBLE
        iv_instagram.visibility = View.INVISIBLE
        iv_portafolio.visibility = View.INVISIBLE

        if(dBio != null){
            tv_bio.text = dBio
        }
        if(dActual != null){
            Picasso.get()
                .load(dActual)
                .placeholder(R.drawable.cargando)
                .error(R.drawable.cargando)
                .fit()
                .into(iv_imagen)
        }
        if(ddescripcion != null){
            tv_descripcion.text = ddescripcion
        }

        if (iPerfil != null) {
            Picasso.get()
                    .load(iPerfil)
                    .placeholder(R.drawable.cargando)
                    .error(R.drawable.cargando)
                    .fit()
                    .into(imgPerfil)
        }
        if(dColeccion != null){
            tv_coleccion.text = dColeccion
        }
        if(dLikes != null){
            tv_likes.text = dLikes
        }
        if(dFotos != null){
            tv_fotos.text = dFotos
        }
        if(dTwitter != null){
            iv_twitter.visibility = View.VISIBLE
            tv_twitter.text = dTwitter
        }
        if(dInstagram != null){
            iv_instagram.visibility = View.VISIBLE
            tv_instagram.text = dInstagram
        }
        if(dPortafolio != null){
            iv_portafolio.visibility = View.VISIBLE
            iv_portafolio.setOnClickListener {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(dPortafolio)
                startActivity(openURL)
            }
        }
    }
}