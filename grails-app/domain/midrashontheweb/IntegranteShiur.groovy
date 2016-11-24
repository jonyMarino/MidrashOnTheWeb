package midrashontheweb

class IntegranteShiur extends Persona{
    Horario horarioOcupado = new Horario()
    DisponibilidadHoraria disponibilidad
    
    static constraints = {
        disponibilidad(nullable:true)
    }
    
}
