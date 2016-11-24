package midrashontheweb

class Shiur {
    Horario horario
    static hasMany = [talmidim : Talmid]
    static belongsTo = [talmid:Talmid]
    
    static constraints = {
    }
}
