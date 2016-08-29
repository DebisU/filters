package com.scmspain.mercadio.filter.application;

import com.scmspain.mercadio.filter.application.usecases.FilterNotFoundException;
import com.scmspain.mercadio.filter.application.usecases.FilterUseCase;
import com.scmspain.mercadio.filter.application.usecases.FilterUseCaseRequest;
import com.scmspain.mercadio.filter.application.usecases.FilterUseCaseResponse;
import com.scmspain.mercadio.filter.domain.FilterRequest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ManualAdTest {
    @Test
    public void manualTest() throws Exception {
        final FilterUseCase filterUseCase = new FilterUseCase();
        final FilterRequest filterRequest = new FilterRequest();
        final Map<String,String> filters = new HashMap<>();

        filters.put("forbiddenwords","tags,keywords");
        filters.put("removespecificwords","Palabras de búsqueda,search");
        filters.put("separators","");
        filters.put("url","");
        filters.put("commonwords","");
        filters.put("endspam","");

        filterRequest.setFiltersToApply(filters);
        filterRequest.setTextToFilter(getInput());

        final FilterUseCaseRequest filterUseCaseRequest = new FilterUseCaseRequest(filterRequest);

        FilterUseCaseResponse filterUseCaseResponse = null;
        try {
            filterUseCaseResponse = filterUseCase.execute(filterUseCaseRequest);
        } catch (FilterNotFoundException e) {
            e.printStackTrace();
        }
        final String result = filterUseCaseResponse.getResult();

        System.out.println("*******______RESULTADO DE LOS FILTROS______*******");
        System.out.println();
        System.out.println(result);
        System.out.println();
        System.out.println("*******______FIN RESULTADO DE LOS FILTROS______*******");
    }

    //when copy paste an string remember to remove \n at the end of each line
    private String getInput() {
        return "Vendo mi aviaro completo tengo 37 jaulas de 0,60 en perfecto estado,4 jaulones de" +
                "metro..54 isabelitas de Japón, la mayoría  sesadas. y ((81)) canarios de los" +
                "cuales 52 nuevos, todos anillados de este año y 29 de ellos de sobre año.34 rojos" +
                "mosaicos,12 de sobre año y 21 nuevos,31 amarillo lipocromo y portadores de blancos" +
                "recesivo,22 nuevos y 9 de sobre año,16 blancos recesivos,9 nuevos y 7 sobre año," +
                "mando fotos por whasap</p>";
    }

    private void texts() {
        final String check0 = "---------------------- CANAL OCIO CONCORDE -------------------------<br />     NUEVA TIENDA DE VIDEOJUEGOS Y CONSOLAS<br />                      EN SANTA CRUZ DE TENERIFE<br /><br />Juegos En Perfecto Estado<br /><br />---------------------------De PlayStation1(Y Mas" +
                "Plataformas)------------------------<br /><br />Final Fantasy VIII,Crash Bandicout 2 y 3, Tomb Raider 1,2 y 3 Y Muchos Mas!!!" +
                "Desde 5 euros!!! También Compramos Y Cambiamos Tus Juegos!!!<br /> <br />ADEMÁS DISPONEMOS DE VARIAS PLATAFORMAS RETRO COMO GAMECUBE NES SNES NINTENDO 64" +
                "DREAMCAST...<br /><br />COMPRAMOS,VENDEMOS Y CAMBIAMOS,  PRODUCTOS DE ELECTRÓNICA, TODA CLASE DE CONSOLAS," +
                "VIDEOJUEGOS, MÓVILES,TABLES, ...<br /><br />SI PREFIERES LOS PUEDES ALQUILAR POR 1,50€<br />SOMOS TIENDA FISÍCA<br /><br />PREGÚNTANOS POR WHATSAPP AL <br />TLF. (nº móvil en la ficha de la tienda)<br />Y ASÍ SERÁS EL PRIMERO EN ENTERARTE<br />DE NUESTRAS PROMOCIONES.<br /><br />COMPRAMOS Y VENDEMOS<br />VIDEOJUEGOS DESDE 1,95€, CONSOLAS<br />PELÍCULAS, MÓVILES, TABLETS...<br /><br />Visitanos en el CENTRO COMERCIAL CONCORDE<br />CTRA. GENERAL DEL SUR, KM 6,3 LOCAL Nº11<br />--- O En Facebook: Canal Ocio Concorde<br />----------------------Y Conoce Nuestras ofertas---------------------------------- <br /><br />producto original</p>";
    }
}
