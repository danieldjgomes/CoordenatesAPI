package com.gomes.daniel.controller;

import com.gomes.daniel.domain.model.Coordinate;
import com.gomes.daniel.domain.model.ModoPercurso;
import com.gomes.daniel.domain.model.Percurso;
import com.gomes.daniel.domain.repository.PercursoRepository;
import com.gomes.daniel.service.CoordinateService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@Controller

public class CoordinateController {
     private static final String MAPS_KEY = "AIzaSyAzXJTthZ-4MMQfiVSSfM9k1BP_5YjoEzo";
     private static final String SCHEME = "https";
     private static final String HOST = "maps.googleapis.com";
     private static final String PATH = "/maps/api/directions/json";
     private static final String ORIGINPARAM = "origin={origin}";
     private static final String DESTPARAM = "destination={destination}";
     private static final String MODEPARAM = "mode={mode}";
     private static final String KEYPARAM = "key={key}";

    @Autowired
    private CoordinateService service;

    @Autowired
    private PercursoRepository percursoRepository;

//    @PostMapping("/percurso")
//    public  ResponseEntity<Percurso> main(
//            @RequestParam String origin,
//            @RequestParam String destination,
//            @RequestParam String modoPercurso) {
//
//        UriComponents uriComponents = UriComponentsBuilder.newInstance()
//                .scheme(SCHEME).host(HOST)
//                .path(PATH)
//                .query(ORIGINPARAM)
//                .query(DESTPARAM)
//                .query(KEYPARAM)
//                .query(MODEPARAM)
//                .buildAndExpand(origin,destination,MAPS_KEY,modoPercurso);
//
//        System.out.println(uriComponents.toUriString());
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriComponents.toUri(), Object.class);
//        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//            String polyline = StringUtils.substringBetween(responseEntity.toString(), "overview_polyline={points=", "},");
//            Percurso percurso = new Percurso(service.listCoord(polyline),ModoPercurso.stringToModo(modoPercurso),origin,destination);
//                percursoRepository.save(percurso);
//                return ResponseEntity.ok(percurso);
//        }
//        return  ResponseEntity.notFound().build();
//
//    }

//    @GetMapping("/polyline")
//    public String main2(
//            @RequestParam String origin,
//            @RequestParam String destination) {
//
//        UriComponents uriComponents = UriComponentsBuilder.newInstance()
//                .scheme(SCHEME).host(HOST)
//                .path(PATH)
//                .query(ORIGINPARAM)
//                .query(DESTPARAM)
//                .query(KEYPARAM)
//                .buildAndExpand(origin,destination,MAPS_KEY);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriComponents.toUri(), Object.class);
//        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//             return StringUtils.substringBetween(responseEntity.toString(), "overview_polyline={points=", "},");
//
//        }
//
//
//
//    }
//    @GetMapping("/3")
//    public String main3(
//            @RequestParam String origin,
//            @RequestParam String destination) {
//
//        UriComponents uriComponents = UriComponentsBuilder.newInstance()
//                .scheme(SCHEME).host(HOST)
//                .path(PATH)
//                .query(ORIGINPARAM)
//                .query(DESTPARAM)
//                .query(KEYPARAM)
//                .buildAndExpand(origin,destination,MAPS_KEY);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriComponents.toUri(), Object.class);
//
//        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//            StringBuilder stringBuilder =  new StringBuilder();
//            String polyline = StringUtils.substringBetween(responseEntity.toString(), "overview_polyline={points=", "},");
//            for (Coordinate i : service.listCoord(polyline)){
//                stringBuilder.append(i.getLat() + "," + i.getLng() + "|");
//            }
//            return stringBuilder.toString();
//
//
//        }
//
//    }
}