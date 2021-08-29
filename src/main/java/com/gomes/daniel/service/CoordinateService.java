package com.gomes.daniel.service;

import com.gomes.daniel.domain.model.Coordinate;
import com.gomes.daniel.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoordinateService {

    private static final double DEFAULT_PRECISION = 1E5;

    public List<Coordinate> listCoord(String polyline){
        return decodePolyToCoord(polyline);
    }

    public List<Coordinate> decodePolyToCoord(String encoded) {
        List<Coordinate> track = new ArrayList<>();
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

            Coordinate p = new Coordinate(lat / precision, lng / precision);
            track.add(p);
        }
        return fitCoordinate(track);
    }

    public List<Coordinate> fitCoordinate(List<Coordinate> coordinates){
        int listSize = coordinates.size();
        while (coordinates.size()>12){

            for (int i = 0; i < listSize; i=i+1){
                listSize = coordinates.size();
               if (i%2==1){
                   coordinates.remove(i);
               }
            }
        }
        return  coordinates;
    }

//    public Usuario SalvarPercurso(){
//
//    }




}
