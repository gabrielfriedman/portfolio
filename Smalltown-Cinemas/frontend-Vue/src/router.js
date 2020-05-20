import Vue from 'vue'
import Router from 'vue-router'
import auth from './auth'
import Home from './views/Home.vue'
import Login from './views/Login.vue'
import Register from './views/Register.vue'
import MovieDetails from './views/MovieDetails.vue'
import BadPage from './views/BadPage.vue'
import AccountInfo from './views/AccountInfo.vue'
import PurchaseTickets from './views/PurchaseTickets.vue'
import SeatingChart from './views/SeatingChart.vue'
import ViewShowtimes from './views/ViewShowtimes.vue'

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [{
            path: '/',
            name: 'home',
            component: Home,
            meta: {
                requiresAuth: false
            }
        },
        {
            path: "/login",
            name: "login",
            component: Login,
            meta: {
                requiresAuth: false
            }
        },
        {
            path: "/register",
            name: "register",
            component: Register,
            meta: {
                requiresAuth: false
            }
        },
        {
            path: "/movie-details/:id",
            name: "movie-details",
            component: MovieDetails,
            meta: {
                requiresAuth: false
            }


        },
        {
            path: "/account-info/",
            name: "account-info",
            component: AccountInfo,
            meta: {
                requiresAuth: true
            }
        },
        {
            path: "/purchase-tickets/:id",
            name: "purchase-tickets",
            component: PurchaseTickets,
            meta: {
                requiresAuth: true
            },
            props: {prop: 'seats'}


        },
        {
            path: "/seating-chart/:id",
            name: "seating-chart",
            component: SeatingChart,
            meta: {
                requiresAuth: true
            }


        },
        {
            path: "/view-showtimes/:id",
            name: "view-showtimes",
            component: ViewShowtimes,
            meta: {
                requiresAuth: true
            }


        },
        {
            path: "/*",
            name: "bad-page",
            component: BadPage,
            meta: {
                requiresAuth: false
            }
        }
    ]
})

router.beforeEach((to, from, next) => {
    // Determine if the route requires Authentication
    const requiresAuth = to.matched.some(x => x.meta.requiresAuth);
    const user = auth.getUser();

    // If it does and they are not logged in, send the user to "/login"
    if (requiresAuth && !user) {
        next("/login");
    } else {
        // Else let them go to their next destination
        next();
    }
});

export default router;