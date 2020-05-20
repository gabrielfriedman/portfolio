<template>
<div>
    <movie-header />
    <body>
    <div class="info-view">
    <h1 id="header1">Welcome {{getLoggedInUsername()}}</h1>
    <h2 id="header2">Purchase History</h2>
    <div>
    <table id="table">
        <tr>
            <th> Film </th>
            <th> Screen </th>
            <th> Seat </th>
            <th> Date </th>
            <th> Time </th>
            <th> Price </th>
        </tr>
        <tr v-for='purchase in purchases' v-bind:key='purchase.reservationId'>
            <td>{{purchase.filmTitle}}</td>
            <td>{{purchase.screen}}</td>
            <td>{{purchase.seatRow}}{{purchase.seatNumber}}</td>
            <td>{{toStandardDate(purchase.showingDate)}}</td>
            <td>{{toStandardTime(purchase.showingTime)}}</td>
            <td>${{formatMoney(purchase.price)}} </td><br id="break">
        </tr>
    </table>
    <h1 id="break">1</h1>
    </div>
    </div>
    </body>
</div>
    
</template>

<script>

import MovieHeader from '@/views/MovieHeader.vue';
import auth from '../auth';

export default {
    components: {
        MovieHeader
    },
    data() {
        return {
            purchases: []
        }
    },
    methods: {
        getLoggedInUsername(){
            return auth.getUser().sub
        },
        formatMoney(value) { // The currency value comes in from JSON as an integer, this applies REGEX to make it pretty
            let val= (value/1).toFixed(2).replace(',','.')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")
        },
        getFilm(reservationId){
            fetch('http://localhost:8080/Final-Capstone-Backend-Server/api/reservations/getMovie/'+reservationId,
            {method:'GET', headers: {'Content-Type': 'application/json'}})
            .then((response) => {
                return response.json()
            })
            .then((jsonData) => {
            
                return jsonData
            })
            .catch((err) => {console.log(err)})
        },
         toStandardTime(militaryTime) {
                militaryTime = militaryTime.split(':');
                return ((militaryTime[0].charAt(0) == 1 && militaryTime[0].charAt(1) > 2) || militaryTime[0].charAt(0) == 2) ? (militaryTime[0] - 12) + ':' + militaryTime[1] + ':' + ' PM' : militaryTime[0] + ':' + militaryTime[1] + ' AM'
             },
        toStandardDate(date) {
            date = date.split('-');
            return (date[1] + '/' + date[2] + '/' + date[0])
        }
    },
    
    created() {
        fetch('http://localhost:8080/Final-Capstone-Backend-Server/api/reservations/getReservations/' + auth.getUser().sub,
            {method:'GET', headers: {'Content-Type': 'application/json'}})          
            .then((response) => {
                return response.json();
             })
             .then((jsonData) => {  
         
                 let tempArray = jsonData
                 console.log(tempArray)
                 tempArray.forEach((ticket) => {
                     
                     fetch('http://localhost:8080/Final-Capstone-Backend-Server/api/reservations/getMovie/'+ticket.reservationId,
                     {method:'GET', headers: {'Content-Type': 'application/json'}})
                     .then((response) => {
                            return response.json()
                      })
                        .then((thisTicket) => {
                            this.purchases.push({
                             filmTitle: thisTicket.filmTitle,
                             screen: thisTicket.screenId,
                             showingDate: thisTicket.showingDate,
                             showingTime: thisTicket.showingTime,
                             seatRow: thisTicket.seatRow,
                             seatNumber: thisTicket.seatNumber,
                             price: ticket.payment
                         })        
                           
                      })
                     .catch((err) => {console.log(err)})
                    
                     
                 })
     
             })
             .catch((err) => {console.log(err)})
        
    }
    
}
</script>

<style scoped>

    html {
        height: auto;
        min-height: 100%;
        }
    body {
        min-height: 100%;
         background-color: rgb(194, 177, 152);
        }
    
    #header1 {
        font-size: 3.75em;
        margin-left: 40px;
    }
    #header2 {
        font-size: 2em;
        margin-left: 40px;
    }
    #table {
        border-spacing: 2em .5em;
        font-size: 1em;
        margin-left: 40px;
        margin-bottom: 400px;
        background-color: rgb(194, 177, 152);
    }

    #break {
        color: rgb(194, 177, 152);
    }

</style>
