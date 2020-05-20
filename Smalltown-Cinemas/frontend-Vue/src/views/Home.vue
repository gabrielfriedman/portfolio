<template>
  
  <div class="home">
    <movie-header />
    <div id="background-image"></div>
    <div class="movie-list">
      <div v-for='movie in movies' id='movie' v-bind:key='movie.id'>
        <router-link v-on:click='sendScreen(movie.id)' v-bind:key='movie.id'  v-bind:to='{name:"movie-details", params:{id: movie.id}}'>
          <movie-card 
          
          v-bind:name='movie.title' 
          v-bind:posterUrl="'https://image.tmdb.org/t/p/w500' + movie.poster_path" 
          
        />
        </router-link>
      </div>
    </div>
    <!--<h2>Due to recent litigation, Smalltown Cinemas will no longer be playing adult titles.</h2>
    <h3>Blame Karen for the inconvenience</h3>-->
  </div>
</template>

<script>
import MovieCard from '@/views/MovieCard.vue'
import MovieHeader from '@/views/MovieHeader.vue'


export default {
  components: {
      MovieCard,
      MovieHeader
  },

  name: 'home',
  data() {
    return {
      
      API_URL:'https://api.themoviedb.org/3/movie/now_playing?api_key=c61dfd39d764c829f63ee9e60bc55eb7',
      backend_URL: 'process.env.VUE_APP_REMOTE_API',
      screens: [],
      movies: [],
      screenId: ""
    }
  },
  created() {
    fetch('http://localhost:8080/Final-Capstone-Backend-Server/api/screens', 
     {method: 'GET', headers: {'Content-Type': 'application/json'} })
    .then((response ) => {
      return response.json();
    })
    .then((jsonData) => {
      this.screens=jsonData;
      return this.screens;
    })
    .then((screens) => {
      screens.forEach((screen) => {
        fetch('https://api.themoviedb.org/3/movie/'+screen.filmId+'?api_key=c61dfd39d764c829f63ee9e60bc55eb7')
        .then((response) => {
          return response.json();
         })
        .then((jsonData) => {
          this.movies.push(jsonData);
          })
          .catch((err) => {
            console.log(err)
          })
        })
    })
    .catch((err) => {
      console.log(err)
    })     
  },
  methods: {
    sendScreen(movieId) {

      this.screens.forEach((screen) => {
        if (screen.filmId == movieId) {
           this.screenId = screen.screenId
        }
      }
      );
        this.$emit('screen', this.screenId);
    }
  }
}
</script>

<style>

h1{
  font-size: 5rem;
  margin-left: 23rem;
}
.home{
	padding: 0;
	list-style-type: none;
	overflow: hidden;
	background-image: url(../img/background.jpg);
  background-repeat: no-repeat;
  background-size: cover;

}
.movie-list{
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  grid-gap: 1rem;
  max-width: 80%;
  height: auto;
  margin: 5rem auto;
  font-size: 20px;
  opacity: 100%;
}
#movie{
  max-width: 30%;
  padding-bottom: 10px;
}




</style>



