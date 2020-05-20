<template>
<div id="seating-chart">
    <movie-header />
    <h2>Please select seats</h2>
    <div id="container">
        <div v-for='seat in seats' v-bind:key='seat.seatId' v-on:click="changeStatus(seat.seatId)">
        <div v-if="!isSeatReserved(seat.seatId)" class="seat">
        <p 
         v-bind:class="{'selected':seat.selected}"
         >{{seat.row}} {{seat.seatNumber}}
            <input type="checkbox" :checked="seat.selected===true" :disabled=" isSeatReserved(seat.seatId)">
        </p>
        </div>
        </div>
    </div> 
    <br>
    <button id="select-button" v-on:click="sendSeats()" :disabled="showPurchaseTickets">to Purchase</button>
    
    <div class="seats-to-buy">
                <h3 v-if="showPurchaseTickets">Please insert bills face up</h3>

    <div v-for='seat in sendingSeats' v-bind:key="seat.seatId" >
        <div id="buying-seats">
            <p id="purchase-seat-p">Seat: {{seat.row}}{{seat.seatNumber}} Price: $12.00</p>
        </div>
    </div>
    <router-link :to="{ name: 'account-info' }" > <button id="purchase-button" v-if="showPurchaseTickets" v-on:click="saveSeats()">Reserve Seats</button></router-link>
    </div>
</div>
  


</template>

<script>
import auth from '../auth';

import MovieHeader from '@/views/MovieHeader.vue'


export default {
    components: {
        MovieHeader,
       
    },

    data(){
        return{
        seats:[],
        reservedSeats:[],
        showtime:[],
        sendingSeats: [],
        showPurchaseTickets: false,
        userId: ''
        
        }
    },

    created() {
        fetch('http://localhost:8080/Final-Capstone-Backend-Server/api/getUserId/'+auth.getUser().sub,
            {method:'GET', headers: {'Content-Type': 'application/json'}})
            .then((response) => {
                return response.json()
            })
            .then((jsonData) => {
               this.userId = jsonData;
               return this.userId;
            })
             .catch((err) => {console.log(err)})
       
       fetch('http://localhost:8080/Final-Capstone-Backend-Server/api/showtimes/showtimeId/'+this.$route.params.id)
       .then((response) => {
           return response.json();
       })
       .then((jsonData) => {
           this.showtime = jsonData
           return this.showtime
       })
       .then((showtime) => {
            
            fetch('http://localhost:8080/Final-Capstone-Backend-Server/api/seats/'+showtime.screenId,  
            {method:'GET', headers: {'Content-Type': 'application/json'}})    
            .then((response) => {
                return response.json();
             })
            .then((jsonData) => {
                let tempArray = jsonData
                tempArray.forEach((seat)=>{ // This adds a "selected" value in each seat
                    this.seats.push({
                        seatId: seat.seatId,
                        row: seat.row,
                        seatNumber: seat.seatNumber,
                        screenId: seat.screenId,
                        selected: false
                    })
                    
                })
                return this.seats;
              })
             .catch((err) => {console.log(err)})

              fetch('http://localhost:8080/Final-Capstone-Backend-Server/api/seats/reservedSeats/' + showtime.showtimeId,
              {method:'GET', headers: {'Content-Type': 'application/json'}})
              .then((response) => {
                  return response.json();
              })
              .then((jsonData) => {
                  this.reservedSeats = jsonData;
                  return this.reservedSeats
              })
              .catch((err) => {console.log(err)})
       })
       .catch((err) => {console.log(err)})

    },
    methods: {
        isSeatReserved(id) {
            let isReserved = false;
            this.reservedSeats.forEach((seat) => {
                if (seat.seatId == id) {
                    isReserved = true;
                }
            })
            return isReserved;
        },
        changeStatus(id){
            const arrIndex = this.seats.findIndex((seat) => seat.seatId == id);
            this.seats[arrIndex].selected = !this.seats[arrIndex].selected
        },
        sendSeats(){
            this.seats.forEach((seat) => {
                if (seat.selected == true) {
                    this.sendingSeats.push(seat)
                }
            })
            this.showPurchaseTickets = true;
        },
        saveSeats(){
            this.sendingSeats.forEach((seat) => {
                   
                  fetch('http://localhost:8080/Final-Capstone-Backend-Server/api/reservations/makeReservation', {
        method: 'POST',
        headers: {
          "Content-Type": "application/json"
        },
       
        body: '{"userId":'+this.userId+',"showtimeId":'+this.showtime.showtimeId+',"seatId":'+seat.seatId+',"reservationType": "adult","payment": 12.0}'
      })
      .then((response) => {
        if(response.ok) {
              console.log('saved')  
                }
            })
        
            })
        }
    },
    computed:{
        

    }

}
</script>

<style scoped>

#container {
    -webkit-user-select: none;
    width: 90%;
    margin-left: 90px;
    display: grid;
    grid-column-gap: 3px;
    grid-row-gap: 2px;
    grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr; 
    grid-template-areas:
        " seat seat seat seat seat seat seat seat seat seat seat seat seat seat "
        " seat seat seat seat seat seat seat seat seat seat seat seat seat seat "
        " seat seat seat seat seat seat seat seat seat seat seat seat seat seat "
        " seat seat seat seat seat seat seat seat seat seat seat seat seat seat "
        " seat seat seat seat seat seat seat seat seat seat seat seat seat seat "
        " seat seat seat seat seat seat seat seat seat seat seat seat seat seat "
        " seat seat seat seat seat seat seat seat seat seat seat seat seat seat ";        

}

#seat {
    grid-area: seat;
}

#seating-chart {
    background-color: rgb(194, 177, 152);

}

#select-button {
    display: inline-block;
    background-color: rgb(41, 15, 10);
    color: white;
    text-align: center;
    text-decoration: none;
    font-size: 2em;
    padding: 4px;
    border-radius: 7px;
    border-style: double;
    border-color: rgb(255, 255, 255);
    margin-top: 25px;
    margin-bottom: 200px;
    margin-left: 44%;
    
}

/* After you click the buy tickets button */
.seats-to-buy {
    margin-top: -150px;
}

#purchase-button {
    font-size: 2em;
    margin-left: 42.69%;
    margin-top: 30px;
    margin-bottom: 10px;
    border-style: solid;
    border-radius: 7px;
    color:white;
    background-color: rgb(41, 15, 10);
    padding: 3px
}

h3{
    text-align: center;
    margin-bottom: 10px;
}

#buying-seats{
    font-size: 1.5em;
}

#purchase-seat-p{
     background-color:rgb(151, 18, 18);
     margin-left: 39%;
     margin-right: 39%;
     color: white;
     border-style: solid;
     border-radius: 7px;
     border-color: black;
}
/*************/

.selected {
    border-color: white;
    
}

.seat {
    width: 3.75em;
    height: 1.75em;
    border-style: solid;
    border-radius: 7px;
    border-color: black;
    background-color: rgb(151, 18, 18);
    color: white;
}

h2 {
    margin-top: 3%;
    margin-bottom: 3%;
    margin-left: 33%;
    width: 45%;
    font-size: 2.75em;
}
</style>

