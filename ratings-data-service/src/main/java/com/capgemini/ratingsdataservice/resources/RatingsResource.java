package com.capgemini.ratingsdataservice.resources;


import com.capgemini.ratingsdataservice.models.Rating;

import com.capgemini.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {


    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId){
        return new Rating(movieId,4); }

        @RequestMapping("users/{userId}")
        public UserRating getuserRating(@PathVariable("userId") String userId) {
            List<Rating> ratings = Arrays.asList(
                    new Rating("43", 4),
                    new Rating("49", 5)
            );
           UserRating userRating = new UserRating();
           userRating.setUserRating(ratings);
           return userRating;
        }
    }

