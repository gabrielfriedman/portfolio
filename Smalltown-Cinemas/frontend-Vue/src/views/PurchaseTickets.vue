<template>
<div>
 <p>seat</P>
 
        <div v-if='this.isReserved == false'>
       <p>seat: {{seat.row}}{{seat.seatNumber}}</p>       
           <div class="dropdown">
                 <div class="dropdown-content">
                    <select id="seatType" v-model="reservation.reservationType">
                        <option value="adult">Adult</option>
                        <option value="child">Child</option>
                        <option value="senior">Senior</option>
                    </select>  
                 </div>
                 <p id="price" v-bind:key="reservation.payment">Price: ${{reservation.payment}}  </p>
                 <button id="select-button" v-on:click="saveReservation()">Reserve Seat</button>
           </div> 
        </div>
    <p v-if='this.isReserved == true'>Thanks asshole</p>
       


</div>
    
</template>

<script>
import auth from '../auth';


export default {
    components: {
  
    },
    data() {
        return {
            reservation: {
                userId: null,
                showtimeId: this.showtimeId,
                seatId: this.seat.seatId,
                reservationType: '',
                payment: this.payment()
            },
            isReserved: false
        }
    },
    props: {
        seat: {},
        showtimeId: Number
    },
    computed: {
        payment() {
            let price = null;
            if (this.reservation.reservationType == 'adult') {
                price = 12.00
            }
            if (this.reservation.reservationType == 'child') {
                price = 8.00
            }
            if (this.reservation.reservationType == 'senior') {
                price = 10.00
            }
            return price
        }
        
    },
    created() {
           fetch('http://localhost:8080/Final-Capstone-Backend-Server/api/getUserId/'+auth.getUser(),
            {method:'GET', headers: {'Content-Type': 'application/json'}})
            .then((response) => {
                return response.json()
            })
            .then((jsonData) => {
               this.reservation.userId = jsonData;
               return this.reservation.userId;
            })
             .catch((err) => {console.log(err)})
      
    },
    methods: {
        saveReservation() {
             fetch('http://localhost:8080/Final-Capstone-Backend-Server/api/reservations/makeReservation', {
        method: 'POST',
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(this.reservation)
      })
      .then((response) => {
        if(response.ok) {
                this.isReserved = true;
        }
      })
    }  
  }        
 }
    

</script>
