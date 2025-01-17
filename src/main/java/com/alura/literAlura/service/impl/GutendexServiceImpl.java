package com.alura.literAlura.service.impl;

import com.alura.literAlura.dto.GutendexBookDTO;
import com.alura.literAlura.exception.GutendexException;
import com.alura.literAlura.model.GutendexResponse;
import com.alura.literAlura.service.GutendexService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GutendexServiceImpl implements GutendexService {

    private static final String GUTENDEX_BASE_URL = "https://gutendex.com/books/";
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    @Autowired
    public GutendexServiceImpl(OkHttpClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<GutendexBookDTO> searchBooksByTitle(String title) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(GUTENDEX_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("search", title);
        String url = urlBuilder.build().toString();
        System.out.println("URL de petición: " + url); // <-- Agrega este log
        return fetchGutendexData(url);
    }

    @Override
    public List<GutendexBookDTO> getTopDownloadedBooks() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(GUTENDEX_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("sort", "downloads");
        return fetchGutendexData(urlBuilder.build().toString(), 5);
    }

    @Override
    public List<GutendexBookDTO> searchAuthorsByYear(int year) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(GUTENDEX_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("copyright", "false");
        urlBuilder.addQueryParameter("author_year_end", String.valueOf(year));
        urlBuilder.addQueryParameter("author_year_start", String.valueOf(year));
        return fetchGutendexData(urlBuilder.build().toString());
    }

    private List<GutendexBookDTO> fetchGutendexData(String url) {
        return fetchGutendexData(url,Integer.MAX_VALUE);
    }
    private List<GutendexBookDTO> fetchGutendexData(String url, int limit) {
        Request request = new Request.Builder().url(url).build();
        List<GutendexBookDTO> allBooks = new ArrayList<>();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new GutendexException("Error al conectar con la API de Gutendex: Código " + response.code() + " Mensaje: " + response.message());
            }
            GutendexResponse gutendexResponse = objectMapper.readValue(response.body().string(), GutendexResponse.class);
            allBooks.addAll(gutendexResponse.results());
            String nextUrl = gutendexResponse.next();
            while (nextUrl != null && allBooks.size() < limit) {
                Request nextRequest = new Request.Builder().url(nextUrl).build();
                Response nextResponse = client.newCall(nextRequest).execute();
                if (!nextResponse.isSuccessful()) {
                    break;
                }
                GutendexResponse nextGutendexResponse = objectMapper.readValue(nextResponse.body().string(), GutendexResponse.class);
                allBooks.addAll(nextGutendexResponse.results());
                nextUrl = nextGutendexResponse.next();
            }
            if(allBooks.size()>limit)
                return allBooks.subList(0,limit);
            return allBooks;

        }  catch (JsonProcessingException e) {
            throw new GutendexException("Error al procesar la respuesta JSON de la API", e);
        }catch (IOException e) {
            throw new GutendexException("Error al realizar la petición a la API de Gutendex", e);
        }
    }
}
