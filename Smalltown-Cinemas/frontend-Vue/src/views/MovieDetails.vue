<template>
<div>
  <div id="details-header">
    <movie-header />
  </div>
  <div id="card">
    <body class="movie-details" >
      <h1 id="movie-title">{{ movie.title }}</h1>
      <h2 id="movie-tagline">{{ movie.tagline }}</h2>
      <h3 id="movie-overview">{{ movie.overview }}</h3>
      <h3 id="movie-runtime">{{ movie.runtime }} minutes</h3>
      <h3 id="movie-rating" v-bind:key="this.rating">Rated: {{this.rating}}</h3>
      <img id="image" :src="getMoviePoster()" />
      <h3 id="purchase">Select a showtime to purchase tickets</h3>
      <view-showtimes  id="showtimes" v-bind:key="screenId" v-bind:screenId="this.screenId" />
    </body>
  </div>
</div>
</template> 

<script>

import MovieHeader from '@/views/MovieHeader.vue'
import ViewShowtimes from '@/views/ViewShowtimes.vue'

export default {
    components: {
        MovieHeader,
        ViewShowtimes
    },
    data() {
        return {
      API_URL:'https://api.themoviedb.org/3/movie/'+this.$route.params.id+'?api_key=c61dfd39d764c829f63ee9e60bc55eb7',
      movie: [],
      screenId: 1,
      rating: "",
      region: []
        }
    },
    methods: {
        getMoviePoster () {
            return 'http://image.tmdb.org/t/p/w500' + this.movie.poster_path;

        }
       
         
          
    },
    created() {
        fetch(this.API_URL)
        .then((response) => {
            return response.json();
        })
        .then((jsonData) => {
            this.movie = jsonData
            return this.movie.id
        })
        .then((id) => {
            fetch('http://localhost:8080/Final-Capstone-Backend-Server/api/screens/movie/'+ id, 
              {method: 'GET', headers: {'Content-Type': 'application/json'} })
            .then((response ) => {
              return response.json();
             })
             .then((response) =>{
                 this.screenId=response
               
              })
              .catch((err) => {console.log(err)})
           
           fetch('https://api.themoviedb.org/3/movie/'+id+'/release_dates?api_key=c61dfd39d764c829f63ee9e60bc55eb7',
            {method: 'GET', headers: {'Content-Type': 'application/json'}})
             .then((response) => {
                         return response.json();
                        })
                       .then((response) => {
                         return response.results
                        })
                        .then((results) => {
                          let aresult = []
                          results.forEach(result => {
                            if (result.iso_3166_1 == "US") {
                              aresult = result
                            }                            
                          })

                          return aresult
                        })
                        .then((aresult) => {
                          return aresult.release_dates[0]
                        })
                        .then((release_dates)=>{
                          return release_dates.certification
                        })
                        .then((certification) => {
                          this.rating = certification;
                        })
                        .catch((err) => {console.log(err)})
          
           })

        }
      
  
    

}
</script>

<style lang="scss" scoped>
.movie-details {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas:
    " image movie-title     "
    " image movie-rating    "
    " image movie-tagline   "
    " image movie-overview  "
    " image movie-runtime   "
    " image purchase        "
    " .     showtimes       ";
}

#card {
  background-color: rgb(194, 177, 152);
  text-decoration: none;
}

#image {
  grid-area: image;
}

#movie-title {
  grid-area: movie-title;
}

#movie-rating {
  grid-area: movie-rating;
}

#movie-tagline {
  grid-area: movie-tagline;
}

#movie-overiew {
  grid-area: movie-overview;
}

#movie-runtime {
  grid-area: movie-runtime;
}

#purchase {
  grid-area: purchase;
}

#showtimes {
  grid-area: showtimes;
}

#card {
  display: flex;
  flex-wrap: wrap;
  padding: 10px;
}

#image {
  border-radius: 3%;
  margin-left: 25px;
}

#movie-title {
  margin-left: -10%;
  margin-top: 25px;
  font-size: 4em;
}

#movie-rating {
  margin-left: -10%;
}

#movie-tagline {
  margin-left: -10%;
  font-size: 2.5em;
  font-style: oblique;
}

#movie-overview {
  margin-left: -10%;
  margin-right: 10%;
  font-size: 1.5em;
}

#movie-runtime {
  margin-left: -10%;
  font-size: 1.5em;
}

#purchase {
  margin-left: -10%;
  font-size: 1em;
}

view-showtimes {
  text-decoration: none;
  list-style-type: none;
  color: black;
}

#showtimes {
  margin-left: -10%;
  font-size: 1.5em;
  color: black;
  list-style-type: none;
}
  

h4 {
  margin-left: -10%;
  font-size: 2em;
}
</style>