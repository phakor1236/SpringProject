<template>
  <div class="headerContainer">
    <!-- header左側區域 -->
    <div class="left">
      
      <ul>
        <li @click="HighlightHandler(index,)"  v-for="(item,index) in findAllTypeList" :key="item.tid">
          <a :class="{ active: item.isHighlight }" href="javascript:;">{{item.tname}}</a>
        </li>
      </ul>
    </div>
    <!-- header右側區域 -->
    <div class="right">
      <div class="rightInput" style="margin-right: 50px;">
        
        <el-input v-model="keywords" placeholder="搜尋" :prefix-icon="Search" size="large"></el-input>
        <!-- <el-button type="primary">搜尋</el-button> -->
        
      </div>

  
      <!-- 登入以後的header -->
      <div class="btn-dropdown">
      <!-- 沒有登入的header -->
     
      <div v-if="nickName" style="display: flex; justify-content: center; align-items: center;">
             <el-dropdown>
          <el-button type="primary">
          您好:{{ nickName }}<el-icon class="el-icon--right"><arrow-down /></el-icon>
          </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="handlerNews">發布新聞</el-dropdown-item>
              <el-dropdown-item @click="Logout">退出登入</el-dropdown-item>
            </el-dropdown-menu>
          </template>
          
        </el-dropdown>
      </div>
       <div v-else class="containerButton">
          <el-button size="small" style="background: #212529; color: #aea7a2" @click="toLogin">登入</el-button>
          <el-button size="small" style="background: #ffc107; color: #684802" @click="toRegister">註冊</el-button>
          <el-button size="small" style="background: #f3f3f3; color: #684802" @click="tohome">首頁</el-button>
        </div>
      
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
export default defineComponent({
  name: 'Header'
})
</script>

<script setup>
import { getfindAllTypes, isUserOverdue } from '../api/index'
import { ref, onMounted , getCurrentInstance ,watch, onUpdated} from "vue"
import { useRouter } from 'vue-router'
import { ArrowDown,Search} from '@element-plus/icons-vue'
import { removeToken } from '../utils/token-utils' 
import pinia from '../stores/index';
import { useUserInfoStore } from '../stores/userInfo'
const userInfoStore = useUserInfoStore(pinia)
const nickName = ref("")
//獲取到全局事件
const { Bus } = getCurrentInstance().appContext.config.globalProperties

//路由對象
const router = useRouter()
// 搜尋最新頭條參數
const keywords = ref("") 

//監視搜尋參數的變化 然後直接顯示有關鍵字的結果
watch(keywords, (newVal) => {
  Bus.emit('keyword', newVal)
})
//所有頭條分類
const findAllTypeList = ref([])

//路由前往的地方

//登入頁面
const toLogin = () => {
router.push({ name: "Login" });
}
//首頁
const tohome = () => {
  router.push({name: "HeadlineNews"})
}
//註冊頁面
const toRegister = () => {
  router.push({ name: "Register" });
}
const getList = async () => {
  //調用getfindAllTypes函數，使用await等待這個執行，並將結果賦值給result
  let result = await getfindAllTypes()
  // 遍歷數據
  result.forEach((item) => {
    item.tid = item.tid
    item.tname = item.tname
    item.isHighlight = false
  })
  // 在索引為0的地方，加入第一項測試
  // isHighlight是要標示目前搜尋的tname
  result.unshift({
    isHighlight: true,
    tid: 0,
    tname: "頭條測試"
  })
  //響應式數據
  findAllTypeList.value = result
}
//頁面掛載的生命週期回調
//更新使用者的名字
onUpdated(() => {
  nickName.value = userInfoStore.nickName
})
onMounted(() => {
  getList()
})

//點擊後切換高光(排他思想)
const HighlightHandler = (index) => {
  findAllTypeList.value.forEach((item) => {
    item.isHighlight = false
  })
  // 切換高光的時候把tid给HeadlineNews组件
  findAllTypeList.value[index].isHighlight = true
  Bus.emit('tid', findAllTypeList.value[index].tid)
}

// 點擊退出登入的回調
const Logout = () => {
  removeToken()
  userInfoStore.initUserInfo()
  nickName.value = ""
  router.go({ name: "HeadlineNews" });
}

//點擊發布新聞的回調
const handlerNews = async () => {
  //發送請求詢問token是否過期
  await isUserOverdue()
  router.push({ name: "addOrModifyNews" });
}
</script>

<style>
.el-dropdown {
  vertical-align: top;
  width: 100px;
}

.el-dropdown+.el-dropdown {
  margin-left: 15px;
}

.el-icon-arrow-down {
  font-size: 12px;
}
</style>

<style lang="less" scoped>
.headerContainer {
  width: 100%;
  height: 90px;
  background: #020405;
  display: flex;
  justify-content: space-around;
  .left {
    ul {
      display: flex;
      li {
        list-style: none;
        margin-left: 30px ;
        padding: 15px;
        a:-webkit-any-link {
          text-decoration: none;
          color: #82bee4;
          &.active {
            color: #ffffff;
          }
        }
      }
    }
  }
  //Header右半部
  .right {
    .containerButton {
      display: flex;
      align-items: center;
    }
    line-height: 60px;
    display: flex;
    flex-wrap: nowrap;
    //輸入框
    .rightInput {
      display: flex;
       align-items: center;
      :deep(.el-input__inner) {
        height: 30px;
        width: 250px;
      }
    }
    //登入後的下拉選單
    .btn-dropdown{
      display: flex;
      align-items: center;
    }
    :deep(.el-button) {
      margin: 0 0 0 10px;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
}


.example-showcase .el-dropdown + .el-dropdown {
  margin-left: 15px;
}
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>


















