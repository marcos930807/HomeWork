package cu.marcos930807.robotrevo.db.model

data class Notice(var id: Long?,
                  var category: String,
                  var title: String,
                  var body: String,
                  var price: String,
                  var mail: String,
                  var ownername: String,
                  var number: String,
                  var photoa: String,
                  var photob: String,
                  var photoc: String,
                  var clientid: Long?)

fun Notice.clear() {
    category = ""
    title = ""
    body = ""
    price = ""
    mail = ""
    ownername = ""
    number = ""
    photoa = ""
    photob = ""
    photoc = ""
    id = null
}

data class Client(
        var id: Long?,
        var mail:String,
        var name:String,
        var phone:String
)