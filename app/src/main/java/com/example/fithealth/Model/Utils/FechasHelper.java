package com.example.fithealth.Model.Utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FechasHelper {

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static ZonedDateTime getMediaNocheTime() {

        ZonedDateTime now = null;
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime medianoche = null;

        now = ZonedDateTime.now(zoneId);


        if (now != null) {
            //obtenemos la fecha de medianoche de hoy
            LocalDate hoy = now.toLocalDate();
            medianoche = hoy.atStartOfDay(zoneId);

        }

        return medianoche;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean cambioDia() {
        ZonedDateTime medianoche = getMediaNocheTime();
        ZoneId zoneid = ZoneId.systemDefault();

        ZonedDateTime now = ZonedDateTime.now(zoneid);

        return now.isAfter(medianoche);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getTxtDia(LocalDate fechaMensaje) {
        ZoneId zoneid = ZoneId.systemDefault();
        LocalDate now = ZonedDateTime.now(zoneid).toLocalDate();





        if (fechaMensaje.equals(now)) {
            return "Hoy";
        } else if (fechaMensaje.plusDays(1).equals(now)) {
            return "Ayer";
        } else {
            //devolvemos la fecha en el formato mostrado
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy");
            return fechaMensaje.format(formatter);


        }

    }


}
