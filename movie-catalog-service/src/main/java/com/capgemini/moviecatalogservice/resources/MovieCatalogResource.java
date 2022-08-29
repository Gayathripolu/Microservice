package com.capgemini.moviecatalogservice.resources;


import com.capgemini.moviecatalogservice.models.CatalogItem;
import com.capgemini.moviecatalogservice.models.Movie;
import com.capgemini.moviecatalogservice.models.Rating;
import com.capgemini.moviecatalogservice.models.UserRating;
import com.netflix.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    //@Autowired
    //private DiscoveryClient discoveryClient;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public MovieCatalogResource() {
    }

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

       // RestTemplate restTemplate = new RestTemplate();
       //Movie movie = restTemplate.getForObject("http://localhost:8082/movies/suryauma", Movie.class);
        //move to down in above line
        //get all rated movie IDS

       // WebClient.Builder builder = WebClient.builder();

  /*
        List<Rating> ratings = Arrays.asList(

                new Rating("43",4),
                new Rating("49",5)
        );

  */

       UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/" + userId , UserRating.class) ;
        return ratings.getUserRating().stream().map(rating -> {

                    // For each movie ID, call movie info service and get details

                    Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
/*
                    Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();

 */

                    // Put them all together
                   return new CatalogItem(movie.getName(), "Desc", rating.getRating());
                })
         .collect(Collectors.toList());


        //  return Collections.singletonList(
        //      new CatalogItem ("Transformers", "Test",5 )
        //        );

    }
}

