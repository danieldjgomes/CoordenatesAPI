package com.gomes.daniel.service;

import com.gomes.daniel.domain.model.Coordenada;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoordenadaService {

    private static final double DEFAULT_PRECISION = 1E5;

    private static final Logger logger = LoggerFactory.getLogger(CoordenadaService.class);

    public List<Coordenada> listCoord(String polyline){
        logger.info(polyline);
        return decodePolyToCoord(polyline);
    }

    public List<Coordenada> decodePolyToCoord(String encoded) {
        List<Coordenada> track = new ArrayList<>();
        double precision = DEFAULT_PRECISION;
        int index = 0;
        int lat = 0;
        int lng = 0;

        while (index < encoded.length()) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            Coordenada p = new Coordenada(lat / precision, lng / precision);
            track.add(p);
        }
        return fitCoordinate(track);
    }

    public List<Coordenada> fitCoordinate(List<Coordenada> coordinates){

//        logger.info(String.valueOf(coordinates.stream().count()));

        var index = 0;
        var toRemove = new ArrayList<>();
        int escopo = coordinates.size()/16;

        for(Coordenada coor : coordinates){
            if(index % escopo != 0 && index != coordinates.size()-1){
                toRemove.add(coor);
            }
            index++;
        }
        coordinates.removeAll(toRemove);

        logger.info(String.valueOf(coordinates.stream().count()));
        String log = "";
//        for(Coordenada coor : coordinates){
//            log += coor.getLat() + "," + coor.getLng()  + "\n";
//        }
//        logger.info(log);
        return  coordinates;
    }





}
