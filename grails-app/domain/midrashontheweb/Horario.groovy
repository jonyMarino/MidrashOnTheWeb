package midrashontheweb

public enum DiasDeLaSemana {
 DOMINGO,LUNES,MARTES,MIERCOLES,JUEVES,VIERNES,SABADO
}

public enum RangoMediaHora {
 RANGO0A30,RANGO30A60
}

class MediaHora {
	byte hora
	RangoMediaHora mediaHora
	static constraints = {
            hora(max: 23)
    }
}

class Horario {
    Map horario = [:]
	
    static constraints = {
    }
	
	def ingresar ( DiasDeLaSemana dia , MediaHora hora){
		List list = horario.get((dia), [])
		list.add(hora)
		horario."$dia" = list
	}
	
	boolean incluye (Horario horarioConsultado){
		horarioConsultado.keySet().every { 
			dia, value -> horarioConsultado."$dia".every { 
				hora, val->horario."$dia".any {
					it == hora
				}
			} 
			
		}
		
	}
}
