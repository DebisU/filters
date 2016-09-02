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
        final String result = filterUseCaseResponse != null ? filterUseCaseResponse.getResult() : null;

        System.out.println("*******______RESULTADO DE LOS FILTROS______*******");
        System.out.println();
        System.out.println(result != null ? result : "");
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
}
