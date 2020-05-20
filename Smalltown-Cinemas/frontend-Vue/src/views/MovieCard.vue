<template>
 <div class="movie-card">
     <img id="movie-poster" v-bind:src="this.posterUrl" />
     <p> {{ this.name }}</p>
 </div>
</template>

<script>
export default {
    props: {
        name: String,
        posterUrl: String,
        overview: String
    }

}
</script>

<style>
   .movie-card{
       width: 200px;
       background-color: rgb(41, 15, 10);
       color: rgb(201, 182, 153);
       border-style: solid;
       border-color: rgb(41, 15, 10);
   }

    #movie-poster{
        max-width: 100%;
    }

    p {
        text-align: center;
        font-size: .9em;
    }

    


</style>