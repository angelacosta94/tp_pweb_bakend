package py.com.progweb.prueba.ejb;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;


@Singleton
@Startup
public class ActualizarSaldoBolsasDePuntos {
    @Inject
    private BolsaDePuntosDAO bolsaDePuntosDAO;

    @Schedule(hour = "0", minute = "0", second = "0", dayOfWeek = "*", persistent = true,
            info = "Cada dia a la medianoche")
    //@Schedule(hour = "*", minute = "*", second = "*/30", dayOfWeek = "*", persistent = true,
    //info = "Cada 30 segundos")
    public void actualizarSaldoBolsaDePuntos() {
        bolsaDePuntosDAO.actualizarSaldoCaducados();
        System.out.println("Bolsas de puntos actualizadas =D");
    }
}

