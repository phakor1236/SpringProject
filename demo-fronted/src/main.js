import { createApp } from "vue";
import ElementPlus from "element-plus";
import zhTw from "element-plus/es/locale/lang/zh-tw";
import mitt from "mitt";
import router from "./routers/index";
import "element-plus/dist/index.css";
import App from "./App.vue";
import pinia from "./stores";
const app = createApp(App);
app.config.globalProperties.Bus = mitt();

app.use(ElementPlus, {
  locale: zhTw,
});
app.use(pinia);
app.use(router);
app.mount("#app");
