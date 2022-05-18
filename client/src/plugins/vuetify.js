import Vue from "vue";
import Vuetify from "vuetify/lib/framework";

Vue.use(Vuetify);



export default new Vuetify({
    icons: {
    iconfont: 'mdiSvg',
  },
    theme: {
        themes: {
            light: {
                primary: '#2b2b2b',
                secondary: '#eff0ea',
                accent: '#EDAFB8',
                error: '#d17070',
                info: '#6c99be',
                success: '#9aaaa1',
                warning: '#FFC107'
            }
        }
    }
});
