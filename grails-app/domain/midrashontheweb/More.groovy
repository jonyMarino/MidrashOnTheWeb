package midrashontheweb

class More extends IntegranteShiur{

    static hasMany = [shiurim : Shiur, temasParaShiurim : TemaDeShiur]
    
    static constraints = {
    }
}
