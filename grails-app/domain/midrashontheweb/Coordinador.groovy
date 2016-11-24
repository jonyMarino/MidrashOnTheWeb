package midrashontheweb

class Coordinador extends Persona{
    static hasMany = [temasParaShiurim : TemaDeShiur]
    static constraints = {
    }
}
