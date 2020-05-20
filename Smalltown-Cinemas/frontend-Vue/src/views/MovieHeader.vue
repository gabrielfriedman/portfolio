<template>
    <div class='header'>
    <h2>Welcome to Smalltown Cinemas!</h2>
    <ul>
    <li style="float:left"><router-link to="/">Home</router-link></li>
    <li v-if=isLoggedIn() @click="logout"><a href='/Login'>Log Out</a></li> 
    <li v-if=isNotLoggedIn() ><router-link to="/Login" active-class="active">Login</router-link></li>
    <li><router-link to="/Register" active-class="active" v-if=isNotLoggedIn()>Register</router-link></li>
    <li v-if=isLoggedIn()><router-link to="/Account-Info">{{getLoggedInUsername()}}</router-link></li>
    </ul>
    </div>
</template>

<script>
import auth from '../auth';

export default {
  methods: {
  logout() {
      auth.logout();
      this.$router.go('/Login')
  },
  isLoggedIn() {
    if (auth.getUser()) {
      return true;
    }
    else return false;
  },
  isNotLoggedIn() {
    if (auth.getUser()) {
      return false;
    }
    else return true;
  },
  getLoggedInUsername(){
            return auth.getUser().sub
        }
}

}


</script>

<style scoped>

.header {
    background-color: rgb(41, 15, 10);
}

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

li {
  float: right;
}

li a {
  display: block;
  color: rgb(177, 155, 122);
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

li {
  border-right: 1px solid #bbb;
}

li:first-child {
  border-right: none;
}

h2 {
   text-align: center; 
   font-size: 3em;
  color: rgb(177, 155, 122)
}

</style>