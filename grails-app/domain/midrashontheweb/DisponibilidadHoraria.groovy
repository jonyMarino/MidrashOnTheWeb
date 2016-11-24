package midrashontheweb

class DisponibilidadHoraria {
    byte cantidadDeDias
    byte cantidadDeHoras
    Horario horario
    
    static constraints = {
    }
    
    boolean tieneHorarioLibre (Horario horarioOcupado){
        assert horario.incluye(horarioOcupado)
        
      
        if( (horarioOcupado.getCantidadDeMediasHoras()/2.0) < cantidadDeHoras   ){
            true
        }else{
            false
        }
        
    }
}
