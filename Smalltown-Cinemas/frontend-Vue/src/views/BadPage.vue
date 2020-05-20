<template>
  <body>
  <div class="bad-page">
    <movie-header/>
    <img src='@/img/404error.jpg' />
  </div>
  </body>
</template>

<script>
import MovieHeader from "@/views/MovieHeader.vue";

export default {
  components: MovieHeader
};
</script>

<style>
body, html {
  height: 100%;
  margin: 0;
}

.bad-page {
  height: 100%; 

  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
}
</style>