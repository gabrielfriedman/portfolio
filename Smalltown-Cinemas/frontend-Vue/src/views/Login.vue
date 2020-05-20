<template>
  <div id="login" class="text-center">
    <movie-header />
    <form class="form-signin" @submit.prevent="login">
      <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
      <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div class="alert alert-success" role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <label for="username" class="sr-only">Username</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <h4><router-link :to="{ name: 'register' }">Need an account?</router-link></h4><br/>
      <button type="submit" class="button">Sign in</button>
    </form>
  </div>
</template>

<script>
import auth from '../auth';
import MovieHeader from '@/views/MovieHeader.vue';

export default {
  name: 'login',
  components: {
    MovieHeader
  },
  data() {
    return {
      user: {
        username: '',
        password: '',
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      fetch('http://localhost:8080/Final-Capstone-Backend-Server/login', {
        method: 'POST',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(this.user),
      })
        .then((response) => {
          if (response.ok) {
            return response.text();
          } else {
            this.invalidCredentials = true;
          }
        })
        .then((token) => {
          if (token != undefined) {
            if (token.includes('"')) {
              token = token.replace(/"/g, '');
            }
            auth.saveToken(token);
            this.$router.push('/');
          }
        })
        .catch((err) => console.error(err));
    },
  },
};
</script>

<style scoped>

#login {
  background-color: rgb(194, 177, 152);
  height: 100%;
}

h1 {
  margin-left: 0px;
  margin-bottom: 20px;
}

.form-signin {
  margin-left: 350px;
  margin-right: 350px;
}

input[type=text], select {
  width: 100%;
  padding: 12px 20px;
  margin: 20px 0;
  display: inline-block;
  align-items: center;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

input[type=password], select {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  margin-bottom: 20px;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

.button {
  background-color: rgb(41, 15, 10); 
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin-top: 20px;
  margin-bottom: 1000px;
}

h4 {
  font-size: 1.5em;
  margin-top: 20px;
}



</style>
