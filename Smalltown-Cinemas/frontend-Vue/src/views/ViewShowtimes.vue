<template>
    <div>   
    <div class="showtimes">
        <ul class="showtimes-list"> 
            <button id="showtime-button"><router-link class="showtime" v-for='showtime in showtimes' v-bind:key='showtime.showtimeId' 
            v-bind:to='{name:"seating-chart", params:{id: showtime.showtimeId}}'>
                <li v-bind:key='showtime.showtimeId'>
                {{toStandardDate(showtime.showingDate)}}  -  {{toStandardTime(showtime.showingTime)}}
            </li></router-link></button>
        </ul>       
    </div>
    </div>
</template>

<script>

export default {
    components: {
       
    },
    data() {
        return {
      backend_URL:'process.env.VUE_APP_REMOTE_API',
      movie: [],
      showtimes: []
        }
    },
    methods: {
        toStandardTime(militaryTime) {
                militaryTime = militaryTime.split(':');
                return ((militaryTime[0].charAt(0) == 1 && militaryTime[0].charAt(1) > 2) || militaryTime[0].charAt(0) == 2) ? (militaryTime[0] - 12) + ':' + militaryTime[1] + ':' + ' PM' : militaryTime[0] + ':' + militaryTime[1] + ' AM'
             },
        toStandardDate(date) {
            date = date.split('-');
            return (date[1] + '/' + date[2] + '/' + date[0])
        }
       
    },
    mounted() {
        fetch('http://localhost:8080/Final-Capstone-Backend-Server/api/showtimes/' + this.screenId, 
        {method:'GET', headers: {'Content-Type': 'application/json'}})
        .then((response) => {
            return response.json();
        })
        .then((jsonData) => {
            this.showtimes = jsonData
            return this.showtimes;
        })
        .catch((err) => {console.log(err)})
    },
    props: {
        screenId:Number
    }
    
}
</script>

<style lang="scss" scoped>
/*    
    .movie-details {
        display: grid;
        grid-template-columns: 1fr 1fr;
        grid-template-areas: 
        " image movie-title     "
        " image movie-showtimes "
        " image movie-runtime   ";
    }

    #card {
        background-color: rgb(194, 177, 152);
    }

    #image {
        grid-area: image;
    }

    #movie-title {
        grid-area: movie-title;
    }

    #movie-showtimes {
        grid-area: movie-showtimes;
    }


    #movie-runtime {
        grid-area: movie-runtime;
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
*/
    #movie-showtimes {
        margin-left: -10%;
        font-size: 2.5em;
        font-style: oblique;
    }

    #movie-runtime {
        margin-left: -10%;
        font-size: 1.5em;
    }
    
    .showtime {
        font-size: .80em;
        text-decoration: none;
        list-style-type: none;
        text-decoration-color: white;
       
    }

    #showtime-button {
        border-style: solid;
        border-color: black;
        width: 13em;
        height: auto;
        font-size: 1.5em;
        background-color: rgb(194, 177, 152);
        border-radius: 5px;
        text-decoration-color: white;
    }
    button {
        text-decoration-color: white;
    }
</style>