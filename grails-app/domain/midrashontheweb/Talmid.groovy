package midrashontheweb

class Talmid extends IntegranteShiur{

    static hasMany = [shiurim : Shiur]
    static belongsTo = [shiur : Shiur]
    
    static constraints = {
        shiurim(nullable:true)
        disponibilidad(nullable:true)
        shiur(nullable:true)
        horarioOcupado(nullable:true)
    }
}
