<template>
  <div class="container">
    <div class="listItem">
      <!-- 每一項頭條列表 -->
      <div class="containerItem" v-for="item in pageData" :key="item.hid">
        <div>
          <!-- 標題 -->
          <span class="text">{{ item.title }}</span>
        </div>
        <div class="detail">
          <span>{{
            item.type == 1
              ? "新聞"
              : item.type == 2
              ? "體育"
              : item.type == 3
              ? "娛樂"
              : item.type == 4
              ? "科技"
              : "其他"
          }}</span>
          <span>{{ item.pageViews }}觀看次數</span>
          <span>
            {{
              item.pastHours < 24
                ? item.pastHours + "小時前"
                : item.pastHours < 720
                ? Math.floor(item.pastHours / 24) + "天前"
                : Math.floor(item.pastHours / 720) + "個月前"
            }}
          </span>
        </div>
        <div>
          <el-button
            @click="toDetail(item.hid)"
            size="small"
            style="background: #198754; margin-left: 15px; color: #bbd3dc"
            >查看全文</el-button
          >
          <el-popconfirm
            v-if="item.publisher == type"
            @confirm="handlerDelete(item.hid)"
            :title="`確定要刪除嗎?`"
          >
            <template #reference>
              <el-button
                size="small"
                style="background: #dc3545; color: #bbd3dc"
                >删除</el-button
              >
            </template>
          </el-popconfirm>

          <el-button
            @click="Modify(item.hid)"
            v-if="item.publisher == type"
            size="small"
            style="background: #212529; color: #bbd3dc"
            >修改</el-button
          >
        </div>
      </div>

      <!-- 分頁 -->
      <div style="margin-top: 20px">
        <el-pagination
          v-model:current-page="findNewsPageInfo.pageNum"
          v-model:page-size="findNewsPageInfo.pageSize"
          @size-change="getPageList"
          @current-change="getPageList"
          :page-sizes="[5, 7, 10]"
          background
          layout="prev, pager, next , ->, sizes, total"
          :total="totalSize"
        
        />
      </div>
    </div>
  </div>
</template>

<script>
import { getfindNewsPageInfo, removeByHid } from "../../api/index";
import { defineComponent } from "vue";
export default defineComponent({
  name: "HeadlineNews",
});
</script>
<script setup>
import { ref, onMounted, getCurrentInstance, watch } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import pinia from "../../stores/index";
import { useUserInfoStore } from "../../stores/userInfo";
const { Bus } = getCurrentInstance().appContext.config.globalProperties;
const userInfoStore = useUserInfoStore(pinia);
const router = useRouter();
const type = userInfoStore.uid;
const findNewsPageInfo = ref({
  keyWords: "", // 搜尋標題關鍵字
  type: 0, // 新聞類型
  pageNum: 1, // 頁碼
  pageSize: 5, // 頁數
});
const totalSize = ref(0); //分頁總數量
// 初始化列表數據
const pageData = ref([
  {
    hid: null,
    pageViews: null,
    pastHours: null,
    publisher: null,
    title: "",
    type: null,
  },
]);

//接收header组件搜尋的數據
Bus.on("keyword", (keywords) => {
  findNewsPageInfo.value.keyWords = keywords;
});
// header點擊切換highlight的數據
Bus.on("tid", (type) => {
  findNewsPageInfo.value.type = type;
});
// 監視初始化type的變化，當發生改變時，重新發送請求獲取列表數據
watch(
  () => findNewsPageInfo.value,
  () => {
    getPageList();
  },
  {
    deep: true,
  }
);
//  初始化請求頁列表數據
const getPageList = async () => {
  let result = await getfindNewsPageInfo(findNewsPageInfo.value);
  pageData.value = result.pageInfo.pageData;
  findNewsPageInfo.value.pageNum = result.pageInfo.pageNum;
  findNewsPageInfo.value.pageSize = result.pageInfo.pageSize;
  totalSize.value = +result.pageInfo.totalSize;
};

// 組件掛載的生命週期鉤子
onMounted(() => {
  getPageList();
});
// 點擊查看全文的路由push
const toDetail = (hid) => {
  router.push({ name: "Detail", query: { hid } });
};

// 點擊刪除的回調
const handlerDelete = async (id) => {
  await removeByHid(id);
  ElMessage.success("删除成功!");
  //重新獲取列表請求
  getPageList();
};
//點擊修改的回調
const Modify = (hid) => {
  router.push({ name: "addOrModifyNews", query: { hid } });
};
</script>

<style lang="less" scoped>

.container {
  width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;

  // 列表樣式
  .listItem {
    .containerItem {
      margin-top: 20px;
      border-radius: 10px;
      border: 2px solid #ebebeb;
      width: 600px;
      height: 120px;

      div {
        margin-top: 10px;
      }

      .text {
        margin-left: 15px;
        color: #353a3f;
      }

      .detail {
        span {
          margin-left: 15px;
          color: #8b778a;
          font-size: 14px;
        }
      }
    }
  }
}
</style>
