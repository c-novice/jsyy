import Vue from 'vue'
import App from './App'
import uView from "uview-ui";

Vue.use(uView);

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()

Vue.prototype.$baseUrl = "http://127.0.0.1/api"
