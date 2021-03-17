package gt.maggarzona.imagensoyfri.clases

class clsInformacion {
    var id: String = ""
    lateinit var perfil: ByteArray
    var username: String = ""
    var user: String = ""
    lateinit var imagen: ByteArray
    var portafolio: String = ""
    var twitterUser: String = ""
    var instagramuser: String = ""
    var colecciones: String = ""
    var bio: String = ""
    var likes: String = ""
    var fotos: String = ""
    var descripcion: String = ""
    var favorito: String = ""

    constructor(id: String, perfil: ByteArray, userName: String, user: String, imagen: ByteArray, portafolio: String, twitterUser: String?, instagramuser: String?, colecciones: String, bio: String?, likes: String, fotos: String, descripcion: String, favorito: String) {
        this.id = id
        this.perfil = perfil
        this.username = userName
        this.user = user
        this.imagen = imagen
        this.portafolio = portafolio
        if (twitterUser != null) {
            this.twitterUser = twitterUser
        }else{
            this.twitterUser = ""
        }
        if (instagramuser != null) {
            this.instagramuser = instagramuser
        }else{
            this.instagramuser = ""
        }
        this.colecciones = colecciones
        if (bio != null) {
            this.bio = bio
        }else{
            this.bio = ""
        }
        this.likes = likes
        this.fotos = fotos
        this.descripcion = descripcion
        this.favorito = favorito
    }
    constructor() {
    }


}